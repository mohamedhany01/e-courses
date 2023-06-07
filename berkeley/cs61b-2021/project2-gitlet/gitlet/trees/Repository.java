package gitlet.trees;

import gitlet.*;
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

    public static void initialize() {
        Repository.checkGitletRepository();
        Repository.initializeCorePaths();
        GLStagingArea.initialize();

        // Replace ZoneId.systemDefault() with ZoneId.of("UTC-8") should store data as Wed Dec 31 16:00:00 1969 -0800
        LocalDateTime zeroDate = Instant.ofEpochSecond(0).atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Prepare root commit
        Blob blob = new Blob(
        );
        blob.setFileName("");
        Tree tree = new Tree();
        tree.setBlob(blob.getHash());
        tree.calculateContentHash();
        Commit commit = new Commit(
                "initial commit",
                zeroDate,
                "foo",
                "foo@foo.foo",
                tree.getHash(),
                null,
                Utils.sha1(tree.getHash()) // TODO: find is this the right way to calculate hash for a commit?
        );
        Repository.commit(blob, tree, commit);
        Repository.newBranch("master", commit.getHash());
        HEAD.move("master");
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
        String hash = HEAD.getBranchHash();
        Commit lastCommit = Commit.getCommit(hash);
        Tree tree = Tree.getTree(lastCommit.getTree());

        // Load last commit files using its hash from HEAD pointer
        for (Object object : tree.getContent()) {
            Blob blob = Blob.getBlob((String) object);
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
