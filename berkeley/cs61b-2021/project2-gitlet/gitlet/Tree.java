package gitlet;

import java.io.Serializable;
import java.util.Arrays;

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
    private final String[] content;

    public Tree(Blob ...blobs) {
        this.content = new String[blobs.length];
        setContent(blobs);
        this.hash = Utils.sha1(this.getContent());
    }

    public String getHash() {
        return hash;
    }

    public void setContent(Object ...blobs) {
        int counter = 0;

        for (Object blob : blobs) {
            Blob b = (Blob) blob;
            this.content[counter++] = b.getHash();
        }
    }

    public String[] getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "hash='" + hash + '\'' +
                ", type='" + type + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
