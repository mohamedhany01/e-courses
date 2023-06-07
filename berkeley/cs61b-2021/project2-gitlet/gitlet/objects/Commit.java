package gitlet.objects;

import gitlet.Utils;
import gitlet.interfaces.ICommit;
import gitlet.trees.Repository;

import java.io.Serializable;
import java.util.List;

public class Commit implements ICommit, Serializable {
    /**
     * The message of this Commit.
     */
    private String message;
    /**
     * When this commit is created.
     */
    private String date;
    /**
     * Who created this commit.
     */
    private String authorName;
    /**
     * The email author of this commit.
     */
    private String authorEmail;
    /**
     * The tree hash of this commit.
     */
    private String tree;
    /**
     * The parent hash of this commit.
     */
    private String parent;
    /**
     * The hash of this commit.
     */
    private String hash;

    public Commit() {
        this.authorName = "";
        this.authorEmail = "";
    }

    /*
     * The root commit hash is always predictable, so we can use it to build the linked list history
     * */
    public static Commit getRootCommit() {
        return Repository.getObject("f3d664d3d0be025b6747ee19e4eef90bf22528e8", Commit.class);
    }

    public static Blob hasFile(String file, String hash) {
        Commit commit = Repository.getObject(hash, Commit.class);
        Tree commitTree = Repository.getObject(commit.getTree(), Tree.class);
        for (Object blobHash : commitTree.getBlobs()) {
            Blob blob = Repository.getObject((String) blobHash, Blob.class);
            if (blob.getFileName().equals(file)) {
                return blob;
            }
        }
        return null;
    }

    public static List<Object> getBlobs(String hash) {
        Commit commit = Repository.getObject(hash, Commit.class);
        Tree commitTree = Repository.getObject(commit.getTree(), Tree.class);
        return commitTree.getBlobs();
    }

    public static String calculateHash(ICommit commit) {
        StringBuilder builder = new StringBuilder();
        builder.append(commit.getMessage());
        builder.append(commit.getDate());
        builder.append(commit.getAuthorName());
        builder.append(commit.getAuthorName());
        builder.append(commit.getTree());
        builder.append(commit.getParent());
        return Utils.sha1(builder.toString());
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getAuthorName() {
        return authorName;
    }

    @Override
    public String getAuthorEmail() {
        return authorEmail;
    }

    @Override
    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    @Override
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
