package gitlet;

import gitlet.interfaces.IBlob;
import gitlet.interfaces.ITree;
import gitlet.interfaces.IUtilitiesWrapper;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tree implements ITree, Serializable {
    private final static String type = "tree";
    private final String[] content;
    private final String hash;

    public Tree(IUtilitiesWrapper utilities, IBlob... blobs) {
        this.content = new String[blobs.length];
        for (int i = 0; i < blobs.length; i++) {
            this.content[i] = blobs[i].getHash();
        }
        this.hash = utilities.sha1(this.content);
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
    public String[] getContent() {
        return content;
    }

    @Override
    public String getHash() {
        return hash;
    }
}
