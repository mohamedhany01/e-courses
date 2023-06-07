package gitlet.objects;

import gitlet.Utils;
import gitlet.interfaces.ITree;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Tree implements ITree, Serializable {
    private final List<Object> content;
    private String hash;

    public Tree() {
        this.content = new LinkedList<>();
    }

    @Override
    public List<Object> getBlobs() {
        return this.content;
    }

    public void addBlob(String hash) {
        this.content.add(hash);
        this.hash = Utils.sha1(this.content);
    }

    @Override
    public String getHash() {
        return hash;
    }
}
