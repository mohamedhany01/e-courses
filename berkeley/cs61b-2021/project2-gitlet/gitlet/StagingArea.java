package gitlet;

import gitlet.interfaces.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class StagingArea implements IStagingArea {
    private final IUtilitiesWrapper utilities;
    private final IGitletPathsWrapper gitletPaths;

    public StagingArea(IUtilitiesWrapper utilities, IGitletPathsWrapper gitletPaths) {
        this.utilities = utilities;
        this.gitletPaths = gitletPaths;
    }

    @Override
    public void writeStagingArea() {
        if (!Files.exists(gitletPaths.getIndex())) {
            throw new RuntimeException("index file not found");
        }
        this.utilities.writeObject(gitletPaths.getIndex().toFile(), new HashMap<String, String>());
    }

    public void writeStagingArea(HashMap<String, String> stagingArea) {
        if (!Files.exists(gitletPaths.getIndex())) {
            throw new RuntimeException("index file not found");
        }
        this.utilities.writeObject(gitletPaths.getIndex().toFile(), stagingArea);
    }

    @Override
    public HashMap<String, String> loadStagingArea() {
        if (!Files.exists(gitletPaths.getIndex())) {
            throw new RuntimeException("index file not found");
        }
        return (HashMap<String, String>) this.utilities.readObject(gitletPaths.getIndex().toFile(), HashMap.class);
    }

    @Override
    public HashMap<String, String> updateStagingArea(HashMap<String, String> newStagingArea) {
        if (!Files.exists(gitletPaths.getIndex())) {
            throw new RuntimeException("index file not found");
        }
        writeStagingArea(newStagingArea);
        return loadStagingArea();
    }

    @Override
    public void getFilesStatus() {
        // TODO: Make this logic more simple
        IUtilitiesWrapper utilities = new UtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IHEAD head = new HEAD(utilities, gitletPaths);
        IWorkingArea workingArea = new WorkingArea(utilities, gitletPaths);
        Repository repository = Repository.create(utilities, gitletPaths);
        HashMap<String, String> HEADFiles = head.getCommitFiles();
        HashMap<String, String> stagingArea = loadStagingArea();

        System.out.println("\n=== Branches ===");
        for (String branch : repository.getAllBranches()) {
            if (branch.equals(head.getActiveBranchName())) {
                System.out.println("*" + branch);
            } else {
                System.out.println(branch);
            }
        }

        System.out.println("\n=== Staged Files ===");
        for (Map.Entry<String, String> entry : stagingArea.entrySet()) {
            String stagingAreaFile = entry.getKey();
            String HeadFileHash = HEADFiles.get(stagingAreaFile);
            String stagingAreaFileHash = stagingArea.get(stagingAreaFile);

            if (!HEADFiles.containsKey(stagingAreaFile) || !HeadFileHash.equals(stagingAreaFileHash)) {
                System.out.println(stagingAreaFile);
            }
        }

        System.out.println("\n=== Removed Files ===");
        // In case it staged and then deleted
        for (Map.Entry<String, String> entry : stagingArea.entrySet()) {
            String file = entry.getKey();

            if (!workingArea.isFileExist(file) && !stagingArea.get(file).isEmpty()) {
                System.out.println(file);
            }
        }

        System.out.println("\n=== Modifications Not Staged For Commit ===");
        for (String fileName : workingArea.getFiles()) {
            String fileHash = workingArea.getFileHash(fileName);
            if (stagingArea.containsKey(fileName) && !stagingArea.get(fileName).equals(fileHash)) {
                if (stagingArea.containsKey(fileName) && !stagingArea.get(fileName).equals(fileHash)) {
                    System.out.println(fileName + " (modified)");
                } else {
                    System.out.println(fileName + " (deleted)");
                }
            }
        }

        System.out.println("\n=== Untracked Files ===");
        for (String fileName : workingArea.getFiles()) {
            if (!containsPair(fileName, workingArea.getFileHash(fileName))) {
                System.out.println(fileName);
            }
        }

    }

    @Override
    public boolean containsPair(String fileName, String hash) {
        HashMap<String, String> stagingArea = loadStagingArea();
        return stagingArea.containsKey(fileName) && stagingArea.get(fileName).equals(hash);
    }

    @Override
    public void stage(IBlob blob) {
        HashMap<String, String> stagingArea = loadStagingArea();

        if (!stagingArea.containsValue(blob.getHash())) {
            // Modified and thus it will be updated
            if (stagingArea.containsKey(blob.getFilePath())) {
                stagingArea.put(blob.getFileName(), blob.getHash());
            } else {
                // It's totally a new entry
                stagingArea.put(blob.getFileName(), blob.getHash());
            }
        }

        updateStagingArea(stagingArea);
    }

    @Override
    public void stagManually(String key, String hash) {
        HashMap<String, String> stagingArea = loadStagingArea();

        stagingArea.put(key, hash);

        updateStagingArea(stagingArea);
    }

    @Override
    public void deleteEntry(String key) {
        HashMap<String, String> stagingArea = loadStagingArea();
        stagingArea.remove(key);
        updateStagingArea(stagingArea);
    }

    @Override
    public boolean hasFileName(String key) {
        HashMap<String, String> stagingArea = loadStagingArea();
        return stagingArea.containsKey(key);
    }

    @Override
    public boolean isStagingAreaInCleanState() {
        HashMap<String, String> stagingArea = loadStagingArea();
        for (Map.Entry<String, String> entry : stagingArea.entrySet()) {
            String committedCommitHash = entry.getValue();
            Path committedCommitFullPath = Path.of(gitletPaths.getObjects().toString(), committedCommitHash);

            // The OR "||" condition is in case a hash of a file is empty "" using stagManually(), process it as a dirty state
            if (stagingArea.size() != 0 && !Files.exists(committedCommitFullPath) || entry.getValue().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public HashMap<String, String> getBlobsReadyToBeCommitted() {
        HashMap<String, String> stagingArea = loadStagingArea();
        HashMap<String, String> readyBlobs = new HashMap<>();
        for (Map.Entry<String, String> entry : stagingArea.entrySet()) {
            Path fileInWorkingDirectory = Paths.get(gitletPaths.getWorkingDirectory().toString(), entry.getKey());
            boolean isFileAlreadyExist = Files.exists(fileInWorkingDirectory);
//            boolean isBlobAlreadyCommitted = Files.exists(Paths.get(gitletPaths.getObjects().toString(), entry.getValue()));
            if (isFileAlreadyExist) {
                readyBlobs.put(entry.getKey(), entry.getValue());
            }
        }
        return readyBlobs;
    }
}
