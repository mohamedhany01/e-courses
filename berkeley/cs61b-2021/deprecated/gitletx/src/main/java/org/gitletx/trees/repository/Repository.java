package org.gitletx.trees.repository;

import org.gitletx.objects.blob.IBlob;
import org.gitletx.objects.commit.ICommit;
import org.gitletx.objects.tree.ITree;
import org.gitletx.utilities.GitletxPaths;
import org.gitletx.utilities.IUtilitiesWrapper;

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
            Path blobPath = Path.of(GitletxPaths.OBJECTS.toString(), blob.getHash());
            utilities.writeObject(blobPath.toFile(), blob);
        }

        Path treePath = Path.of(GitletxPaths.OBJECTS.toString(), tree.getHash());
        Path commitPath = Path.of(GitletxPaths.OBJECTS.toString(), commit.getHash());

        utilities.writeObject(treePath.toFile(), tree);
        utilities.writeObject(commitPath.toFile(), commit);

        return commit;
    }
}
