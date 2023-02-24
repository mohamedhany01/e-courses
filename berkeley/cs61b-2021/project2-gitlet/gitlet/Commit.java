package gitlet;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author Mohamed Hany
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

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
     * The type of this commit.
     */
    private static String type = "commit";
    /**
     * The tree hash of this commit.
     */
    private final String tree;

    /**
     * The hash of this commit.
     */
    private final String hash;

    /**
     * The parent hash of this commit.
     */
    private final String parent;

    public Commit(
            String message,
            LocalDateTime date,
            String authorName,
            String authorEmail,
            String tree,
            String hash,
            String parent
    ) {
        this.message = message;
        this.type = Commit.getType();
        this.date = date;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.tree = tree;
        this.hash = hash;
        this.parent = parent;
    }

    public static String calcHash(String... commits) {
        return Utils.sha1(commits);
    }

    // These defaults are used for root commit only
    public static LocalDateTime getDefaultData() {
        LocalDateTime zeroDate = Instant.ofEpochSecond(0).atZone(ZoneId.of("UTC")).toLocalDateTime();
        return zeroDate;
    }

    public static String getDefaultMessage() {
        return "initial commit";
    }

    public static String getDefaultParent() {
        return null;
    }

    public static String getDefaultAuthorName() {
        return "Foo";
    }

    public static String getDefaultAuthorEmail() {
        return "foo@example.com";
    }

    public static String getType() {
        return type;
    }

    public String getHash() {
        return hash;
    }

    public String getTree() {
        return tree;
    }

    public String getParent() {
        return parent;
    }

    public static Commit loadCommit(String hash) {
        Path objectPath = Paths.get(Repository.OBJECTS.toString(), hash);
        return Utils.readObject(objectPath.toFile(), Commit.class);
    }
    @Override
    public String toString() {
        return "Commit{" +
                "message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", authorName='" + authorName + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                ", tree='" + tree + '\'' +
                ", hash='" + hash + '\'' +
                ", parent='" + parent + '\'' +
                '}';
    }
}
