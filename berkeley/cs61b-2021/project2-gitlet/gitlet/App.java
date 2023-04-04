package gitlet;

import gitlet.interfaces.*;

import java.io.IOException;
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
    public static void init() {
        if (LocalRepositoryManager.isgitletExists()) {
            throw new RuntimeException("gitlet exists");
        }

        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IStagingArea stagingArea = new StagingArea(utilities, gitletPaths);
        IHEAD head = new HEAD(utilities, gitletPaths);

        LocalRepositoryManager manager = LocalRepositoryManager.create(utilities, stagingArea, head);

        LocalDateTime zeroDate = Instant.ofEpochSecond(0).atZone(ZoneId.of("UTC")).toLocalDateTime();

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
    }

    public static void status() {
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IStagingArea stagingArea = new StagingArea(utilities, gitletPaths);
        IHEAD head = new HEAD(utilities, gitletPaths);
        LocalRepositoryManager manager = LocalRepositoryManager.create(utilities, stagingArea, head);
        manager.showStatus();
    }

    public static void stage(String[] args) {
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IStagingArea stagingArea = new StagingArea(utilities, gitletPaths);
        IHEAD head = new HEAD(utilities, gitletPaths);

        LocalRepositoryManager manager = LocalRepositoryManager.create(utilities, stagingArea, head);

        for (int i = 1; i < args.length; i++) {
            String fileName = args[i];
            Path fullPath = Paths.get(gitletPaths.getWorkingDirectory().toString(), fileName);

            if (!stagingArea.hasFileName(fileName) && !Files.exists(fullPath)) {
                throw new RuntimeException("Can't handle {" + fileName + "} it isn't exist!");
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

        System.out.println(stagingArea.loadStagingArea());
    }

    public static void commit(String[] args) {
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IStagingArea stagingArea = new StagingArea(utilities, gitletPaths);
        IHEAD head = new HEAD(utilities, gitletPaths);

        System.out.println("The content of Staging Tree are:");
        System.out.println(stagingArea.loadStagingArea());

        // Get the commit message and load the staging area
        String commitMessage = args[1];
        HashMap<String, String> readyBlobs = stagingArea.getBlobsReadyToBeCommitted();

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

        String parentHash = head.getHEAD();
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

        head.updateHEAD(committedObject.getHash());

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

    public static void unstage(String[] args) {
        // TODO: this operation supports only one file per-time, so maybe supporting multiple file if possible
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IStagingArea stagingArea = new StagingArea(utilities, gitletPaths);
        HashMap<String, String> currentStagingArea = stagingArea.loadStagingArea();

        Path fullPath = Paths.get(GitletPaths.WORKING_DIRECTORY.toString(), args[1]);
        if (!Files.exists(fullPath)) {
            throw new RuntimeException("Can't handle {" + fullPath.getFileName() + "} it isn't exist!");
        }

        // Extract file info
        String fileName = fullPath.getFileName().toString();
        String fileHash = utilities.sha1(
                utilities.readContentsAsString(
                        fullPath.toFile()
                )
        );

        // TODO: this condition is handle only files in current working directory, no nesting is supported yet
        // If passed file is in the staging area then remove it
        if (currentStagingArea.containsKey(fileName) && currentStagingArea.containsValue(fileHash)) {
            currentStagingArea.remove(fileName);
        }

        stagingArea.updateStagingArea(currentStagingArea);
    }

    public static void log() {
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IHEAD head = new HEAD(utilities, gitletPaths);
        Commit nextCommit = Commit.getCommit(head.getHEAD(), utilities);

        if (nextCommit == null) {
            return;
        }

        while (true) {
            System.out.println(nextCommit);
            if (nextCommit.getParent() == null) {
                break;
            }
            nextCommit = Commit.getCommit(nextCommit.getParent(), utilities);
        }
    }

    public static void find(String[] args) {
        String commitMassage = args[1];
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IHEAD head = new HEAD(utilities, gitletPaths);
        Commit nextCommit = Commit.getCommit(head.getHEAD(), utilities);

        if (nextCommit == null) {
            return;
        }

        while (true) {
            if (nextCommit.getMessage().equals(commitMassage)) {
                System.out.println(nextCommit.getHash());
            }
            if (nextCommit.getParent() == null) {
                break;
            }
            nextCommit = Commit.getCommit(nextCommit.getParent(), utilities);
        }
    }

    // TODO: move it to working directory class
    public static Blob findBlob(String fileName, Tree tree, IUtilitiesWrapper utilities) {
        List<Object> blobsInTree = tree.getContent();
        for (int i = 0; i < blobsInTree.size(); i++) {
            Blob blob = Blob.getBlob((String) blobsInTree.get(i), utilities);
            if (blob.getFileName().equals(fileName)) {
                return blob;
            }
        }

        return null;
    }

    public static void checkout(String[] args) {
        String fileName = args[2];
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IHEAD head = new HEAD(utilities, gitletPaths);
        Commit currentCommit = Commit.getCommit(head.getHEAD(), utilities);

        if (currentCommit == null) {
            return;
        }

        Tree tree = Tree.getTree(currentCommit.getTree(), utilities);
        Blob targetBlob = findBlob(fileName, tree, utilities);

        if (targetBlob != null) {
            // Remove the file from working directory if exist
            Path filePath = Paths.get(GitletPaths.WORKING_DIRECTORY.toString(), fileName);

            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                System.out.println("file not found...but continue anyway");
            }

            utilities.writeContents(filePath.toFile(), targetBlob.getContent());
        }

        // Brunt-force logic to remove all file in working directory
//        Tree tree = Tree.getTree(nextCommit.getTree(), utilities);
//        String [] treeBlobs = tree.getContent();
//        for (int i = 0; i < treeBlobs.length; i++) {
//            Blob blob = Blob.getBlob(treeBlobs[i], utilities);
//            if (blob.getFileName().equals(fileName)) {
//
//                // Delete working directory content "files only"
//                File workingDirectory = GitletPaths.WORKING_DIRECTORY.toFile();
//                List<String> fileNames = utilities.plainFilenamesIn(workingDirectory);
//
//                for (String file : fileNames) {
//                    System.out.println(file);
//                }
//            }
//        }
    }

    static void debug() {
        IStagingArea stagingArea = new StagingArea(new UtilitiesWrapper(), new GitletPathsWrapper());
        System.out.println(stagingArea.loadStagingArea());
    }
}
