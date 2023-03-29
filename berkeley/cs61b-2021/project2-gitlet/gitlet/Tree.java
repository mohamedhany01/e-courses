package gitlet;

import gitlet.interfaces.ITree;
import gitlet.interfaces.IUtilitiesWrapper;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Tree implements ITree, Serializable {
    private final static String type = "tree";
    private final List<Object> content;
    private String hash;

    public Tree() {
        this.content = new LinkedList<>();
    }

    public static Tree getTree(String hash, IUtilitiesWrapper utilities) {
        Path treeFullPath = Paths.get(GitletPaths.OBJECTS.toString(), hash);
        if (!Files.exists(treeFullPath)) {
            throw new RuntimeException("Tree with " + hash + " not found");
        }
        Tree tree = utilities.readObject(treeFullPath.toFile(), Tree.class);
        return tree;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public List<Object> getContent() {
        return this.content;
    }

    public void setBlob(String hash) {
        this.content.add(hash);
    }

    public void calculateContentHash(IUtilitiesWrapper utilities) {
        this.hash = utilities.sha1(this.content);
    }

    @Override
    public String getHash() {
        return hash;
    }
}
