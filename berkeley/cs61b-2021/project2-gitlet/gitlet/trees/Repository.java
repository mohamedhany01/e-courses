package gitlet.trees;

import gitlet.Utils;
import gitlet.interfaces.IBlob;
import gitlet.interfaces.ICommit;
import gitlet.interfaces.ITree;
import gitlet.objects.Blob;
import gitlet.objects.Commit;
import gitlet.objects.Tree;
import gitlet.trees.extra.Branch;
import gitlet.trees.extra.HEAD;
import gitlet.trees.staging.StagingArea;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TreeMap;

public class Repository {
    public final static String GITLET = Path.of(WorkingArea.WD, ".gitlet").toString();
    public final static String INDEX = Path.of(GITLET, "index").toString();
    public final static String OBJECTS = Path.of(GITLET, "objects").toString();
    public final static String HEAD_POINTER = Path.of(GITLET, "HEAD").toString();
    public final static String BRANCHES = Path.of(GITLET, "refs", "heads").toString();


    public static <T extends Serializable> T getObject(String hash, Class<T> type) {
        if (hash == null) {
            return null;
        }

        String [] path = Utils.splitHash(hash, 2);
        String directory = path[0];
        String object = path[1];

        if (!Repository.objectExists(directory, object)) {
            return null;
        }

        return Utils.readObject(new File(Path.of(Repository.getObjectPath(directory).toString(), object).toString()), type);
    }

    public static Path getObjectPath(String file) {
        return Path.of(Repository.OBJECTS, file);
    }

    public static Path getBranchesPath(String file) {
        return Path.of(Repository.BRANCHES, file);
    }

    public static Path getHeadPath(String file) {
        return Path.of(Repository.HEAD_POINTER, file);
    }

    public static Path getIndexPath(String file) {
        return Path.of(Repository.INDEX, file);
    }

    public static void initialize() {
        Repository.checkGitletRepository();
        Repository.initializeCorePaths();
        StagingArea.initialize();

        // Replace ZoneId.systemDefault() with ZoneId.of("UTC-8") should store data as Wed Dec 31 16:00:00 1969 -0800
        LocalDateTime zeroDate = Instant.ofEpochSecond(0).atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Prepare root commit
        Blob blob = new Blob();
        blob.setFileName("");

        Tree tree = new Tree();
        tree.addBlob(blob.getHash());

        Commit commit = new Commit();
        commit.setMessage("initial commit");
        commit.setDate(Utils.getFormattedDate(zeroDate));
        commit.setTree(tree.getHash());
        commit.setParent(null);
        commit.setHash(Commit.calculateHash(commit));

        Repository.commit(blob, tree, commit);
        Branch.create("master", commit.getHash());
        HEAD.move("master");
    }

    public static Blob getBlob(String file, String hash) {
        Commit commit = Repository.getObject(hash, Commit.class);
        Tree commitTree = Repository.getObject(commit.getTree(), Tree.class);
        for (Object blobHash : commitTree.getBlobs()) {
            Blob blob = Repository.getObject((String) blobHash, Blob.class);
            if (blob.getHash().equals(file)) {
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

    public static void commit(IBlob blob, ITree tree, ICommit commit) {
        storeObject(blob.getHash(), blob);
        storeObject(tree.getHash(), tree);
        storeObject(commit.getHash(), commit);
    }

    public static void commit(ITree tree, ICommit commit) {
        storeObject(tree.getHash(), tree);
        storeObject(commit.getHash(), commit);
    }

    public static ICommit commit(ICommit commit, ITree tree, List<? extends IBlob> blobs) {
        for (IBlob blob : blobs) {
            storeObject(blob.getHash(), blob);
        }

        storeObject(tree.getHash(), tree);
        storeObject(commit.getHash(), commit);

        return commit;
    }

    public static void storeObject(String hash, Serializable object) {
        String[] path = Utils.splitHash(hash, 2);
        String directory = path[0];
        String file = path[1];
        String directoryFullPath = Repository.getObjectPath(directory).toString();
        String fileFullPath = Path.of(directoryFullPath, file).toString();

        if (Repository.directoryExists(directory)) {
            Utils.writeObject(new File(fileFullPath), object);
        } else {
            try {
                Files.createDirectory(Path.of(directoryFullPath));
                Utils.writeObject(new File(fileFullPath), object);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void initializeCorePaths() {
        try {
            Files.createDirectory(
                    Path.of(Repository.GITLET)
            );
            Files.createDirectory(
                    Path.of(Repository.OBJECTS)
            );
            Files.createDirectories(
                    Path.of(Repository.BRANCHES)
            );
            Files.createFile(
                    Path.of(Repository.HEAD_POINTER)
            );
            Files.createFile(
                    Path.of(Repository.INDEX)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkGitletRepository() {
        if (Files.exists(Path.of(Repository.GITLET))) {
            Utils.exit("A Gitlet version-control system already exists in the current directory.");
        }
    }

    public static boolean directoryExists(String directory) {
        return Files.exists(getObjectPath(directory));
    }

    public static boolean objectExists(String directory, String hash) {
        String filePath = Path.of(getObjectPath(directory).toString(), hash).toString();
        return Files.exists(Path.of(filePath));
    }

    public static TreeMap<String, String> getLastCommitFiles() {
        TreeMap<String, String> lastCommitFiles = new TreeMap<>();
        String hash = HEAD.getHash();
        Commit lastCommit = Repository.getObject(hash, Commit.class);
        Tree tree = Repository.getObject(lastCommit.getTree(), Tree.class);

        // Load last commit files using its hash from HEAD pointer
        for (Object object : tree.getBlobs()) {
            Blob blob = Repository.getObject((String) object, Blob.class);
            lastCommitFiles.put(blob.getFileName(), blob.getHash());
        }
        return lastCommitFiles;
    }
}
