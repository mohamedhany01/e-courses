package gitlet;

import java.nio.file.Path;

public class Repository implements IRepository {
    private static Repository instance;
    private final IUtilitiesWrapper utilities;

    private Repository(IUtilitiesWrapper utilities) {
        this.utilities = utilities;
    }

    public static Repository create(IUtilitiesWrapper utilities) {
        if (instance == null) {
            return instance = new Repository(utilities);
        }
        return instance;
    }

    @Override
    public ICommit commitObjects(ICommit commit, ITree tree, IBlob... blobs) {

        for (IBlob blob : blobs) {
            Path blobPath = Path.of(GitletPaths.OBJECTS.toString(), blob.getHash());
            utilities.writeObject(blobPath.toFile(), blob);
        }

        Path treePath = Path.of(GitletPaths.OBJECTS.toString(), tree.getHash());
        Path commitPath = Path.of(GitletPaths.OBJECTS.toString(), commit.getHash());

        utilities.writeObject(treePath.toFile(), tree);
        utilities.writeObject(commitPath.toFile(), commit);

        return commit;
    }
}
