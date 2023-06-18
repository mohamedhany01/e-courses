package gitlet;

import java.io.Serializable;

public class MergeEntry implements Serializable {
    private boolean hasConflict;
    private String firstParent;
    private String secondParent;

    public MergeEntry(boolean hasConflict, String firstParent, String secondParent) {
        this.hasConflict = hasConflict;
        this.firstParent = firstParent;
        this.secondParent = secondParent;
    }

    public boolean isHasConflict() {
        return hasConflict;
    }

    public String getFirstParent() {
        return firstParent;
    }

    public String getSecondParent() {
        return secondParent;
    }
}
