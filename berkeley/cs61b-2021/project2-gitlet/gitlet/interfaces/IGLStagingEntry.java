package gitlet.interfaces;

import gitlet.trees.staging.Status;

public interface IGLStagingEntry {
    String getHash();

    Status getStatus();

    void setStatus(Status status);
}
