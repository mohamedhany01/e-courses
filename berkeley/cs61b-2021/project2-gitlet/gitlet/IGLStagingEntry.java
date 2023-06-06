package gitlet;

public interface IGLStagingEntry {
    String getHash();

    Status getStatus();

    void setStatus(Status status);
}
