package gitlet.interfaces;

import java.util.HashMap;

public interface IStagingArea {
    void writeStagingArea();

    HashMap<String, String> loadStagingArea();

    HashMap<String, String> updateStagingArea(HashMap<String, String> newStagingArea);

    void getFilesStatus();

    boolean containsPair(String fileName, String hash);

    void stage(IBlob blob);

    void stagManually(String key, String hash);

    void deleteEntry(String key);

    boolean hasFileName(String key);

    boolean isStagingAreaInCleanState();

    HashMap<String, String> getBlobsReadyToBeCommitted();
}
