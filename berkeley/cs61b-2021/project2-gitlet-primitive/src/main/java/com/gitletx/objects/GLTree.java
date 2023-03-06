package com.gitletx.objects;

import com.gitletx.objects.blob.IGLBlob;
import com.gitletx.utilities.hashing.IHashing;
import java.io.Serializable;

/**
 * Represents a gitlet tree object
 *
 * @author Mohamed Hany
 */

public class GLTree implements Serializable {
    private String hash;
    private final String type = "tree";
    private String[] content;

    public GLTree() {}

    public int setContent(Object ...blobs) {
        int counter = 0;

        this.content = new String[blobs.length];

        for (Object blob : blobs) {
            IGLBlob b = (IGLBlob) blob;
            this.content[counter++] = b.getHash();
        }

        return blobs.length;
    }

    // Dependency injection "concrete implementation"
    public String setHash(IHashing hashing) {
        if (this.content == null) {
            throw new RuntimeException("The content isn't initialized yet");
        }

        this.hash = hashing.sha1(this.getContent());

        return getHash();
    }
    public String getHash() {
        return hash;
    }
    public String getType() {
        return type;
    }
    public String[] getContent() {
        return content;
    }
}
