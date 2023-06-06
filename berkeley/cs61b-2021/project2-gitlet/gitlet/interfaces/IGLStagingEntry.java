package gitlet.interfaces;

import gitlet.Status;

public interface IGLStagingEntry {
    String getHash();

    Status getStatus();

    void setStatus(Status status);
}
