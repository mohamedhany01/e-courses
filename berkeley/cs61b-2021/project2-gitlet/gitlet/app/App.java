package gitlet.app;

import gitlet.Utils;
import gitlet.interfaces.ICommit;
import gitlet.objects.Blob;
import gitlet.objects.Commit;
import gitlet.objects.Tree;
import gitlet.trees.Repository;
import gitlet.trees.WorkingArea;
import gitlet.trees.extra.Branch;
import gitlet.trees.extra.HEAD;
import gitlet.trees.staging.StagingArea;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class App {

    /*  init: https://sp21.datastructur.es/materials/proj/proj2/proj2#init
     *
     *   - A commit that contains no files and has the commit message initial commit [DONE]
     *
     *   - It will have a single branch: master, which initially points to this initial commit,
     *      and master will be the current branch [DONE]
     *
     *   - The timestamp for this initial commit will be 00:00:00 UTC, Thursday, 1 January 1970} [DONE]
     *
     *   - If there is already a Gitlet version-control system in the current directory, it should abort [DONE]
     *
     *   - It should NOT overwrite the existing system with a new one,
     *      Should print the error message A Gitlet version-control system already exists in the current directory. [DONE]
     *
     *   - Line count: ~15
     * */
    // DONE
    public static void init() {
        Repository.initialize();
    }

    /*  add: https://sp21.datastructur.es/materials/proj/proj2/proj2#add
     *
     *   - Adds a copy of the file as it currently exists to the staging area (see the description of the commit command) [DONE]
     *
     *   - Staging an already-staged file overwrites the previous entry in the staging area with the new contents [DONE]
     *
     *   - The staging area should be somewhere in .gitlet [DONE]
     *
     *   - If the current working version of the file is identical to the version in the current commit, do not stage
     *      it to be added, and remove it from the staging area if it is already there
     *      (as can happen when a file is changed, added, and then changed back to it’s original version) [DONE]
     *
     *   - The file will no longer be staged for removal (see gitlet rm), if it was at the time of the command rm. [DONE]
     *
     *   - If the file does not exist, print the error message File does not exist. and exit without changing anything. [DONE]
     *
     *   - 20 lines
     * */
    //DONE
    public static void add(String[] args) {
        String fileName = args[1];

        if (!WorkingArea.exists(fileName)) {
            Utils.exit("File does not exist.");
        }

        Blob blob = new Blob();
        blob.setFileName(fileName);

        StagingArea stagingArea = new StagingArea();
        stagingArea.stageForAddition(blob);
        stagingArea.saveChanges();
    }

    /* commit: https://sp21.datastructur.es/materials/proj/proj2/proj2#commit
     *
     *   - Saves a snapshot of tracked files in the current commit and staging area so they-can be
     *      restored at a later time [DONE]
     *
     *   - A commit will only update the contents of files it is tracking that have been staged
     *      for addition at the time of commit, in which case the commit will now include
     *      the version of the file that was staged instead of the version it got from its parent [DONE]
     *
     *   - A commit will save and start tracking any files that were staged for addition
     *      but weren’t tracked by its parent. [DONE]
     *
     *   - Files tracked in the current commit may be untracked in the
     *      new commit as a result being staged for removal by the rm command. [DONE]
     *
     *   - The staging area is cleared after a commit. [DONE]
     *
     *   - The commit command never adds, changes, or removes files in
     *      the working directory (other than those in the .gitlet directory). [DONE]
     *
     *   - The rm command will remove such files, as well as staging them for removal,
     *      so that they will be untracked after a commit. [DONE]
     *
     *   - Any changes made to files after staging for addition or removal are ignored
     *      by the commit command, which only modifies the contents of the .gitlet directory. [DONE]
     *
     *   - After the commit command, the new commit is added as a new node in the commit tree. [DONE]
     *
     *   - The commit just made becomes the “current commit”, and the head pointer now points to it.
     *      The previous head commit is this commit’s parent commit. [DONE]
     *
     *   - Each commit should contain the date and time it was made. [DONE]
     *
     *   - Each commit is identified by its SHA-1 id, which must include the file (blob) references of
     *      its files, parent reference, log message, and commit time. [DONE]
     *
     *   - If no files have been staged, abort. Print the message No changes added to the commit. [DONE]
     *
     *   - Every commit must have a non-blank message.
     *      If it doesn’t, print the error message Please enter a commit message. [DONE]
     *
     *   - From real git: In real git, commits may have multiple parents (due to merging)
     *      and also have considerably more metadata.
     *
     *  - Line count: ~35 [DONE]
     * */

    // DONE
    public static void commit(String[] args) {
        StagingArea stagingArea = new StagingArea();

        if (stagingArea.isClean()) {
            Utils.exit("No changes added to the commit.");
        }

        String message = args[1].trim();

        if (message.isEmpty()) {
            Utils.exit("Please enter a commit message.");
        }

        stagingArea.commitStagedFiles(message);
    }

    /*
     *   rm: https://sp21.datastructur.es/materials/proj/proj2/proj2#rm
     *   - Unstage the file if it is currently staged for addition. [DONE]
     *   - If the file is tracked in the current commit, stage it for removal
     *      and remove the file from the working directory if the user has not already done so [DONE]
     *   - If the file is neither staged nor tracked by the head commit, print the error message No reason to remove the file. [DONE]
     * */
    public static void rm(String[] args) {

        String fileName = args[1];
        StagingArea stagingArea = new StagingArea();

        // This file untracked by Gitlet yet
        if (!stagingArea.hasFileInAdditions(fileName)) {
            Utils.exit("No reason to remove the file.");
        }

        /*
         *   We have two cases here
         *   - The file is staged (exist in staging area),
         *       then this will be deleted from staging area, to be untracked.
         *   - The file is in the current commit (exist in last commit),
         *       then it will be added to removals (staged for to be removed in the next commit),
         *       and remove it from working directory
         * */

        // Unstage the file to be untracked
        // TODO: in condition add, and if STAGED
        if (stagingArea.hasFileInAdditions(fileName)) {
            stagingArea.deleteAdditionsEntry(fileName);
        }

        // Stage it to be removed in the next commit
        TreeMap<String, String> lastCommitFiles = Repository.getLastCommitFiles();

        if (lastCommitFiles.containsKey(fileName)) {
            stagingArea.stageForRemoval(fileName);
            WorkingArea.remove(fileName);
        }

        stagingArea.saveChanges();
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
        Commit currentCommit = Repository.getObject(HEAD.getHash(), Commit.class);

        while (true) {
            if (currentCommit == null) {
                break;
            }
            AppUtils.formatLog(currentCommit);
            currentCommit = Repository.getObject(currentCommit.getParent(), Commit.class);
        }
    }

    /* log: https://sp21.datastructur.es/materials/proj/proj2/proj2#global-log
     *
     *   - Like log, except displays information about all commits ever made. The order of the commits does not matter. [DONE]
     *   - There is a useful method in gitlet.Utils that will help you iterate over files within a directory. [DONE]
     *   - Merge field. TODO
     * */
    public static void logAll() {
        Path objectsDirectory = Path.of(Repository.OBJECTS);

        // If no objects directory just exit and don't do anything
        if (!Files.exists(objectsDirectory)) {
            System.exit(0);
        }

        // Loop over objects directory, and read all serialized objects
        for (String objectHash : Utils.plainFilenamesIn(objectsDirectory.toFile())) {
            Path objectPath = Path.of(objectsDirectory.toString(), objectHash);

            try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(objectPath.toFile()))) {
                Object rowObject = reader.readObject();

                // If you found any object of type Commit print it
                if (rowObject instanceof Commit commit) {
                    AppUtils.formatLog(commit);
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
        Path objectsDirectory = Path.of(Repository.OBJECTS);

        // If no objects directory just exit and don't do anything
        if (!Files.exists(objectsDirectory)) {
            Utils.exit("");
        }

        // Loop over objects directory, and read all serialized objects
        for (String objectHash : Utils.plainFilenamesIn(objectsDirectory.toFile())) {
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
     * - Displays what files have been staged for addition or removal. [DONE]
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
        StagingArea stagingArea = new StagingArea();
        System.out.println("\n=== Branches ===");
        for (String branch : Branch.getAllBranches()) {
            if (HEAD.isPoint(branch)) {
                System.out.println("*" + branch);
            } else {
                System.out.println(branch);
            }
        }

        System.out.println("\n=== Staged Files ===");
        for (String file : stagingArea.getStagedFiles()) {
            System.out.println(file);
        }

        System.out.println("\n=== Removed Files ===");
        for (String file : stagingArea.getRemovedFiles()) {
            System.out.println(file);
        }

        System.out.println("\n=== Modifications Not Staged For Commit ===");
        for (Map.Entry<String, String> entry : stagingArea.getModifiedFiles()) {
            String fileName = entry.getKey();
            String fileStatus = entry.getValue();

            System.out.println(fileName + " " + fileStatus);
        }

        System.out.println("\n=== Untracked Files ===");
        for (String file : stagingArea.getUntrackedFiles()) {
            System.out.println(file);
        }

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

        Path branches = Path.of(Repository.BRANCHES);

        if (!Files.exists(branches)) {
            Utils.exit("");
        }

        if (Files.exists(Path.of(branches.toString(), branchName))) {
            Utils.exit("A branch with that name already exists.");
        }

        Branch.create(branchName, HEAD.getHash());
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

        if (!Branch.exists(branchName)) {
            Utils.exit("A branch with that name does not exist.");
        }

        if (HEAD.isPoint(branchName)) {
            Utils.exit("Cannot remove the current branch.");
        }

        Branch.remove(branchName);
    }

    /* checkout: https://sp21.datastructur.es/materials/proj/proj2/proj2#checkout
     *
     * - However, that you poke around in a .git directory (specifically, .git/objects) and see how it manages to speed up its search. You will perhaps recognize a familiar data structure implemented with the file system rather than pointers. TODO
     *
     * - Only version 3 (checkout of a full branch) modifies the staging area: otherwise files scheduled for addition or removal remain so. TODO
     * */
    public static void checkout(String[] args) {
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

            if (!Branch.exists(branchName)) {
                Utils.exit("No such branch exists.");
            }

            if (HEAD.isPoint(branchName)) {
                Utils.exit("No need to checkout the current branch.");
            }

            List<Object> blobs = Repository.getBlobs(Branch.getBranchHash(branchName));
            WorkingArea workingArea = new WorkingArea();
            StagingArea stagingArea = new StagingArea();


            if (stagingArea.haveUntrackedFiles()) {
                Utils.exit("There is an untracked file in the way; delete it, or add and commit it first.");
            }

            workingArea.clear();

            for (Object rowBlob : blobs) {
                String blobHash = (String) rowBlob;
                Blob blob = Repository.getObject(blobHash, Blob.class);
                Path file = Path.of(WorkingArea.WD, blob.getFileName());

                Utils.writeContents(file.toFile(), blob.getFileContent());

                stagingArea.stageForAddition(blob);
            }

            HEAD.move(branchName);
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

            Blob blob = Repository.getBlob(fileName, HEAD.getHash());
            if (blob == null) {
                Utils.exit("File does not exist in that commit.");
            }

            // Start restoring the file
            Path fileFullPath = Path.of(WorkingArea.WD, fileName);
            Utils.writeContents(fileFullPath.toFile(), blob.getFileContent());
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
            String commitHash = args[1];
            String fileName = args[3];

            if (!Repository.directoryExists(commitHash)) {
                Utils.exit("No commit with that id exists.");
            }

            Blob blob = Repository.getBlob(fileName, commitHash);
            if (blob == null) {
                Utils.exit("File does not exist in that commit.");
            }

            // Start restoring the file
            Path fileFullPath = Path.of(WorkingArea.WD, fileName);
            Utils.writeContents(fileFullPath.toFile(), blob.getFileContent());
//            stagingArea.stagManually(fileName, blob.getHash()); TODO: should I active this or not? because this command shouldn't update the staging area
            Branch.update(HEAD.getName(), commitHash);
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
        WorkingArea workingArea = new WorkingArea();
        StagingArea stagingArea = new StagingArea();

        String hash = args[1];
        if (!Repository.directoryExists(hash)) {
            Utils.exit("No commit with that id exists.");
        }

        if (stagingArea.haveUntrackedFiles()) {
            Utils.exit("There is an untracked file in the way; delete it, or add and commit it first.");
        }

        workingArea.clear();

        List<Object> blobs = Repository.getBlobs(hash);
        for (Object rowBlob : blobs) {
            Blob blob = Repository.getObject((String) rowBlob, Blob.class);

            Utils.writeContents(new File(WorkingArea.getPath(blob.getFileName()).toString()), blob.getFileContent());

            stagingArea.stageForAddition(blob);
        }

        Branch.update(HEAD.getName(), hash);
    }
}
