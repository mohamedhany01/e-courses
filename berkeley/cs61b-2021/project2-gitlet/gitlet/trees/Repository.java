package gitlet.trees;

import gitlet.HEAD;
import gitlet.Utils;
import gitlet.interfaces.IBlob;
import gitlet.interfaces.ICommit;
import gitlet.interfaces.IRepository;
import gitlet.interfaces.ITree;
import gitlet.objects.Blob;
import gitlet.objects.Commit;
import gitlet.objects.Tree;
import gitlet.trees.staging.GLStagingArea;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TreeMap;

public class Repository implements IRepository {
    public final static String GITLET = Path.of(WorkingArea.WD, ".gitlet").toString();
    public final static String INDEX = Path.of(GITLET, "index").toString();
    public final static String OBJECTS = Path.of(GITLET, "objects").toString();
    public final static String HEAD_POINTER = Path.of(GITLET, "HEAD").toString();
    public final static String BRANCHES = Path.of(GITLET, "refs", "heads").toString();

    public Repository() {
    }

    public static <T extends Serializable> T getObject(String hash, Class<T> type) {
        if (hash == null) {
            return null;
        }

        if (!Repository.isInRepository(hash)) {
            return null;
        }

        return Utils.readObject(new File(getObjectPath(hash).toString()), type);
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
        GLStagingArea.initialize();

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
        Repository.newBranch("master", commit.getHash());
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

    private static void newBranch(String name, String hash) {
        String branch = Path.of(Repository.BRANCHES, name).toString();
        Utils.writeContents(new File(branch), hash);
    }

    public static void commit(IBlob blob, ITree tree, ICommit commit) {
        storeObject(blob.getHash(), blob);
        storeObject(tree.getHash(), tree);
        storeObject(commit.getHash(), commit);
    }

    private static void storeObject(String hash, Serializable object) {
        String path = Path.of(Repository.OBJECTS, hash).toString();
        Utils.writeObject(new File(path), object);
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

    public static boolean isInRepository(String hash) {
        Path objectPath = Paths.get(Repository.OBJECTS, hash);
        return Files.exists(objectPath);
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

    @Override
    public ICommit commitObjects(ICommit commit, ITree tree, List<? extends IBlob> blobs) {

        for (IBlob blob : blobs) {
            Path blobPath = Path.of(Repository.OBJECTS, blob.getHash());
            Utils.writeObject(blobPath.toFile(), blob);
        }

        Path treePath = Path.of(Repository.OBJECTS, tree.getHash());
        Path commitPath = Path.of(Repository.OBJECTS, commit.getHash());

        Utils.writeObject(treePath.toFile(), tree);
        Utils.writeObject(commitPath.toFile(), commit);

        return commit;
    }

    @Override
    public String createBranch(String name, String commitHash) {
        Path branchFullPath = Path.of(Repository.BRANCHES, name);
        Utils.writeContents(branchFullPath.toFile(), commitHash);
        return Utils.readContentsAsString(branchFullPath.toFile());
    }

    @Override
    public boolean removeBranch(String name) {
        Path branchFullPath = Path.of(Repository.BRANCHES, name);
        try {
            return Files.deleteIfExists(branchFullPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasBranch(String branchName) {
        return Files.exists(Path.of(Repository.BRANCHES, branchName));
    }

    @Override
    public String updateBranch(String name, String commitHash) {
        return createBranch(name, commitHash);
    }

    @Override
    public String getBranchHash(String name) {
        if (hasBranch(name)) {
            Path branchPath = Path.of(Repository.BRANCHES, name);
            return Utils.readContentsAsString(branchPath.toFile());
        }
        return null;
    }

    @Override
    public List<String> getAllBranches() {
        Path branches = Path.of(Repository.BRANCHES);
        if (!Files.exists(branches)) {
            System.exit(0);
        }

        return Utils.plainFilenamesIn(new File(Repository.BRANCHES));
    }
}
