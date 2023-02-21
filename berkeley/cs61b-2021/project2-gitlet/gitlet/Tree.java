package gitlet;

import java.io.Serializable;

/**
 * Represents a gitlet tree object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author Mohamed Hany
 */

public class Tree implements Serializable {
    private final String hash;
    private final String type = "tree";
    private final String content;

    public Tree(String blobHash) {
        this.hash = Utils.sha1(blobHash);
        this.content = blobHash;
    }

    public String getHash() {
        return hash;
    }

    public String getContent() {
        return content;
    }
}
