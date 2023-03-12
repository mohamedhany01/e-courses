package org.gitletx.objects.commit;

import org.gitletx.utilities.GitletxPaths;
import org.gitletx.utilities.IUtilitiesWrapper;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Commit implements ICommit, Serializable {
    /**
     * The type of this commit.
     */
    private final static String type = "commit";
    /**
     * The message of this Commit.
     */
    private final String message;
    /**
     * When this commit is created.
     */
    private final LocalDateTime date;
    /**
     * Who created this commit.
     */
    private final String authorName;
    /**
     * The email author of this commit.
     */
    private final String authorEmail;
    /**
     * The tree hash of this commit.
     */
    private final String tree;
    /**
     * The parent hash of this commit.
     */
    private final String parent;
    /**
     * The hash of this commit.
     */
    private final String hash;

    public Commit(String message, LocalDateTime date, String authorName, String authorEmail, String tree, String parent, String hash) {
        this.message = message;
        this.date = date;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.tree = tree;
        this.parent = parent;
        this.hash = hash;
    }

    /*
     * The root commit hash is always predictable, so we can use it to build the linked list history
     * */
    public static Commit getRootCommit(IUtilitiesWrapper utilities) {
        String rootCommitHash = "3e6c06b1a28a035e21aa0a736ef80afadc43122c";
        return getCommit(rootCommitHash, utilities);
    }

    public static Commit getCommit(String hash, IUtilitiesWrapper utilities) {
        Path commitFullPath = Paths.get(GitletxPaths.OBJECTS.toString(), hash);
        if (!Files.exists(commitFullPath)) {
            throw new RuntimeException("Commit with " + hash + " not found");
        }
        Commit commit = utilities.readObject(commitFullPath.toFile(), Commit.class);
        return commit;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
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
    public String getParent() {
        return parent;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "message='" + message + '\'' +
                ", date=" + date +
                ", authorName='" + authorName + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                ", tree='" + tree + '\'' +
                ", parent='" + parent + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
