package gitlet;

import gitlet.interfaces.ITree;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class Tree implements ITree, Serializable {
    private final List<Object> content;
    private String hash;

    public Tree() {
        this.content = new LinkedList<>();
    }

    public static Tree getTree(String hash) {
        String tree = Path.of(Repository.OBJECTS, hash).toString();

        if (!Repository.isInRepository(hash, new GitletPathsWrapper())) {
            return null;
        }

        return Utils.readObject(new File(tree), Tree.class);
    }

    @Override
    public List<Object> getContent() {
        return this.content;
    }

    public void setBlob(String hash) {
        this.content.add(hash);
    }

    public void calculateContentHash() {
        this.hash = Utils.sha1(this.content);
    }

    @Override
    public String getHash() {
        return hash;
    }
}
