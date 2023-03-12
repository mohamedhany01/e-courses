package gitlet;

import java.util.HashMap;
import java.util.List;

public interface IStagingArea {
    void writeStagingArea();

    HashMap<String, String> loadStagingArea();

    HashMap<String, String> updateStagingArea(HashMap<String, String> newStagingArea);

    void displayStagedFiles();

    void displayUntrackedFiles();

    void displayModifiedFiles();

    void stage(List<IBlob> blobs);

    HashMap<String, String> getBlobsReadyToBeCommitted();
}
