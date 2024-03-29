package org.gitletx.trees.staging;

import org.gitletx.objects.blob.IBlob;
import org.gitletx.utilities.GitletxPaths;
import org.gitletx.utilities.IUtilitiesWrapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StagingArea implements IStagingArea {
    private final IUtilitiesWrapper utilities;

    public StagingArea(IUtilitiesWrapper utilities) {
        this.utilities = utilities;
    }

    @Override
    public void writeStagingArea() {
        if (!Files.exists(GitletxPaths.INDEX)) {
            throw new RuntimeException("index file not found");
        }
        this.utilities.writeObject(GitletxPaths.INDEX.toFile(), new HashMap<String, String>());
    }

    public void writeStagingArea(HashMap<String, String> stagingArea) {
        if (!Files.exists(GitletxPaths.INDEX)) {
            throw new RuntimeException("index file not found");
        }
        this.utilities.writeObject(GitletxPaths.INDEX.toFile(), stagingArea);
    }

    @Override
    public HashMap<String, String> loadStagingArea() {
        if (!Files.exists(GitletxPaths.INDEX)) {
            throw new RuntimeException("index file not found");
        }
        return (HashMap<String, String>) this.utilities.readObject(GitletxPaths.INDEX.toFile(), HashMap.class);
    }

    @Override
    public HashMap<String, String> updateStagingArea(HashMap<String, String> newStagingArea) {
        if (!Files.exists(GitletxPaths.INDEX)) {
            throw new RuntimeException("index file not found");
        }
        writeStagingArea(newStagingArea);
        return loadStagingArea();
    }

    @Override
    public void displayStagedFiles() {
        File workingDirectory = GitletxPaths.WORKING_DIRECTORY.toFile();
        List<String> fileNames = utilities.plainFilenamesIn(workingDirectory);
        HashMap<String, String> stagingArea = loadStagingArea();

        System.out.println("\n=== Staged Files ===");
        for (String fileName : fileNames) {
            Path fullPath = Paths.get(workingDirectory.getPath(), fileName);
            String fileHash = utilities.sha1(utilities.readContents(fullPath.toFile()));
//            String fileRelativePath = Paths.get(fullPath.relativize(workingDirectory.toPath()).toString(), fileName).toString();

            /*
             * If staging area "index" has info about this blob and local repo "objects/" doesn't
             * */
            if (stagingArea.containsKey(fileName) && stagingArea.containsValue(fileHash) && !Files.exists(Paths.get(GitletxPaths.OBJECTS.toString(), fileHash))) {
                System.out.println(fullPath.getFileName());
            }
        }
    }

    @Override
    public void displayUntrackedFiles() {
        File workingDirectory = GitletxPaths.WORKING_DIRECTORY.toFile();
        List<String> fileNames = utilities.plainFilenamesIn(workingDirectory);
        HashMap<String, String> stagingArea = loadStagingArea();

        System.out.println("\n=== Untracked Files ===");
        for (String fileName : fileNames) {
            Path fullPath = Paths.get(workingDirectory.getPath(), fileName);
            String fileHash = utilities.sha1(utilities.readContents(fullPath.toFile()));
//            String fileRelativePath = Paths.get(fullPath.relativize(workingDirectory.toPath()).toString(), fileName).toString();
            /*
             * If staging area "index" doesn't have any info "empty" or doesn't have file's path and its hash
             * */
            if ((stagingArea.isEmpty() || !stagingArea.containsKey(fileName)) && !stagingArea.containsValue(fileHash)) {
                System.out.println(fullPath.getFileName());
            }
        }
    }

    @Override
    public void displayModifiedFiles() {
        File workingDirectory = GitletxPaths.WORKING_DIRECTORY.toFile();
        List<String> fileNames = utilities.plainFilenamesIn(workingDirectory);
        HashMap<String, String> stagingArea = loadStagingArea();

        System.out.println("\n=== Modifications Not Staged For Commit ===");
        for (String fileName : fileNames) {
            Path fullPath = Paths.get(workingDirectory.getPath(), fileName);
            String fileHash = utilities.sha1(utilities.readContents(fullPath.toFile()));
//            String fileRelativePath = Paths.get(fullPath.relativize(workingDirectory.toPath()).toString(), fileName).toString();

            /*
             * If staging area "index" has file path but file's hash not the same
             * */
            if (stagingArea.containsKey(fileName) && !stagingArea.containsValue(fileHash)) {
                System.out.println(fullPath.getFileName());
            }
        }
    }

    @Override
    public void stage(List<IBlob> blobs) {
        HashMap<String, String> stagingArea = loadStagingArea();

        for (IBlob blob : blobs) {
            if (!stagingArea.containsValue(blob.getHash())) {
                // Modified and thus it will be updated
                if (stagingArea.containsKey(blob.getFilePath())) {
                    stagingArea.put(blob.getFileName(), blob.getHash());
                } else {
                    // It's totally a new entry
                    stagingArea.put(blob.getFileName(), blob.getHash());
                }
            }
        }

        updateStagingArea(stagingArea);
    }

    @Override
    public HashMap<String, String> getBlobsReadyToBeCommitted() {
        HashMap<String, String> stagingArea = loadStagingArea();
        HashMap<String, String> readyBlobs = new HashMap<>();
        for (Map.Entry<String, String> entry : stagingArea.entrySet()) {
            boolean isBlobAlreadyCommitted = Files.exists(Paths.get(GitletxPaths.OBJECTS.toString(), entry.getValue()));
            if (!isBlobAlreadyCommitted) {
                readyBlobs.put(entry.getKey(), entry.getValue());
            }
        }
        return readyBlobs;
    }
}
