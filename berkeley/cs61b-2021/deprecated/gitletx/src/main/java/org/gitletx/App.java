package org.gitletx;

import org.gitletx.etc.head.HEAD;
import org.gitletx.etc.head.IHEAD;
import org.gitletx.manager.LocalRepositoryManager;
import org.gitletx.objects.blob.Blob;
import org.gitletx.objects.blob.IBlob;
import org.gitletx.objects.commit.Commit;
import org.gitletx.objects.commit.ICommit;
import org.gitletx.objects.tree.Tree;
import org.gitletx.trees.repository.Repository;
import org.gitletx.trees.staging.IStagingArea;
import org.gitletx.trees.staging.StagingArea;
import org.gitletx.utilities.GitletxPaths;
import org.gitletx.utilities.IUtilitiesWrapper;
import org.gitletx.utilities.UtilitiesWrapper;

import java.io.File;
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
        if (LocalRepositoryManager.isGitletxExists()) {
            throw new RuntimeException("gitletx exists");
        }

        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IStagingArea stagingArea = new StagingArea(utilities);
        IHEAD head = new HEAD(utilities);

        LocalRepositoryManager manager = LocalRepositoryManager.create(utilities, stagingArea, head);

        LocalDateTime zeroDate = Instant.ofEpochSecond(0).atZone(ZoneId.of("UTC")).toLocalDateTime();

        // Root commit
        Blob blob = new Blob(
                utilities,
                new byte[]{},
                null,
                null
        );
        Tree tree = new Tree(utilities, blob);
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
        IStagingArea stagingArea = new StagingArea(utilities);
        IHEAD head = new HEAD(utilities);
        LocalRepositoryManager manager = LocalRepositoryManager.create(utilities, stagingArea, head);
        manager.showStatus();
    }

    public static void stage(String[] args) {
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IStagingArea stagingArea = new StagingArea(utilities);
        IHEAD head = new HEAD(utilities);

        LocalRepositoryManager manager = LocalRepositoryManager.create(utilities, stagingArea, head);

        List<IBlob> blobs = new LinkedList<>();

        for (int i = 1; i < args.length; i++) {
            Path validFile = manager.verifyFile(args[i]);
            blobs.add(
                    new Blob(
                            utilities,
                            utilities.readContents(validFile.toFile()),
                            args[i],
                            validFile.toString()
                    )
            );
        }

        stagingArea.stage(blobs);
    }

    public static void commit(String[] args) {
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IStagingArea stagingArea = new StagingArea(utilities);
        IHEAD head = new HEAD(utilities);

        // Get the commit message and load the staging area
        String commitMessage = args[1];
        HashMap<String, String> readyBlobs = stagingArea.getBlobsReadyToBeCommitted();
        IBlob[] blobs = new IBlob[readyBlobs.size()];

        int counter = 0;
        for (Map.Entry<String, String> entry : readyBlobs.entrySet()) {
            File filePath = Paths.get(GitletxPaths.WORKING_DIRECTORY.toString(), entry.getKey()).toFile();
            blobs[counter] = new Blob(
                    utilities,
                    utilities.readContents(filePath),
                    filePath.getName(),
                    entry.getKey()
            );
            counter++;
        }
        Tree tree = new Tree(utilities, blobs);

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

        Repository repository = Repository.create(utilities);
        ICommit committedObject = repository.commitObjects(commit, tree, blobs);

        head.updateHEAD(committedObject.getHash());
    }

    public static void unstage(String[] args) {
        // TODO: this operation supports only one file per-time, so maybe supporting multiple file if possible
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IStagingArea stagingArea = new StagingArea(utilities);
        HashMap<String, String> currentStagingArea = stagingArea.loadStagingArea();

        Path fullPath = Paths.get(GitletxPaths.WORKING_DIRECTORY.toString(), args[1]);
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
        IHEAD head = new HEAD(utilities);
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
        IHEAD head = new HEAD(utilities);
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
        String [] blobsInTree = tree.getContent();
        for (int i = 0; i < blobsInTree.length; i++) {
            Blob blob = Blob.getBlob(blobsInTree[i], utilities);
            if (blob.getFileName().equals(fileName)) {
                return blob;
            }
        }

        return null;
    }
    public static void checkout(String[] args) {
        String fileName = args[2];
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IHEAD head = new HEAD(utilities);
        Commit currentCommit = Commit.getCommit(head.getHEAD(), utilities);

        if (currentCommit == null) {
            return;
        }

        Tree tree = Tree.getTree(currentCommit.getTree(), utilities);
        Blob targetBlob = findBlob(fileName, tree, utilities);

        if (targetBlob != null) {
            // Remove the file from working directory if exist
            Path filePath = Paths.get(GitletxPaths.WORKING_DIRECTORY.toString(), fileName);

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
//                File workingDirectory = GitletxPaths.WORKING_DIRECTORY.toFile();
//                List<String> fileNames = utilities.plainFilenamesIn(workingDirectory);
//
//                for (String file : fileNames) {
//                    System.out.println(file);
//                }
//            }
//        }
    }
}
