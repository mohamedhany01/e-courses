package gitlet;

import gitlet.interfaces.ICommit;
import gitlet.interfaces.IUtilitiesWrapper;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public static String formatCommitData(LocalDateTime rowData) {
        ZoneOffset offset = ZoneOffset.ofHours(-8);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss uuuu Z");

        OffsetDateTime offsetDateTime = OffsetDateTime.of(rowData, offset);
        String result = offsetDateTime.format(pattern);
        return result;
    }

    public static Commit getCommit(String hash, IUtilitiesWrapper utilities) {
        if (hash == null) {
            return null;
        }
        Path commitFullPath = Paths.get(GitletPaths.OBJECTS.toString(), hash);
        if (!Files.exists(commitFullPath)) {
            throw new RuntimeException("Commit with " + hash + " not found");
        }
        Commit commit = utilities.readObject(commitFullPath.toFile(), Commit.class);
        return commit;
    }

    public static Blob hasFile(String file, String hash, IUtilitiesWrapper utilities) {
        Commit commit = Commit.getCommit(hash, utilities);
        Tree commitTree = Tree.getTree(commit.getTree(), utilities);
        for (Object blobHash : commitTree.getContent()) {
            Blob blob = Blob.getBlob((String) blobHash, utilities);
            if (blob.getFileName().equals(file)) {
                return blob;
            }
        }
        return null;
    }

    public static List<Object> getBlobs(String hash, IUtilitiesWrapper utilities) {
        Commit commit = Commit.getCommit(hash, utilities);
        Tree commitTree = Tree.getTree(commit.getTree(), utilities);
        return commitTree.getContent();
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
