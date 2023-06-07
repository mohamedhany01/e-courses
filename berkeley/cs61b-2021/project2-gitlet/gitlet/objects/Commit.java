package gitlet.objects;

import gitlet.Utils;
import gitlet.interfaces.ICommit;

import java.io.Serializable;

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

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
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

    @Override
    public void setTree(String tree) {
        this.tree = tree;
    }

    @Override
    public String getParent() {
        return parent;
    }

    @Override
    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public void setHash(String hash) {
        this.hash = hash;
    }
}
