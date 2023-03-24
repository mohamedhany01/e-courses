package gitlet;

import gitlet.interfaces.*;

import java.nio.file.Path;

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

    @Override
    public ICommit commitObjects(ICommit commit, ITree tree, IBlob... blobs) {

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
}
