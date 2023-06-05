package gitlet;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public interface IGLStagingArea {
    public void saveChanges();

    public void clearAdditions();

    public void clearRemovals();

    public void deleteAdditionsEntry(String key);

    public void deleteRemovalsEntry(String key);

    public TreeMap<String, String> getAdditions();

    public Set<String> getRemovals();

    public void stageForAddition(String fileName, String fileHash);

    public void stageForRemoval(String fileName);

    public LinkedList<String> getUntrackedFiles();

    public LinkedList<String> getStagedFiles();

    public LinkedList<String> getRemovedFiles();

    public Set<Map.Entry<String, String>> getModifiedFiles();

    public boolean hasFileInAdditions(String key);

    public boolean isClean();
}
