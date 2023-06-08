package gitlet.interfaces;

import gitlet.trees.staging.Status;

import java.util.LinkedList;
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

    TreeMap<String, IGLStagingEntry> getAdditions();

    Set<String> getRemovals();

    void stageForAddition(IBlob blob);

    void stageForRemoval(String fileName);

    LinkedList<String> getUntrackedFiles();

    LinkedList<String> getStagedFiles();

    LinkedList<String> getRemovedFiles();

    Set<Map.Entry<String, String>> getModifiedFiles();

    boolean isTracked(String key);

    boolean existsInLastCommit(String file);

    boolean hasFileInRemovals(String key);

    boolean isClean();

    boolean haveUntrackedFiles();
}
