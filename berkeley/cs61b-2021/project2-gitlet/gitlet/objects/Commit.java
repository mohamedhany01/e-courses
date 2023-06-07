package gitlet.objects;

import gitlet.Utils;
import gitlet.interfaces.ICommit;
import gitlet.trees.Repository;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Commit implements ICommit, Serializable {
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
    public static Commit getRootCommit() {
        String rootCommitHash = "3e6c06b1a28a035e21aa0a736ef80afadc43122c";
        return getCommit(rootCommitHash);
    }

    public static String formatCommitData(LocalDateTime rowData) {
        ZoneOffset offset = ZoneOffset.ofHours(-8);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss uuuu Z");

        OffsetDateTime offsetDateTime = OffsetDateTime.of(rowData, offset);
        String result = offsetDateTime.format(pattern);
        return result;
    }

    public static Commit getCommit(String hash) {
        if (hash == null) {
            return null;
        }

        String commitPath = Path.of(Repository.OBJECTS, hash).toString();

        if (!Repository.isInRepository(hash)) {
            return null;
        }

        return Utils.readObject(new File(commitPath), Commit.class);
    }

    public static Blob hasFile(String file, String hash) {
        Commit commit = Commit.getCommit(hash);
        Tree commitTree = Tree.getTree(commit.getTree());
        for (Object blobHash : commitTree.getBlobs()) {
            Blob blob = Blob.getBlob((String) blobHash);
            if (blob.getFileName().equals(file)) {
                return blob;
            }
        }
        return null;
    }

    public static List<Object> getBlobs(String hash) {
        Commit commit = Commit.getCommit(hash);
        Tree commitTree = Tree.getTree(commit.getTree());
        return commitTree.getBlobs();
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
