package gitlet;

import gitlet.interfaces.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Repository implements IRepository {
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
    public String updateBranch(String name, String commitHash) {
        return createBranch(name, commitHash);
    }
}
