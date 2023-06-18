package gitlet.objects;

import gitlet.Utils;
import gitlet.interfaces.ICommit;

import java.io.Serializable;

public class Commit implements ICommit, Serializable {
    /**
     * Who created this commit.
     */
    private final String authorName;
    /**
     * The email author of this commit.
     */
    private final String authorEmail;
    /**
     * The message of this Commit.
     */
    private String message;
    /**
     * The message of this Commit.
     */
    private String mergeMessage;
    /**
     * When this commit is created.
     */
    private String date;
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
        this.mergeMessage = null;
    }

    public static String calculateHash(ICommit commit) {
        String builder = commit.getMessage() +
                commit.getMergeMessage() +
                commit.getDate() +
                commit.getAuthorName() +
                commit.getAuthorName() +
                commit.getTree() +
                commit.getParent();
        return Utils.sha1(builder);
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

    @Override
    public String getMergeMessage() {
        return this.mergeMessage;
    }

    @Override
    public void setMergeMessage(String mergeMessage) {
        this.mergeMessage = mergeMessage;
    }
}
