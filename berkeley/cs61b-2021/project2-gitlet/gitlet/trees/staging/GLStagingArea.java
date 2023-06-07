package gitlet.trees.staging;

import gitlet.Utils;
import gitlet.interfaces.IGLStagingArea;
import gitlet.interfaces.IGLStagingEntry;
import gitlet.trees.Repository;
import gitlet.trees.WorkingArea;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class GLStagingArea implements IGLStagingArea, Serializable {
    private final TreeMap<String, IGLStagingEntry> additions;
    private final Set<String> removals;

    public GLStagingArea() {
        GLStagingArea loadedStagingArea = GLStagingArea.load();

        if (loadedStagingArea != null) {
            additions = loadedStagingArea.additions;
            removals = loadedStagingArea.removals;
        } else {
            additions = new TreeMap<>();
            removals = new TreeSet<>();
        }
    }

    public static void initialize() {
        Utils.writeObject(
                new File(Repository.INDEX),
                new GLStagingArea()
        );
    }

    public static GLStagingArea load() {
        Path index = Path.of(Repository.INDEX);
        boolean isEmpty = index.toFile().length() == 0;

        if (Files.exists(index) && !isEmpty) {
            return Utils.readObject(new File(Repository.INDEX), GLStagingArea.class);
        }
        return null;
    }

    @Override
    public void saveChanges() {
        Utils.writeObject(new File(Repository.INDEX), this);
    }

    @Override
    public void clearAdditions() {
        additions.clear();
    }

    @Override
    public void clearRemovals() {
        removals.clear();
    }

    @Override
    public void deleteAdditionsEntry(String key) {
        additions.remove(key);
    }

    @Override
    public void deleteRemovalsEntry(String key) {
        removals.remove(key);
    }

    @Override
    public TreeMap<String, IGLStagingEntry> getAdditions() {
        return additions;
    }

    @Override
    public Set<String> getRemovals() {
        return removals;
    }

    @Override
    public void stageForAddition(String fileName, IGLStagingEntry file) {
        additions.put(fileName, file);
    }

    @Override
    public void stageForRemoval(String fileName) {
        removals.add(fileName);
    }

    @Override
    public boolean hasFileInAdditions(String key) {
        return additions.containsKey(key);
    }

    @Override
    public boolean hasFileInRemovals(String key) {
        return removals.contains(key);
    }

    @Override
    public boolean isClean() {
        return getStagedFiles().size() == 0 && getRemovedFiles().size() == 0;
    }

    @Override
    public boolean haveUntrackedFiles() {
        return getUntrackedFiles().size() != 0;
    }

    /*
     * Untracked status
     *
     * We call a file untracked when:
     * - The staging area "additions" hasn't this file
     *
     * */
    @Override
    public LinkedList<String> getUntrackedFiles() {
        LinkedList<String> untrackedFiles = new LinkedList<>();
        for (String file : WorkingArea.getWorkingFiles()) {

            // If no file in the additions map, then this file is untracked
            if (!hasFileInAdditions(file)) {
                untrackedFiles.add(file);
            }
        }
        return untrackedFiles;
    }

    /*
     * Staged status
     *
     * We call a file staged when:
     * - The staging area "additions" has this file
     * AND
     * - This file isn't a blob in the repository yet "Have not been committed before"
     *
     * */
    @Override
    public LinkedList<String> getStagedFiles() {
        LinkedList<String> stagedFiles = new LinkedList<>();

        // Files in the staging area "additions"
        for (Map.Entry<String, IGLStagingEntry> entry : additions.entrySet()) {
            String fileName = entry.getKey();
            Status status = entry.getValue().getStatus();

            Path filePath = Path.of(WorkingArea.WD, fileName);

            if (!Files.exists(filePath)) continue;
//            String fileHash = Utils.sha1(
//                    Utils.readContents(
//                            new File(filePath)
//                    )
//            );

//            if (hasFileInAdditions(fileName) && !Repository.isInRepository(fileHash, new GitletPathsWrapper()))
            if (status.equals(Status.STAGED)) {
                stagedFiles.add(fileName);
            }
        }
        return stagedFiles;
    }

    /*
     * Removed status
     *
     * A file is removed when:
     * - The file in the staging area "removals"
     *
     * */
    @Override
    public LinkedList<String> getRemovedFiles() {
        LinkedList<String> removedFiles = new LinkedList<>();

        // Files in the staging area "removals"
        for (String file : removals) {
            removedFiles.add(file);
        }
        return removedFiles;
    }

    /*
     * Modified status
     *
     * -> Deleted file
     *
     * A file is deleted when:
     *
     * - The file is in the staging area "additions"
     * AND
     * - The file is a blob in the last commit
     * AND
     * - The file it isn't represented in the Working directory
     *
     * OR
     * - The file is in the staging area "additions"
     * AND
     * - The file it isn't represented in the Working directory
     *
     * -> Modified file
     *
     * A file is modified when:
     *
     * - The file is in the staging area "additions"
     * AND
     * - The file is a blob in the last commit, but its content "hash" changed in the working directory
     *
     * */
    @Override
    public Set<Map.Entry<String, String>> getModifiedFiles() {
        TreeMap<String, String> modifiedFiles = new TreeMap<>();
        TreeMap<String, String> lastCommitFiles = Repository.getLastCommitFiles();

        // Files in the staging area "additions"
        for (Map.Entry<String, IGLStagingEntry> entry : additions.entrySet()) {
            String file = entry.getKey();
            String workingDirectoryFile = Path.of(WorkingArea.WD, file).toString();
            Status fileStatus = entry.getValue().getStatus();

            // Isn't represented in the working directory, then it's deleted
            if (lastCommitFiles.containsKey(file) && !WorkingArea.exists(file) || !WorkingArea.exists(file)) {
                modifiedFiles.put(file, "(deleted)");
                continue;
            }

            String workingDirectoryFileHash = Utils.sha1(
                    Utils.readContents(
                            new File(workingDirectoryFile)
                    )
            );

            // If not the same hash then it's modified
            if (lastCommitFiles.containsKey(file) && !lastCommitFiles.get(file).equals(workingDirectoryFileHash) && !fileStatus.equals(Status.STAGED)) {
                modifiedFiles.put(file, "(modified)");
            }
        }
        return modifiedFiles.entrySet();
    }
}
