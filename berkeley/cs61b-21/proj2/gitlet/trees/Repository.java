package gitlet.trees;

import gitlet.MergeEntry;
import gitlet.MergeTracker;
import gitlet.Utils;
import gitlet.interfaces.IBlob;
import gitlet.interfaces.ICommit;
import gitlet.interfaces.ITree;
import gitlet.objects.Blob;
import gitlet.objects.Commit;
import gitlet.objects.Tree;
import gitlet.trees.extra.Branch;
import gitlet.trees.extra.HEAD;
import gitlet.trees.staging.StagingArea;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Repository {

    public final static String GITLET = Path.of(WorkingArea.WD, ".gitlet").toString();
    public final static String INDEX = Path.of(GITLET, "index").toString();
    public final static String OBJECTS = Path.of(GITLET, "objects").toString();
    public final static String HEAD_POINTER = Path.of(GITLET, "HEAD").toString();
    public final static String BRANCHES = Path.of(GITLET, "refs", "heads").toString();


    public static <T extends Serializable> T getObject(String hash, Class<T> type) {
        if (hash == null) {
            return null;
        }

        if (!Repository.objectExists(hash)) {
            return null;
        }

        String[] path = Utils.splitHash(hash, Utils.HASH_TWO);
        String directory = path[0];
        String object = path[1];

        return Utils.readObject(new File(Path.of(Repository.getObjectPath(directory).toString(), object).toString()), type);
    }

    public static boolean gitletDirectoryExist(String path) {
        return Files.exists(Path.of(path));
    }

    public static Path getObjectPath(String file) {
        return Path.of(Repository.OBJECTS, file);
    }

    public static Path getBranchesPath(String file) {
        return Path.of(Repository.BRANCHES, file);
    }

    public static Path getHeadPath(String file) {
        return Path.of(Repository.HEAD_POINTER, file);
    }

    public static Path getIndexPath(String file) {
        return Path.of(Repository.INDEX, file);
    }

    public static void switchTo(String hash) {
        WorkingArea workingArea = new WorkingArea();
        StagingArea stagingArea = new StagingArea();

        TreeMap<String, Blob> blobs = getBlobs(hash);

        // Prevent overwriting untracked file that is in the branch that will be switched to
        for (String file : WorkingArea.getWorkingFiles()) {

            // The file should be untracked and exits in the working area
            if (!stagingArea.isTracked(file) && blobs.containsKey(file)) {
                Utils.exit("There is an untracked file in the way; delete it, or add and commit it first.");
            }
        }

        workingArea.clear();

        // If the checkout is the root commit, there aren't blobs to write
        // in the working directory
        Commit commit = new Commit();
        Commit rootCommit = commit.getRootCommit(false);
        if (!hash.equals(rootCommit.getHash())) {
            workingArea.addBlobs(blobs);
        }
    }

    public static void switchTo(String hash, String file) {
    }

    public static void initialize() {
        Repository.checkGitletRepository();
        Repository.initializeCorePaths();
        StagingArea.initialize();
        Commit commit = new Commit();
        Commit rootCommit = commit.getRootCommit(true);
        Branch.create("master", rootCommit.getHash());
        HEAD.move("master");
    }

    public static Blob getBlob(String file, String hash) {
        Commit commit = Repository.getObject(hash, Commit.class);
        Tree commitTree = Repository.getObject(commit.getTree(), Tree.class);
        for (Object blobHash : commitTree.getBlobs()) {
            Blob blob = Repository.getObject((String) blobHash, Blob.class);
            if (blob.getFileName().equals(file)) {
                return blob;
            }
        }
        return null;
    }

    public static TreeMap<String, Blob> getBlobs(String hash) {
        TreeMap<String, Blob> blobs = new TreeMap<>();

        Commit commit = Repository.getObject(hash, Commit.class);
        Tree commitTree = Repository.getObject(commit.getTree(), Tree.class);

        for (Object blobHash : commitTree.getBlobs()) {
            Blob blob = Repository.getObject((String) blobHash, Blob.class);
            blobs.put(blob.getFileName(), blob);
        }

        return blobs;
    }

    public static void commit(IBlob blob, ITree tree, ICommit commit) {
        storeObject(blob.getHash(), blob);
        storeObject(tree.getHash(), tree);
        storeObject(commit.getHash(), commit);
    }

    public static void commit(ITree tree, ICommit commit) {
        storeObject(tree.getHash(), tree);
        storeObject(commit.getHash(), commit);
    }

    public static ICommit commit(ICommit commit, ITree tree, List<? extends IBlob> blobs) {
        for (IBlob blob : blobs) {
            storeObject(blob.getHash(), blob);
        }

        storeObject(tree.getHash(), tree);
        storeObject(commit.getHash(), commit);

        return commit;
    }

    public static void storeObject(String hash, Serializable object) {
        String[] path = Utils.splitHash(hash, Utils.HASH_TWO);
        String directory = path[0];
        String file = path[1];
        String directoryFullPath = Repository.getObjectPath(directory).toString();
        String fileFullPath = Path.of(directoryFullPath, file).toString();

        if (Repository.directoryExists(directory)) {
            Utils.writeObject(new File(fileFullPath), object);
        } else {
            try {
                Files.createDirectory(Path.of(directoryFullPath));
                Utils.writeObject(new File(fileFullPath), object);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void initializeCorePaths() {

        try {
            Files.createDirectory(

                    Path.of(Repository.GITLET)
            );
            Files.createDirectory(
                    Path.of(Repository.OBJECTS)
            );
            Files.createDirectories(
                    Path.of(Repository.BRANCHES)
            );
            Files.createFile(
                    Path.of(Repository.HEAD_POINTER)
            );
            Files.createFile(
                    Path.of(Repository.INDEX)
            );


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkGitletRepository() {
        if (Files.exists(Path.of(Repository.GITLET))) {
            Utils.exit("A Gitlet version-control system already exists in the current directory.");
        }
    }

    public static boolean directoryExists(String directory) {
        return Files.exists(getObjectPath(directory));
    }

    public static boolean objectExists(String hash) {
        String[] path = Utils.splitHash(hash, Utils.HASH_TWO);
        String directory = path[0];
        String object = path[1];

        String filePath = Path.of(getObjectPath(directory).toString(), object).toString();
        return Files.exists(Path.of(filePath));
    }

    public static TreeMap<String, Blob> getLastCommitBlobs() {
        return getBlobs(HEAD.getHash());
    }

    public static void merge(String other) {
//        String c = HEAD.getHash(),o = Branch.getHash(other);
        Commit currentCommit = getObject(HEAD.getHash(), Commit.class);
        Commit otherCommit = getObject(Branch.getHash(other), Commit.class);

        String LCA = Utils.findLCA(currentCommit, otherCommit);

        String givenBranch = Branch.getHash(other);

        // If the split point is the same commit as the given branch
        if (LCA.equals(givenBranch)) {

            Utils.exit("Given branch is an ancestor of the current branch.");
        }

        String currentBranch = Branch.getHash(HEAD.getName());

        // If the split point is the current branch
        if (currentBranch.equals(LCA)) {

            // fast-forward the current branch to point on given branch
            Repository.switchTo(givenBranch);
            Branch.update(HEAD.getName(), givenBranch);

            Utils.exit("Current branch fast-forwarded.");
        }

        // Files in all commits (parent, head, and other)
        TreeMap<String, String> files = new TreeMap<>();
        TreeMap<String, Blob> parentBlobs = Repository.getBlobs(LCA);
        TreeMap<String, Blob> currentBlobs = Repository.getBlobs(currentBranch);
        TreeMap<String, Blob> givenBlobs = Repository.getBlobs(givenBranch);

        for (Map.Entry<String, Blob> parentEntry : parentBlobs.entrySet()) {
            files.put(parentEntry.getKey(), parentEntry.getKey());
        }

        for (Map.Entry<String, Blob> currentEntry : currentBlobs.entrySet()) {
            files.put(currentEntry.getKey(), currentEntry.getKey());
        }

        for (Map.Entry<String, Blob> givenEntry : givenBlobs.entrySet()) {
            files.put(givenEntry.getKey(), givenEntry.getKey());
        }

        StagingArea stagingArea = new StagingArea();
        WorkingArea workingArea = new WorkingArea();

        for (Map.Entry<String, String> filesEntry : files.entrySet()) {
            String fileName = filesEntry.getKey();

            Blob parentBlob = Repository.getBlob(fileName, LCA);
            Blob currentBlob = Repository.getBlob(fileName, currentBranch);
            Blob givenBlob = Repository.getBlob(fileName, givenBranch);

            // Parent -> A AND Current -> A AND Given -> !A = Given (!A) "STAGING for ADDITION"
            if (
                    parentBlob != null &&
                            currentBlob != null &&
                            givenBlob != null &&
                            parentBlob.getHash().equals(currentBlob.getHash()) &&
                            !parentBlob.getHash().equals(givenBlob.getHash())
            ) {
                workingArea.addBlob(givenBlob);
                stagingArea.stageForAddition(givenBlob);
            }
            // Parent -> B AND Current -> !B AND Given -> B = Current (!B) "No STAGING & DELETION"
//            else if (parentBlob != null && currentBlob != null && givenBlob != null && !parentBlob.getHash().equals(currentBlob.getHash()) && parentBlob.getHash().equals(givenBlob.getHash())) {
//                System.out.println("CASE 2 - Nothing");
////                workingArea.addBlob(currentBlob);
////                tree.addBlob(currentBlob.getHash());
//            }
            // Parent -> C AND Current -> Null AND Given -> Null = (Current | Given) Null "STAGING for DELETION"
            // Nothing
//            else if (
//                    (
//                            parentBlob != null &&
//                            currentBlob != null &&
//                            givenBlob != null &&
//                            !parentBlob.getHash().equals(currentBlob.getHash()) &&
//                            !parentBlob.getHash().equals(givenBlob.getHash()) &&
//                            givenBlob.getHash().equals(currentBlob.getHash())
//                    )
//                    ||
//                    (
//                            parentBlob != null &&
//                            currentBlob == null &&
//                            givenBlob == null &&
//                            WorkingArea.exists(parentBlob.getFileName())
//                    )
//            ) {
//                System.out.println("CASE 3.1");
////                stagingArea.stageForRemoval(parentBlob.getFileName());
////                WorkingArea.remove(parentBlob.getFileName());
////                stagingArea.deleteAdditionsEntry(parentBlob.getFileName());
//            }
            // Parent -> D AND Current -> D AND Given -> Null = "STAGING for DELETION"
            else if (parentBlob != null && currentBlob != null && parentBlob.getHash().equals(currentBlob.getHash()) && givenBlob == null) {
                // Or stagingArea.stageForRemoval(currentBlob.getFileName());
                stagingArea.stageForRemoval(parentBlob.getFileName());
                WorkingArea.remove(parentBlob.getFileName());
                stagingArea.deleteAdditionsEntry(parentBlob.getFileName());
            }
            // Parent -> E AND Current -> Null AND Given -> E = Nothing like B
            // Nothing
//            else if (parentBlob != null && givenBlob != null && parentBlob.getHash().equals(givenBlob.getHash()) && currentBlob == null) {
//                System.out.println("CASE 7");
//                WorkingArea.remove(parentBlob.getFileName());
//            }
            // Parent -> Null AND Current -> Null AND Given -> F|!F = "STAGING for ADDITION" F|!F
            else if (parentBlob == null && currentBlob == null && givenBlob != null) {
                workingArea.addBlob(givenBlob);
                stagingArea.stageForAddition(givenBlob);
            }
            // Parent -> Null AND Current -> G AND Given -> Null = G
            // Nothing
//            else if (parentBlob == null && currentBlob != null && givenBlob == null) {
//                System.out.println("CASE 4");
////                stagingArea.stageForAddition(currentBlob);
////                tree.addBlob(currentBlob.getHash());
//            }
            else if (
                    (
                            parentBlob != null &&
                                    currentBlob != null && givenBlob != null &&
                                    !parentBlob.getHash().equals(currentBlob.getHash()) &&
                                    !parentBlob.getHash().equals(givenBlob.getHash()) &&
                                    !currentBlob.getHash().equals(givenBlob.getHash())
                    )
                            ||
                            (
                                    parentBlob != null &&
                                            currentBlob == null && givenBlob != null &&
                                            !parentBlob.getHash().equals(givenBlob.getHash())
                            )
                            ||
                            (
                                    parentBlob != null &&
                                            currentBlob != null && givenBlob == null &&
                                            !parentBlob.getHash().equals(currentBlob.getHash())
                            )
                            ||
                            (
                                    parentBlob == null &&
                                            currentBlob != null && givenBlob != null &&
                                            !currentBlob.getHash().equals(givenBlob.getHash())
                            )

            ) {

                String current = currentBlob == null ? "" : new String(currentBlob.getFileContent());
                String given = givenBlob == null ? "" : new String(givenBlob.getFileContent());
                String conflictData = "<<<<<<< " + "HEAD" + "\n" +
                        current
                        + "\n" + "=======" + "\n" +
                        given
                        + "\n" +
                        ">>>>>>> " + other;

                workingArea.addBlob(currentBlob, conflictData);
                MergeEntry mergeEntry = new MergeEntry(givenBranch, currentBranch, HEAD.getName(), other);

                MergeTracker.writeEntry(mergeEntry);
                Utils.exit("Encountered a merge conflict.");
            }
        }

        stagingArea.commitStagedFiles("MERGED " + currentBranch + " " + givenBranch);
    }

    public static String getObjectFullHash(String shorthand) {
        // Safe shorthand length
        if (shorthand.length() < 4) {
            return null;
        }

        String[] splittedHash = Utils.splitHash(shorthand, Utils.HASH_TWO);

        if (splittedHash == null) {
            return null;
        }

        String directory = splittedHash[0];

        if (!directoryExists(directory)) {
            Utils.exit("No commit with that id exists.");
        }

        List<String> directoryObjects = Utils.plainFilenamesIn(getObjectPath(directory).toString());

        String objectHash = splittedHash[1];

        for (String file : directoryObjects) {
            String object = Utils.matches(objectHash, file);
            if (object == null) {
                return null;
            }
            return directory + object;
        }

        return null;
    }
}
