package gitlet.interfaces;

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

    TreeMap<String, IGLStagingEntry> getAdditions();

    Set<String> getRemovals();

    void stageForAddition(String fileName, IGLStagingEntry file);

    void stageForRemoval(String fileName);

    LinkedList<String> getUntrackedFiles();

    LinkedList<String> getStagedFiles();

    LinkedList<String> getRemovedFiles();

    Set<Map.Entry<String, String>> getModifiedFiles();

    boolean hasFileInAdditions(String key);

    boolean hasFileInRemovals(String key);

    boolean isClean();

    boolean haveUntrackedFiles();
}
