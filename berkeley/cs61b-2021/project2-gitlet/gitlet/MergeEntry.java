package gitlet;

import java.io.Serializable;

public class MergeEntry implements Serializable {
    private String firstParentHash;
    private String secondParentHash;
    private String firstParentName;
    private String secondParentName;

    public MergeEntry(String firstParentHash, String secondParentHash, String firstParentName, String secondParentName) {
        this.firstParentHash = firstParentHash;
        this.secondParentHash = secondParentHash;
        this.firstParentName = firstParentName;
        this.secondParentName = secondParentName;
    }

    public String getFirstParentHash() {
        return firstParentHash;
    }

    public String getSecondParentHash() {
        return secondParentHash;
    }

    public String getFirstParentName() {
        return firstParentName;
    }

    public String getSecondParentName() {
        return secondParentName;
    }
}
