package gitlet.app;

import gitlet.Utils;
import gitlet.interfaces.IGLStagingEntry;
import gitlet.objects.Blob;
import gitlet.objects.Commit;
import gitlet.trees.Repository;
import gitlet.trees.WorkingArea;
import gitlet.trees.extra.Branch;
import gitlet.trees.extra.HEAD;
import gitlet.trees.staging.StagingArea;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
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
     *   - Adds a copy of the file as it currently exists to the staging area
     *      (see the description of the commit command) [DONE]
     *
     *   - Staging an already-staged file overwrites the previous entry
     *      in the staging area with the new contents [DONE]
     *
     *   - The staging area should be somewhere in .gitlet [DONE]
     *
     *   - If the current working version of the file is identical to the version in the current commit, do not stage
     *      it to be added, and remove it from the staging area if it is already there
     *      (as can happen when a file is changed, added, and then changed back to it’s original version) [DONE]
     *
     *   - The file will no longer be staged for removal (see gitlet rm),
     *      if it was at the time of the command rm. [DONE]
     *
     *   - If the file does not exist, print the error message File does not exist.
     *      and exit without changing anything. [DONE]
     *
     *   - 20 lines
     * */
    //DONE
    public static void add(String fileName) {

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
    public static void commit(String msg) {
        StagingArea stagingArea = new StagingArea();

        if (stagingArea.isClean()) {
            Utils.exit("No changes added to the commit.");
        }

        String message = msg.trim();

        if (message.isEmpty()) {
            Utils.exit("Please enter a commit message.");
        }

        stagingArea.commitStagedFiles(message);
    }

    /*
     *   rm: https://sp21.datastructur.es/materials/proj/proj2/proj2#rm
     *
     *   - Unstage the file if it is currently staged for addition. [DONE]
     *
     *   - If the file is tracked in the current commit, stage it for removal
     *      and remove the file from the working directory if the user has not already done so [DONE]
     *
     *   - If the file is neither staged nor tracked by the head commit,
     *      print the error message No reason to remove the file. [DONE]
     * */
    // DONE
    public static void rm(String file) {

        StagingArea stagingArea = new StagingArea();

        // This file untracked by Gitlet yet
        if (!stagingArea.isTracked(file)) {
            Utils.exit("No reason to remove the file.");
        }

        /*
         *   We have two cases
         *   - The file is staged (exist in staging area),
         *       then this will be deleted from staging area, to be untracked.
         *   - The file is in the current commit (exist in last commit),
         *       then it will be added to removals (staged for to be removed in the next commit),
         *       and removed from the working directory
         * */

        if (stagingArea.isTracked(file)) {
            // If the file in last commit stage it to be removed, in next commit
            // File in the last commit means the staging area is clean, and it has this file "tracked"
            // Only when the file is committed, it staged for removal
            if (stagingArea.existsInLastCommit(file)) {
                WorkingArea.remove(file);
                stagingArea.stageForRemoval(file);
            }

            // In both cases we need to remove it form the additions
            stagingArea.deleteAdditionsEntry(file);
        }

        stagingArea.saveChanges();
    }

    /* log: https://sp21.datastructur.es/materials/proj/proj2/proj2#log
     *
     *  - Starting at the current head commit, display information about each commit backwards along
     *      the commit tree until the initial commit, following the first parent commit links. [DONE]
     *
     *  - Ignoring any second parents found in merge commits. [DONE]
     *
     *  - For every node in this history, the information it should display is the commit id,
     *      the time the commit was made, and the commit message. [DONE]
     *
     *  - There is a === before each commit and an empty line after it [DONE]
     *
     *  - The timestamps displayed in the commits reflect the current timezone, not UTC; as a result,
     *      the timestamp for the initial commit does not read Thursday, January 1st, 1970, 00:00:00,
     *      but rather the equivalent Pacific Standard Time. Your timezone might be different
     *      depending on where you live, and that’s fine. [DONE]
     *
     *  - Merge field. [DONE]
     * */
    // DONE
    public static void log() {
        Commit commit = Repository.getObject(HEAD.getHash(), Commit.class);

        while (commit != null) {

            AppUtils.printFormatted(commit);

            if (commit.getMergeMessage() != null) {
                // TODO fix this odd logic, this bug happened after merging
                String first = commit.getMergeFirstParent();
                commit = Repository.getObject(commit.getMergeSecondParent(), Commit.class);
                AppUtils.printFormatted(Repository.getObject(first, Commit.class));
            } else {
                commit = Repository.getObject(commit.getParent(), Commit.class);
            }
        }
    }

    /* global-log: https://sp21.datastructur.es/materials/proj/proj2/proj2#global-log
     *
     *   - Like log, except displays information about all commits ever made.
     *      The order of the commits does not matter. [DONE]
     *   - There is a useful method in gitlet.Utils that will help you iterate over files within a directory. [DONE]
     *   - Merge field. [DONE]
     * */
    // DONE
    public static void globalLog() {
        for (Path path : AppUtils.listDirectoriesOfDirectory(Path.of(Repository.OBJECTS))) {
            for (String object : Utils.plainFilenamesIn(path.toFile())) {
                File objectPath = new File(path.toString(), object);
                Serializable genericObject = Utils.readObject(objectPath);
                if (Utils.isCommit(genericObject)) {
                    AppUtils.printFormatted((Commit) genericObject);
                }
            }
        }
    }

    /* find: https://sp21.datastructur.es/materials/proj/proj2/proj2#find
     *
     *  - Prints out the ids of all commits that have the given commit message, one per line. [DONE]
     *
     *  - If there are multiple such commits, it prints the ids out on separate lines. [DONE]
     *
     *  - The commit message is a single operand; to indicate a multiword message,
     *      put the operand in quotation marks, as for the commit command below.
     *      Hint: the hint for this command is the same as the one for global-log. [DONE]
     *
     *  - If no such commit exists, prints the error message Found no commit with that message. [DONE]
     *
     *  - Doesn’t exist in real git. Similar effects can be achieved by grepping the output of log. [DONE]
     * */
    // DONE
    public static void find(String[] args) {
        String message = args[1];
        boolean commitFound = false;
        boolean print = true;

        for (Path path : AppUtils.listDirectoriesOfDirectory(Path.of(Repository.OBJECTS))) {
            for (String object : Utils.plainFilenamesIn(path.toFile())) {
                File objectPath = new File(path.toString(), object);
                Serializable genericObject = Utils.readObject(objectPath);
                if (Utils.isCommit(genericObject)) {
                    Commit commit = (Commit) genericObject;
                    if (commit.getMessage().equals(message)) {
                        if (print) {
                            System.out.println(message);
                            print = false;
                        }
                        System.out.println(commit.getHash());
                        commitFound = true;
                    }
                }
            }
        }

        if (!commitFound) {
            System.out.println("Found no commit with that message.");
        }
    }

    /* status: https://sp21.datastructur.es/materials/proj/proj2/proj2#status
     *
     *  - Displays what branches currently exist, and marks the current branch with a *. [DONE]
     *
     *  - Displays what files have been staged for addition or removal. [DONE]
     *
     *  - The last two sections (modifications not staged and untracked files) are extra credit,
     *      worth 32 points. Feel free to leave them blank (leaving just the headers). [DONE]
     *
     *  - There is an empty line between sections, and the entire status ends in an empty line as well. [DONE]
     *
     *  - Entries should be listed in lexicographic order,
     *      using the Java string-comparison order (the asterisk doesn’t count). [DONE]
     *
     *  - Runtime: Make sure this depends only on the amount of data in the working directory
     *      plus the number of files staged to be added or deleted plus the number of branches. [DONE]
     *
     * - READ: https://sp21.datastructur.es/materials/proj/proj2/proj2#status for more details
     * */
    // DONE
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
        for (Map.Entry<String, IGLStagingEntry> entry : stagingArea.getStagedFiles()) {
            String file = entry.getKey();
            System.out.println(file);
        }

        System.out.println("\n=== Removed Files ===");
        for (Map.Entry<String, IGLStagingEntry> entry : stagingArea.getRemovedFiles()) {
            String file = entry.getKey();
            System.out.println(file);
        }

        System.out.println("\n=== Modifications Not Staged For Commit ===");
        for (Map.Entry<String, String> entry : stagingArea.getModifiedFiles()) {
            String fileName = entry.getKey();
            String fileStatus = entry.getValue();

            System.out.println(fileName + " " + fileStatus);
        }

        System.out.println("\n=== Untracked Files ===");
        for (Map.Entry<String, IGLStagingEntry> entry : stagingArea.getUntrackedFiles()) {
            String file = entry.getKey();
            System.out.println(file);
        }
    }

    /* checkout: https://sp21.datastructur.es/materials/proj/proj2/proj2#checkout
     *
     *  - However, that you poke around in a .git directory (specifically, .git/objects) and see how
     *      it manages to speed up its search. You will perhaps recognize a familiar data structure
     *      implemented with the file system rather than pointers. [DONE]
     *
     *  - Only version 3 (checkout of a full branch) modifies the staging area:
     *      otherwise files scheduled for addition or removal remain so. [DONE]
     * */
    // DONE
    public static void checkout(String[] args) {
        /*
         * - Takes all files in the commit at the head of the given branch,
         *      and puts them in the working directory, overwriting the versions of
         *      the files that are already there if they exist. [DONE]
         *
         * - Also, at the end of this command, the given branch
         *      will now be considered the current branch (HEAD). [DONE]
         *
         * - Any files that are tracked in the current branch but are
         *      not present in the checked-out branch are deleted. [DONE]
         *
         * - The staging area is cleared, unless the checked-out branch is the current branch [DONE]
         *
         * - If no branch with that name exists, print No such branch exists. [DONE]
         *
         * - If that branch is the current branch, print No need to checkout the current branch. [DONE]
         *
         * -  If a working file is untracked in the current branch
         *      and would be overwritten by the checkout,
         *      print There is an untracked file in the way; delete it, or add and commit it first. and exit;
         *      perform this check before doing anything else. Do not change the CWD. [DONE]
         * */
        // Checkout branch
        // DONE
        if (args.length == 2) {

            String branch = args[1];

            if (!Branch.exists(branch)) {
                Utils.exit("No such branch exists.");
            }

            if (HEAD.isPoint(branch)) {
                Utils.exit("No need to checkout the current branch.");
            }

            Repository.switchTo(Branch.getHash(branch));

            HEAD.move(branch);
        }

        /*
         *  - Takes the version of the file as it exists in the head commit and puts it in the working directory,
         *      overwriting the version of the file that’s already there if there is one. [DONE]
         *
         *  - The new version of the file is not staged. [DONE]
         *
         *  - If the file does not exist in the previous commit, abort, printing
         *      the error message File does not exist in that commit. Do not change the CWD. [DONE]
         * */
        // Checkout file
        // DONE
        if (args.length == 3 && args[1].equals("--")) {
            String file = args[2];
            TreeMap<String, Blob> blobs = Repository.getLastCommitBlobs();

            if (!blobs.containsKey(file)) {
                Utils.exit("File does not exist in that commit.");
            }

            WorkingArea workingArea = new WorkingArea();
            workingArea.addBlob(blobs.get(file));
        }

        /*
         *  - Takes the version of the file as it exists in the commit with the given id,
         *      and puts it in the working directory, overwriting the version of the file
         *      that’s already there if there is one. [DONE]
         *
         *  - The new version of the file is not staged.
         *      (Which means the file will show in "status" modified) [DONE]
         *
         *  - If the file does not exist in the given commit,
         *      print File does not exist in that commit. Do not change the CWD.[DONE]
         *
         * - Shorthand support [DONE]
         * */
        // Checkout commit hash and file
        // DONE
        if (args.length == 4 && args[2].equals("--")) {
            String shorthand = args[1];
            String file = args[3];

            String hash = Repository.getObjectFullHash(shorthand);

            if (hash == null) {
                Utils.exit("No commit with that id exists.");
            }

            Blob blob = Repository.getBlob(file, hash);
            if (blob == null) {
                Utils.exit("File does not exist in that commit.");
            }

            TreeMap<String, Blob> blobs = Repository.getBlobs(hash);

            WorkingArea workingArea = new WorkingArea();
            workingArea.addBlob(blobs.get(file));

            Branch.update(HEAD.getName(), hash);
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
     * - Failure cases: If a branch with the given name already exists,
     *      print the error message A branch with that name already exists. [DONE]
     * */
    // DONE
    public static void branch(String branchName) {

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
     *  - Deletes the branch with the given name. [DONE]
     *
     *  - This only means to delete the pointer associated with the branch;
     *      it does not mean to delete all commits that were created under the branch,
     *      or anything like that. [DONE]
     *
     *  - If a branch with the given name does not exist, aborts.
     *      Print the error message A branch with that name does not exist. [DONE]
     *
     *  - If you try to remove the branch you’re currently on, aborts,
     *      printing the error message Cannot remove the current branch. [DONE]
     * */
    // DONE
    public static void removeBranch(String[] args) {
        String branch = args[1];

        if (!Branch.exists(branch)) {
            Utils.exit("A branch with that name does not exist.");
        }

        if (HEAD.isPoint(branch)) {
            Utils.exit("Cannot remove the current branch.");
        }

        Branch.remove(branch);
    }

    /* reset: https://sp21.datastructur.es/materials/proj/proj2/proj2#reset
     *
     *  - Checks out all the files tracked by the given commit.
     *      Removes tracked files that are not present in that commit. [DONE]
     *
     *  - Also moves the current branch’s head to that commit node.
     *      See the intro for an example of what happens to the head pointer after using reset. [DONE]
     *
     *  - The [commit id] may be abbreviated as for checkout/hash shorthand. [DONE]
     *
     *  - The staging area is cleared. [DONE]
     *
     *  - If no commit with the given id exists,
     *      print No commit with that id exists. [DONE]
     *
     *  - If a working file is untracked in the current branch and would be overwritten by the reset,
     *      print `There is an untracked file in the way; delete it, or add and commit it first.
     *      and exit; perform this check before doing anything else. [DONE]
     * */
    // DONE
    public static void reset(String[] args) {

        String shorthand = args[1];

        String hash = Repository.getObjectFullHash(shorthand);

        if (hash == null) {
            Utils.exit("No commit with that id exists.");
        }

        if (!Repository.objectExists(hash)) {
            Utils.exit("No commit with that id exists.");
        }

        Repository.switchTo(hash);

        Branch.update(HEAD.getName(), hash);
    }

    /* merge: https://sp21.datastructur.es/materials/proj/proj2/proj2#merge
     *
     *  - If the split point is the same commit as the given branch, then we do nothing; the merge is complete,
     *      and the operation ends with the message Given branch is an ancestor of the current branch. [DONE]
     *
     *  - If the split point is the current branch, then the effect is to check out the given branch,
     *      and the operation ends after printing the message Current branch fast-forwarded. [DONE]
     *
     *  - If a branch with the given name does not exist, print the error
     *      message A branch with that name does not exist. [DONE]
     *
     *  - If attempting to merge a branch with itself,
     *      print the error message Cannot merge a branch with itself. [DONE]
     *
     *  - If an untracked file in the current commit would be overwritten or deleted by the merge,
     *      print There is an untracked file in the way; delete it, or add and commit it first.
     *      and exit; perform this check before doing anything else.
     *
     *  - If there are staged additions or removals present,
     *      print the error message You have uncommitted changes. and exit. TODO
     *
     *  - Enhance LCA algorithm TODO
     *
     *  - Cases NOT needed to be handled
     *      - Real Git does a more subtle job of merging files,
     *          displaying conflicts only in places where both files have changed since the split point.
     *
     *      - Real Git has a different way to decide which of multiple possible split points to use.
     *
     *      - Real Git will force the user to resolve the merge conflicts before committing to complete the merge.
     *          Gitlet just commits the merge, conflicts and all,
     *          so that you must use a separate commit to resolve problems.
     *
     *      - Real Git will complain if there are unstaged changes to a file that would be changed by a merge.
     *          You may do so as well if you want, but we will not test that case.
     *
     * */
    public static void merge(String other) {

        if (!Branch.exists(other)) {
            Utils.exit("A branch with that name does not exist.");
        }

        if (other.equals(HEAD.getName())) {
            Utils.exit("Cannot merge a branch with itself.");
        }

        Repository.merge(other);
    }
}
