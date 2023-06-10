package gitlet.interfaces;

import gitlet.trees.staging.StagingEntry;
import gitlet.trees.staging.Status;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public interface IGLStagingArea {
    void saveChanges();

    void clearAdditions();

    void clearRemovals();

    void deleteAdditionsEntry(String key);

    void deleteRemovalsEntry(String key);

    void commitStagedFiles(String message);

    void updatedEntryStatus(String file, Status status);

    void updatedEntryStatus(String file, StagingEntry entry);

    TreeMap<String, IGLStagingEntry> getAdditions();

    Set<String> getRemovals();

    void stageForAddition(IBlob blob);

    void stageForRemoval(String fileName);

    Set<Map.Entry<String, IGLStagingEntry>> getUntrackedFiles();

    Set<Map.Entry<String, IGLStagingEntry>> getAllFiles();

    Set<Map.Entry<String, IGLStagingEntry>> getStagedFiles();

    Set<Map.Entry<String, IGLStagingEntry>> getRemovedFiles();

    Set<Map.Entry<String, String>> getModifiedFiles();

    String getHashInAdditions(String key);

    boolean isTracked(String key);

    boolean existsInLastCommit(String file);

    boolean hasFileInRemovals(String key);

    boolean isClean();

    boolean haveUntrackedFiles();
}
