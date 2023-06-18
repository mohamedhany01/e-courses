package gitlet.trees.staging;

import gitlet.MergeEntry;
import gitlet.MergeTracker;
import gitlet.Utils;
import gitlet.interfaces.IBlob;
import gitlet.interfaces.IGLStagingArea;
import gitlet.interfaces.IGLStagingEntry;
import gitlet.objects.Blob;
import gitlet.objects.Commit;
import gitlet.objects.Tree;
import gitlet.trees.Repository;
import gitlet.trees.WorkingArea;
import gitlet.trees.extra.Branch;
import gitlet.trees.extra.HEAD;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class StagingArea implements IGLStagingArea, Serializable {
    private final TreeMap<String, IGLStagingEntry> additions;
    private final Set<String> removals;

    public StagingArea() {
        StagingArea loadedStagingArea = StagingArea.load();

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
                new StagingArea()
        );
    }

    public static StagingArea load() {
        Path index = Path.of(Repository.INDEX);
        boolean isEmpty = index.toFile().length() == 0;

        if (Files.exists(index) && !isEmpty) {
            return Utils.readObject(new File(Repository.INDEX), StagingArea.class);
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
    public void commitStagedFiles(String message) {
        MergeEntry mergeEntry = MergeTracker.loadEntry();

        // Prepare staged files to be committed
        Tree tree = new Tree();
        System.out.println(getAllFiles());
        for (Map.Entry<String, IGLStagingEntry> entry : getAllFiles()) {

            String file = entry.getKey();

            // Skip deleted files
            if (!WorkingArea.exists(file)) {
                continue;
            }

            Blob blob = new Blob();
            blob.setFileName(file);

            tree.addBlob(blob.getHash());

            Repository.storeObject(blob.getHash(), blob);

            updatedEntryStatus(blob.getFileName(), Status.COMMITTED);
        }

        Commit commit = new Commit();
        if (MergeTracker.hasMergeData()) {
            commit.setMergeMessage(mergeEntry.getFirstParentHash() + " " + mergeEntry.getSecondParentHash());
            commit.setMessage("Merged " + mergeEntry.getSecondParentName() + " into " + mergeEntry.getFirstParentName() + ".");
        } else {
            commit.setMessage(message);
        }
        commit.setDate(Utils.getFormattedDate(LocalDateTime.now()));
        commit.setTree(tree.getHash());
        commit.setParent(HEAD.getHash());
        commit.setHash(Utils.sha1(Commit.calculateHash(commit)));

        // Store the objects in the .gitlet/objects
        Repository.commit(tree, commit);

        // Update active branch to point to the new commit
        Branch.updateActive(commit.getHash());

        // Clear files staged to be removed,
        // they should be staged when the staging area not in clean status "staged by rm",
        // when removals area isn't empty, the commit command will create a new commit by default
        clearRemovals();

        saveChanges();

        MergeTracker.removeEntry();
    }

    @Override
    public void updatedEntryStatus(String file, Status status) {
        IGLStagingEntry entry = additions.get(file);
        entry.setStatus(status);
        additions.put(file, entry);
    }

    @Override
    public void updatedEntryStatus(String file, StagingEntry entry) {
        additions.put(file, entry);
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
    public void stageForAddition(IBlob blob) {
        StagingEntry entry = new StagingEntry(blob.getHash());
        entry.setStatus(Status.STAGED);
        additions.put(blob.getFileName(), entry);
    }

    @Override
    public void stageForRemoval(String fileName) {
        removals.add(fileName);
    }

    @Override
    public boolean isTracked(String key) {
        return additions.containsKey(key);
    }

    @Override
    public boolean existsInLastCommit(String file) {
        IGLStagingEntry entry = additions.get(file);

        if (isTracked(file) && entry.getStatus().equals(Status.COMMITTED)) {
            return true;
        }
        return false;
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
    public Set<Map.Entry<String, IGLStagingEntry>> getUntrackedFiles() {
        TreeMap<String, IGLStagingEntry> untrackedFiles = new TreeMap<>();
        for (String file : WorkingArea.getWorkingFiles()) {

            // If no file in the additions map, then this file is untracked
            if (!isTracked(file)) {
                untrackedFiles.put(file, untrackedFiles.get(file));
            }
        }
        return untrackedFiles.entrySet();
    }

    @Override
    public Set<Map.Entry<String, IGLStagingEntry>> getAllFiles() {
        return additions.entrySet();
    }

    /*
     * Staged status
     *
     * We call a file staged when:
     * - The staging area "additions" has this file [*]
     * AND
     * - Labeled as "STAGED"
     *
     * IGNORE
     * - This file isn't a blob in the repository yet "Have not been committed before" [X]
     *
     * */
    @Override
    public Set<Map.Entry<String, IGLStagingEntry>> getStagedFiles() {
        TreeMap<String, IGLStagingEntry> stagedFiles = new TreeMap<>();

        // Files in the staging area "additions"
        for (Map.Entry<String, IGLStagingEntry> entry : additions.entrySet()) {
            String fileName = entry.getKey();
            Status status = entry.getValue().getStatus();

            // Don't stage deleted files and invalid files
            if (!Files.exists(WorkingArea.getPath(fileName))) continue;

            if (status.equals(Status.STAGED)) {
                stagedFiles.put(fileName, stagedFiles.get(fileName));
            }
        }
        return stagedFiles.entrySet();
    }

    /*
     * Removed status
     *
     * A file is removed when:
     * - The file in the staging area "removals"
     *
     * */
    @Override
    public Set<Map.Entry<String, IGLStagingEntry>> getRemovedFiles() {
        TreeMap<String, IGLStagingEntry> removedFiles = new TreeMap<>();

        // Files in the staging area "removals"
        for (String file : removals) {
            removedFiles.put(file, removedFiles.get(file));
        }
        return removedFiles.entrySet();
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
        TreeMap<String, Blob> lastCommitFiles = Repository.getLastCommitBlobs();

        // Files in the staging area "additions"
        for (Map.Entry<String, IGLStagingEntry> entry : additions.entrySet()) {
            String file = entry.getKey();
            String workingDirectoryFile = Path.of(WorkingArea.WD, file).toString();

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
            // If any file in last commit changed OR a staged file change
            if (
                    (lastCommitFiles.containsKey(file) && !lastCommitFiles.get(file).getHash().equals(workingDirectoryFileHash)) ||
                            !getHashInAdditions(file).equals(workingDirectoryFileHash)
            ) {
                modifiedFiles.put(file, "(modified)");
            }
        }
        return modifiedFiles.entrySet();
    }

    @Override
    public String getHashInAdditions(String key) {
        return additions.get(key).getHash();
    }
}
