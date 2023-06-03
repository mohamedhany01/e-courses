package gitlet;

import gitlet.interfaces.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class App {

    /*  init: https://sp21.datastructur.es/materials/proj/proj2/proj2#init
     *
     *   - A commit that contains no files and has the commit message initial commit [DONE]
     *
     *   - It will have a single branch: master, which initially points to this initial commit, and master will be the current branch [DONE]
     *
     *   - The timestamp for this initial commit will be 00:00:00 UTC, Thursday, 1 January 1970} [DONE]
     *
     *   - If there is already a Gitlet version-control system in the current directory, it should abort [DONE]
     *
     *   - It should NOT overwrite the existing system with a new one, Should print the error message A Gitlet version-control system already exists in the current directory. [DONE]
     *
     *   - Line count: ~15
     * */
    public static void init() {
        if (LocalRepositoryManager.isgitletExists()) {
            System.out.print("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }

        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IStagingArea stagingArea = new StagingArea(utilities, gitletPaths);
        IHEAD head = new HEAD(utilities, gitletPaths);

        LocalRepositoryManager manager = LocalRepositoryManager.create(utilities, stagingArea, head);

        // Replace ZoneId.systemDefault() with ZoneId.of("UTC-8") should store data as Wed Dec 31 16:00:00 1969 -0800
        LocalDateTime zeroDate = Instant.ofEpochSecond(0).atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Root commit
        Blob blob = new Blob(
                utilities,
                new byte[]{},
                null,
                null
        );
        Tree tree = new Tree();
        tree.setBlob(blob.getHash());
        tree.calculateContentHash(utilities);
        Commit commit = new Commit(
                "initial commit",
                zeroDate,
                "foo",
                "foo@foo.foo",
                tree.getHash(),
                null,
                utilities.sha1(tree.getHash()) // TODO: find is this the right way to calculate hash for a commit?
        );

        manager.commitRootCommit(blob, tree, commit);

        String defaultBranch = "master";

        Repository repository = Repository.create(utilities, gitletPaths);
        repository.createBranch(defaultBranch, commit.getHash());

        head.updateHEAD(defaultBranch);
    }

    /*  add: https://sp21.datastructur.es/materials/proj/proj2/proj2#add
     *
     *   - Adds a copy of the file as it currently exists to the staging area (see the description of the commit command) [DONE]
     *
     *   - Staging an already-staged file overwrites the previous entry in the staging area with the new contents [DONE]
     *
     *   - The staging area should be somewhere in .gitlet [DONE]
     *
     *   - If the current working version of the file is identical to the version in the current commit, do not stage it to be added, and remove it from the staging area if it is already there (as can happen when a file is changed, added, and then changed back to it’s original version) TODO
     *
     *   - The file will no longer be staged for removal (see gitlet rm), if it was at the time of the command rm. TODO
     *
     *   - If the file does not exist, print the error message File does not exist. and exit without changing anything. [DONE]
     *
     *   - 20 lines
     * */
    public static void stage(String[] args) {
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IStagingArea stagingArea = new StagingArea(utilities, gitletPaths);

        for (int i = 1; i < args.length; i++) {
            String fileName = args[i];
            Path fullPath = Paths.get(gitletPaths.getWorkingDirectory().toString(), fileName);

            if (!stagingArea.hasFileName(fileName) && !Files.exists(fullPath)) {
                System.out.print("File does not exist.");
                System.exit(0);
            }

            if (Files.exists(fullPath)) {
                stagingArea.stage(new Blob(
                        utilities,
                        utilities.readContents(fullPath.toFile()),
                        args[i],
                        fullPath.toString()
                ));
            }

            // If not exist this mean it's deleted, so no new blob should be created, and should be removed form the staging area
            if (stagingArea.hasFileName(fileName) && !Files.exists(fullPath)) {
                stagingArea.deleteEntry(fileName);
                stagingArea.stagManually(fileName, "");
            }
        }

        // TODO: remove this line
        System.out.println(stagingArea.loadStagingArea());
    }

    /* commit: https://sp21.datastructur.es/materials/proj/proj2/proj2#commit
     *
     *   - Saves a snapshot of tracked files in the current commit and staging area so they can be restored at a later time [DONE]
     *
     *   - A commit will only update the contents of files it is tracking that have been staged for addition at the time of commit, in which case the commit will now include the version of the file that was staged instead of the version it got from its parent [DONE]
     *
     *   - A commit will save and start tracking any files that were staged for addition but weren’t tracked by its parent. [DONE]
     *
     *   - Files tracked in the current commit may be untracked in the new commit as a result being staged for removal by the rm command. [DONE]
     *
     *   - The staging area is cleared after a commit. [DONE]
     *
     *   - The commit command never adds, changes, or removes files in the working directory (other than those in the .gitlet directory). [DONE]
     *
     *   - The rm command will remove such files, as well as staging them for removal, so that they will be untracked after a commit. [DONE]
     *
     *   - Any changes made to files after staging for addition or removal are ignored by the  commit command, which only modifies the contents of the .gitlet directory. [DONE]
     *
     *   - After the commit command, the new commit is added as a new node in the commit tree. [DONE]
     *
     *   - The commit just made becomes the “current commit”, and the head pointer now points to it. The previous head commit is this commit’s parent commit. [DONE]
     *
     *   - Each commit should contain the date and time it was made. [DONE]
     *
     *   - Each commit is identified by its SHA-1 id, which must include the file (blob) references of its files, parent reference, log message, and commit time. [DONE]
     *
     *   - If no files have been staged, abort. Print the message No changes added to the commit. [DONE]
     *
     *   - Every commit must have a non-blank message. If it doesn’t, print the error message Please enter a commit message. [DONE]
     *
     *   - From real git: In real git, commits may have multiple parents (due to merging) and also have considerably more metadata.
     *
     *  - Line count: ~35
     * */
    public static void commit(String[] args) {
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IStagingArea stagingArea = new StagingArea(utilities, gitletPaths);
        IHEAD head = new HEAD(utilities, gitletPaths);

        if (stagingArea.isStagingAreaInCleanState()) {
            System.out.print("No changes added to the commit.");
            System.exit(0);
        }

        // TODO: remove this log
        System.out.println("The content of Staging Tree are:");
        System.out.println(stagingArea.loadStagingArea());

        // Get the commit message and load the staging area
        String commitMessage = args[1].trim();

        if (commitMessage.isEmpty()) {
            System.out.print("Please enter a commit message.");
            System.exit(0);
        }

        HashMap<String, String> readyBlobs = stagingArea.getBlobsReadyToBeCommitted();

        // TODO: remove this log
        System.out.println("The content will be committed: ");
        System.out.println(readyBlobs);

        List<IBlob> blobs = new LinkedList<>();

        Tree tree = new Tree();
        for (Map.Entry<String, String> entry : readyBlobs.entrySet()) {
            Path blobPathInRepo = Paths.get(gitletPaths.getObjects().toString(), entry.getValue());

            // If file in staging area isn't exist in the repo area "changed for example" then we need to create a new blob
            if (!Files.exists(blobPathInRepo)) {
                Path filePath = Paths.get(gitletPaths.getWorkingDirectory().toString(), entry.getKey());
                Blob newBlob = new Blob(
                        utilities,
                        utilities.readContents(filePath.toFile()),
                        filePath.getFileName().toString(),
                        entry.getKey());

                blobs.add(newBlob);
                tree.setBlob(newBlob.getHash());
            } else {
                // No new blob needed since this blobs already in the repo "objects directory"
                tree.setBlob(entry.getValue());
            }

        }

        tree.calculateContentHash(utilities);

        String parentHash = head.getActiveBranchHash();
        Commit commit = new Commit(
                commitMessage,
                LocalDateTime.now(),
                "foo", // TODO: change this
                "foo@foo.foo", // TODO: change this
                tree.getHash(),
                parentHash,
                utilities.sha1(tree.getHash()) // TODO: find is this the right way to calculate hash for a commit?
        );

        Repository repository = Repository.create(utilities, gitletPaths);
        ICommit committedObject = repository.commitObjects(commit, tree, blobs);

        repository.updateBranch(
                head.getActiveBranchName(),
                committedObject.getHash()
        );

        // Clean staging area from deleted files
        for (Map.Entry<String, String> entry : stagingArea.loadStagingArea().entrySet()) {
            if (entry.getValue().isEmpty()) {
                HashMap<String, String> currentStagingArea = stagingArea.loadStagingArea();
                currentStagingArea.remove(entry.getKey(), entry.getValue());
                stagingArea.updateStagingArea(currentStagingArea);
                System.out.println("[X] " + entry.getKey() + " removed from Staging Area");
            }
        }
    }

    /*
     *   rm: https://sp21.datastructur.es/materials/proj/proj2/proj2#rm
     *   - Unstage the file if it is currently staged for addition. [DONE]
     *   - If the file is tracked in the current commit, stage it for removal and remove the file from the working directory if the user has not already done so [DONE]
     *   - If the file is neither staged nor tracked by the head commit, print the error message No reason to remove the file. [DONE]
     * */
    public static void unstage(String[] args) {
        // TODO: this operation supports only one file per-time, so maybe supporting multiple file if possible
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IStagingArea stagingArea = new StagingArea(utilities, gitletPaths);
        HashMap<String, String> currentStagingArea = stagingArea.loadStagingArea();

        String fileToBeRemoved = args[1];

        Path fullPath = Paths.get(GitletPaths.WORKING_DIRECTORY.toString(), fileToBeRemoved);

        // TODO: remove this condition
        if (!Files.exists(fullPath)) {
            throw new RuntimeException("Can't handle {" + fullPath.getFileName() + "} it isn't exist!");
        }

        if (!stagingArea.hasFileName(fileToBeRemoved)) {
            System.out.print("No reason to remove the file.");
            System.exit(0);
        }

        // Extract file info
        String fileHash = utilities.sha1(
                utilities.readContentsAsString(
                        fullPath.toFile()
                )
        );

        // TODO: this condition is handle only files in current working directory, no nesting "sub-directories" is supported yet
        // If the file in the staging area then gitlet knows something about it
        if (currentStagingArea.containsKey(fileToBeRemoved)) {

            // Stage it to be removed if it is in the repo
            if (Repository.isInRepository(fileHash, gitletPaths)) {
                stagingArea.stagManually(fileToBeRemoved, "");

                // Remove the file from the working directory
                try {
                    Files.deleteIfExists(fullPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // Need to load updated staging area, to stage deleted file
                currentStagingArea = stagingArea.loadStagingArea();
            }

            // If the file is in the staging area and not tracked by the current commit "not in the repo" then remove it "unstage"
            if (!Repository.isInRepository(fileHash, gitletPaths) && currentStagingArea.containsKey(fileToBeRemoved) && currentStagingArea.get(fileToBeRemoved).equals(fileHash)) {
                currentStagingArea.remove(fileToBeRemoved);
            }

            stagingArea.updateStagingArea(currentStagingArea);
        }
    }

    /* log: https://sp21.datastructur.es/materials/proj/proj2/proj2#log
     *
     *   - Starting at the current head commit, display information about each commit backwards along the commit tree until the initial commit, following the first parent commit links. [DONE]
     *
     * - Ignoring any second parents found in merge commits. TODO
     *
     * - For every node in this history, the information it should display is the commit id, the time the commit was made, and the commit message. [DONE]
     *
     * - There is a === before each commit and an empty line after it [DONE]
     *
     * - The timestamps displayed in the commits reflect the current timezone, not UTC; as a result, the timestamp for the initial commit does not read Thursday, January 1st, 1970, 00:00:00, but rather the equivalent Pacific Standard Time. Your timezone might be different depending on where you live, and that’s fine. [DONE]
     *
     * - Merge field. TODO
     * */
    public static void log() {
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IHEAD head = new HEAD(utilities, gitletPaths);

        String currentHead = head.getActiveBranchHash();
        Commit currentCommit = Commit.getCommit(currentHead, utilities);

        while (true) {
            if (currentCommit == null) {
                break;
            }

            System.out.println("===");
            System.out.println("commit " + currentCommit.getHash());
            System.out.println("Date: " + Commit.formatCommitData(
                    currentCommit.getDate()
            ));
            System.out.println(currentCommit.getMessage() + "\n");

            currentCommit = Commit.getCommit(currentCommit.getParent(), utilities);
        }
    }

    /* log: https://sp21.datastructur.es/materials/proj/proj2/proj2#global-log
     *
     *   - Like log, except displays information about all commits ever made. The order of the commits does not matter. [DONE]
     *   - There is a useful method in gitlet.Utils that will help you iterate over files within a directory. [DONE]
     *   - Merge field. TODO
     * */
    public static void logAll() {
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        Path objectsDirectory = gitletPaths.getObjects();

        // If no objects directory just exit and don't do anything
        if (!Files.exists(objectsDirectory)) {
            System.exit(0);
        }

        // Loop over objects directory, and read all serialized objects
        for (String objectHash : utilities.plainFilenamesIn(objectsDirectory.toFile())) {
            Path objectPath = Path.of(objectsDirectory.toString(), objectHash);

            try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(objectPath.toFile()))) {
                Object rowObject = reader.readObject();

                // If you found any object of type Commit print it
                if (rowObject instanceof Commit commit) {
                    System.out.println("===");
                    System.out.println("commit " + commit.getHash());
                    System.out.println("Date: " + Commit.formatCommitData(commit.getDate()));
                    System.out.println(commit.getMessage() + "\n");
                }

            } catch (FileNotFoundException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* find: https://sp21.datastructur.es/materials/proj/proj2/proj2#find
     *
     * - Prints out the ids of all commits that have the given commit message, one per line. [DONE]
     *
     * - If there are multiple such commits, it prints the ids out on separate lines. [DONE]
     *
     * - The commit message is a single operand; to indicate a multiword message, put the operand in quotation marks, as for the commit command below. Hint: the hint for this command is the same as the one for global-log. [DONE]
     *
     * - If no such commit exists, prints the error message Found no commit with that message. [DONE]
     *
     * - Doesn’t exist in real git. Similar effects can be achieved by grepping the output of log. [DONE]
     * */
    public static void find(String[] args) {
        String commitMassage = args[1];
        boolean commitFound = false;

        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        Path objectsDirectory = gitletPaths.getObjects();

        // If no objects directory just exit and don't do anything
        if (!Files.exists(objectsDirectory)) {
            System.exit(0);
        }

        // Loop over objects directory, and read all serialized objects
        for (String objectHash : utilities.plainFilenamesIn(objectsDirectory.toFile())) {
            Path objectPath = Path.of(objectsDirectory.toString(), objectHash);

            try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(objectPath.toFile()))) {
                Object rowObject = reader.readObject();

                // If you found any object of type Commit print it
                if (rowObject instanceof Commit commit && commit.getMessage().equals(commitMassage)) {
                    System.out.println(commit.getHash());
                    commitFound = true;
                }

            } catch (FileNotFoundException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!commitFound) {
            System.out.println("Found no commit with that message.");
        }
    }

    /* status: https://sp21.datastructur.es/materials/proj/proj2/proj2#status
     *
     * - Displays what branches currently exist, and marks the current branch with a *. [DONE]
     *
     * - Displays what files have been staged for addition or removal. TODO
     *
     * - The last two sections (modifications not staged and untracked files) are extra credit, worth 32 points. Feel free to leave them blank (leaving just the headers). TODO
     *
     * - There is an empty line between sections, and the entire status ends in an empty line as well. [DONE]
     *
     * - Entries should be listed in lexicographic order, using the Java string-comparison order (the asterisk doesn’t count). TODO
     *
     * - Runtime: Make sure this depends only on the amount of data in the working directory plus the number of files staged to be added or deleted plus the number of branches. [DONE]
     *
     * - READ: https://sp21.datastructur.es/materials/proj/proj2/proj2#status for more details
     * */
    public static void status() {
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IStagingArea stagingArea = new StagingArea(utilities, gitletPaths);
        IHEAD head = new HEAD(utilities, gitletPaths);
        LocalRepositoryManager manager = LocalRepositoryManager.create(utilities, stagingArea, head);
        manager.showStatus();
    }

    /* branch: https://sp21.datastructur.es/materials/proj/proj2/proj2#branch
     *
     * - Creates a new branch with the given name, and points it at the current head commit. [DONE]
     *
     * - A branch is nothing more than a name for a reference (a SHA-1 identifier) to a commit node. [DONE]
     *
     * - This command does NOT immediately switch to the newly created branch (just as in real Git). [DONE]
     *
     * - Before you ever call branch, your code should be running with a default branch called “master”. [DONE]
     *
     * - Failure cases: If a branch with the given name already exists, print the error message A branch with that name already exists. [DONE]
     * */
    public static void branch(String[] args) {
        String branchName = args[1];

        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        HEAD head = new HEAD(utilities, gitletPaths);
        Repository repository = Repository.create(utilities, gitletPaths);

        Path branches = gitletPaths.getRefs();

        if (!Files.exists(branches)) {
            System.exit(0);
        }

        if (Files.exists(Path.of(branches.toString(), branchName))) {
            System.out.println("A branch with that name already exists.");
            System.exit(0);
        }

        repository.createBranch(branchName, head.getActiveBranchHash());
    }

    /* rm-branch: https://sp21.datastructur.es/materials/proj/proj2/proj2#rm-branch
     *
     * - Deletes the branch with the given name. [DONE]
     *
     * - This only means to delete the pointer associated with the branch; it does not mean to delete all commits that were created under the branch, or anything like that. [DONE]
     *
     * - If a branch with the given name does not exist, aborts. Print the error message A branch with that name does not exist. [DONE]
     *
     * - If you try to remove the branch you’re currently on, aborts, printing the error message Cannot remove the current branch. [DONE]
     * */
    public static void removeBranch(String[] args) {
        String branchName = args[1];

        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        HEAD head = new HEAD(utilities, gitletPaths);
        Repository repository = Repository.create(utilities, gitletPaths);

        Path branches = gitletPaths.getRefs();

        if (!Files.exists(branches)) {
            System.exit(0);
        }

        if (!repository.hasBranch(branchName)) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        }

        if (head.getActiveBranchName().equals(branchName)) {
            System.out.println("Cannot remove the current branch.");
            System.exit(0);
        }

        repository.removeBranch(branchName);
    }

    /* checkout: https://sp21.datastructur.es/materials/proj/proj2/proj2#checkout
     *
     * - However, that you poke around in a .git directory (specifically, .git/objects) and see how it manages to speed up its search. You will perhaps recognize a familiar data structure implemented with the file system rather than pointers. TODO
     *
     * - Only version 3 (checkout of a full branch) modifies the staging area: otherwise files scheduled for addition or removal remain so. TODO
     * */
    public static void checkout(String[] args) {
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();

        Repository repository = Repository.create(utilities, gitletPaths);
        HEAD head = new HEAD(utilities, gitletPaths);

        /*
         * - Takes all files in the commit at the head of the given branch, and puts them in the working directory, overwriting the versions of the files that are already there if they exist. [DONE]
         *
         * - Also, at the end of this command, the given branch will now be considered the current branch (HEAD). [DONE]
         *
         * - Any files that are tracked in the current branch but are not present in the checked-out branch are deleted. [DONE]
         *
         * - The staging area is cleared, unless the checked-out branch is the current branch [DONE]
         *
         * - If no branch with that name exists, print No such branch exists. [DONE]
         *
         * - If that branch is the current branch, print No need to checkout the current branch. [DONE]
         *
         * -  If a working file is untracked in the current branch and would be overwritten by the checkout, print There is an untracked file in the way; delete it, or add and commit it first. and exit; perform this check before doing anything else. Do not change the CWD. [DONE]
         * */

        // Checkout branch
        if (args.length == 2) {

            String branchName = args[1];

            if (!repository.hasBranch(branchName)) {
                System.out.println("No such branch exists.");
                System.exit(0);
            }

            if (head.getActiveBranchName().equals(branchName)) {
                System.out.println("No need to checkout the current branch.");
                System.exit(0);
            }

            List<Object> blobs = Commit.getBlobs(repository.getBranchHash(branchName), utilities);
            WorkingArea workingArea = new WorkingArea(utilities, gitletPaths);
            StagingArea stagingArea = new StagingArea(utilities, gitletPaths);


            if (workingArea.hasUntrackedFile(stagingArea)) {
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                System.exit(0);
            }

            workingArea.clear();

            for (Object rowBlob : blobs) {
                String blobHash = (String) rowBlob;
                Blob blob = Blob.getBlob(blobHash, utilities);
                Path file = Path.of(gitletPaths.getWorkingDirectory().toString(), blob.getFileName());

                utilities.writeContents(file.toFile(), blob.getContent());
                stagingArea.stagManually(blob.getFileName(), blob.getHash());
            }

            head.updateHEAD(branchName);
        }

        /*
         * - Takes the version of the file as it exists in the head commit and puts it in the working directory, overwriting the version of the file that’s already there if there is one. [DONE]
         *
         * - The new version of the file is not staged. [DONE]
         *
         * - If the file does not exist in the previous commit, abort, printing the error message File does not exist in that commit. Do not change the CWD. [DONE]
         * */

        // Checkout file
        if (args.length == 3 && args[1].equals("--")) {
            String fileName = args[2];

            Blob blob = Commit.hasFile(fileName, head.getActiveBranchHash(), utilities);
            if (blob == null) {
                System.out.println("File does not exist in that commit.");
                System.exit(0);
            }

            // Start restoring the file
            Path fileFullPath = Path.of(gitletPaths.getWorkingDirectory().toString(), fileName);
            utilities.writeContents(fileFullPath.toFile(), blob.getContent());
        }

        /*
         * - Takes the version of the file as it exists in the commit with the given id, and puts it in the working directory, overwriting the version of the file that’s already there if there is one. [DONE]
         *
         * - The new version of the file is not staged. [DONE]
         *
         * - if the file does not exist in the given commit, print File does not exist in that commit. Do not change the CWD.[DONE]
         * */

        // Checkout commit hash and file
        if (args.length == 4 && args[2].equals("--")) {
            StagingArea stagingArea = new StagingArea(utilities, gitletPaths);
            String commitHash = args[1];
            String fileName = args[3];

            if (!Repository.isInRepository(commitHash, gitletPaths)) {
                System.out.println("No commit with that id exists.");
                System.exit(0);
            }

            Blob blob = Commit.hasFile(fileName, commitHash, utilities);
            if (blob == null) {
                System.out.println("File does not exist in that commit.");
                System.exit(0);
            }

            // Start restoring the file
            Path fileFullPath = Path.of(gitletPaths.getWorkingDirectory().toString(), fileName);
            utilities.writeContents(fileFullPath.toFile(), blob.getContent());
//            stagingArea.stagManually(fileName, blob.getHash()); TODO: should I active this or not? because this command shouldn't update the staging area
            repository.updateBranch(head.getActiveBranchName(), commitHash);
        }
    }

    /* reset: https://sp21.datastructur.es/materials/proj/proj2/proj2#reset
     *
     * - Checks out all the files tracked by the given commit. Removes tracked files that are not present in that commit. [DONE]
     *
     * - Also moves the current branch’s head to that commit node. See the intro for an example of what happens to the head pointer after using reset. [DONE]
     *
     * -  The [commit id] may be abbreviated as for checkout. TODO
     *
     * - The staging area is cleared. [DONE]
     *
     * -  If no commit with the given id exists, print No commit with that id exists. [DONE]
     *
     * -  If a working file is untracked in the current branch and would be overwritten by the reset, print `There is an untracked file in the way; delete it, or add and commit it first. and exit; perform this check before doing anything else. [DONE]
     * */
    public static void reset(String[] args) {
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        Repository repository = Repository.create(utilities, gitletPaths);
        HEAD head = new HEAD(utilities, gitletPaths);
        WorkingArea workingArea = new WorkingArea(utilities, gitletPaths);
        StagingArea stagingArea = new StagingArea(utilities, gitletPaths);

        String hash = args[1];
        if (!Repository.isInRepository(hash, gitletPaths)) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }

        if (workingArea.hasUntrackedFile(stagingArea)) {
            System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
            System.exit(0);
        }

        workingArea.clear();

        List<Object> blobs = Commit.getBlobs(hash, utilities);
        for (Object rowBlob : blobs) {
            String blobHash = (String) rowBlob;
            Blob blob = Blob.getBlob(blobHash, utilities);
            Path file = Path.of(gitletPaths.getWorkingDirectory().toString(), blob.getFileName());

            utilities.writeContents(file.toFile(), blob.getContent());
            stagingArea.stagManually(blob.getFileName(), blob.getHash());
        }

        repository.updateBranch(head.getActiveBranchName(), hash);
    }

    static void debug() {
        IStagingArea stagingArea = new StagingArea(new UtilitiesWrapper(), new GitletPathsWrapper());
        System.out.println(stagingArea.loadStagingArea());
    }
}
