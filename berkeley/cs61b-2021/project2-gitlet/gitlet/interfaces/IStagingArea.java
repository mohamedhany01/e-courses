package gitlet.interfaces;

import java.util.HashMap;
import java.util.List;

public interface IStagingArea {
    void writeStagingArea();

    HashMap<String, String> loadStagingArea();

    HashMap<String, String> updateStagingArea(HashMap<String, String> newStagingArea);

    void displayStagedFiles();

    void displayUntrackedFiles();

    void displayModifiedFiles();

    void displayRemovedFiles();

    void stage(IBlob blob);

    void stagManually(String key, String hash);

    void deleteEntry(String key);

    boolean hasFileName(String key);

    HashMap<String, String> getBlobsReadyToBeCommitted();
}
