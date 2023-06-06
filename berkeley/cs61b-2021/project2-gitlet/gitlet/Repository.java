package gitlet;

import gitlet.interfaces.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeMap;

public class Repository implements IRepository {
    public final static String GITLET = Path.of(WorkingArea.WD, ".gitlet").toString();
    public final static String INDEX = Path.of(GITLET, "index").toString();
    public final static String OBJECTS = Path.of(GITLET, "objects").toString();
    public final static String HEAD_POINTER = Path.of(GITLET, "HEAD").toString();
    public final static String BRANCHES = Path.of(GITLET, "refs", "heads").toString();
    private static Repository instance;
    private final IUtilitiesWrapper utilities;
    private final IGitletPathsWrapper gitletPaths;

    private Repository(IUtilitiesWrapper utilities, IGitletPathsWrapper gitletPaths) {
        this.utilities = utilities;
        this.gitletPaths = gitletPaths;
    }

    public static Repository create(IUtilitiesWrapper utilities, IGitletPathsWrapper gitletPaths) {
        if (instance == null) {
            return instance = new Repository(utilities, gitletPaths);
        }
        return instance;
    }

    public static boolean isInRepository(String hash, IGitletPathsWrapper gitletPaths) {
        Path objectPath = Paths.get(gitletPaths.getObjects().toString(), hash);
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
            Path blobPath = Path.of(gitletPaths.getObjects().toString(), blob.getHash());
            utilities.writeObject(blobPath.toFile(), blob);
        }

        Path treePath = Path.of(gitletPaths.getObjects().toString(), tree.getHash());
        Path commitPath = Path.of(gitletPaths.getObjects().toString(), commit.getHash());

        utilities.writeObject(treePath.toFile(), tree);
        utilities.writeObject(commitPath.toFile(), commit);

        return commit;
    }

    @Override
    public String createBranch(String name, String commitHash) {
        Path branchFullPath = Path.of(gitletPaths.getRefs().toString(), name);
        utilities.writeContents(branchFullPath.toFile(), commitHash);
        return utilities.readContentsAsString(branchFullPath.toFile());
    }

    @Override
    public boolean removeBranch(String name) {
        Path branchFullPath = Path.of(gitletPaths.getRefs().toString(), name);
        try {
            return Files.deleteIfExists(branchFullPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasBranch(String branchName) {
        return Files.exists(Path.of(gitletPaths.getRefs().toString(), branchName));
    }

    @Override
    public String updateBranch(String name, String commitHash) {
        return createBranch(name, commitHash);
    }

    @Override
    public String getBranchHash(String name) {
        if (hasBranch(name)) {
            Path branchPath = Path.of(gitletPaths.getRefs().toString(), name);
            return utilities.readContentsAsString(branchPath.toFile());
        }
        return null;
    }

    @Override
    public List<String> getAllBranches() {
        Path branches = gitletPaths.getRefs();
        if (!Files.exists(branches)) {
            System.exit(0);
        }

        return utilities.plainFilenamesIn(branches.toFile());
    }
}
