package gitlet.trees.staging;

import gitlet.interfaces.IGLStagingEntry;

import java.io.Serializable;

public class StagingEntry implements IGLStagingEntry, Serializable {
    private final String hash;
    private Status status;


    public StagingEntry(String hash) {
        this.hash = hash;
        this.status = Status.NOT_STAGED;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
}
