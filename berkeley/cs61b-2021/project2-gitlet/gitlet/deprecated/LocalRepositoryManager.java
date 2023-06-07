package gitlet.deprecated;

import gitlet.Utils;
import gitlet.interfaces.IBlob;
import gitlet.interfaces.ICommit;
import gitlet.interfaces.ILocalRepositoryManager;
import gitlet.interfaces.ITree;
import gitlet.trees.Repository;
import gitlet.trees.WorkingArea;
import gitlet.trees.extra.HEAD;
import gitlet.trees.staging.GLStagingArea;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalRepositoryManager implements ILocalRepositoryManager {

    public LocalRepositoryManager() {
        initializePath(WorkingArea.WD, ".gitlet", 'D');
        initializePath(Repository.GITLET, "index", 'F');
        initializePath(Repository.GITLET, "objects", 'D');
        initializePath(Repository.GITLET, "HEAD", 'F');
        initializePath(Repository.GITLET, Path.of(
                "refs",
                "heads"
        ).toString(), 'D');
    }

    private static Path initializePath(String parent, String pathName, char type) {
        Path newPath = Paths.get(parent, pathName);

        if (!Files.exists(newPath)) {
            if (type == 'D') {
                try {
                    return Files.createDirectories(newPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (type == 'F') {
                try {
                    return Files.createFile(newPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException("Type is unknown");
            }
        }
        return newPath;
    }

    public static boolean isgitletExists() {
        return Files.exists(Path.of(WorkingArea.WD, ".gitlet"));
    }

    @Override
    public ICommit commitRootCommit(IBlob blob, ITree tree, ICommit commit) {

        // Store the root commit
        Path commitPath = Path.of(Repository.OBJECTS, commit.getHash());
        Path treePath = Path.of(Repository.OBJECTS, tree.getHash());
        Path blobPath = Path.of(Repository.OBJECTS, blob.getHash());

        Utils.writeObject(blobPath.toFile(), blob);
        Utils.writeObject(treePath.toFile(), tree);
        Utils.writeObject(commitPath.toFile(), commit);

        // TODO: remove
        GLStagingArea.initialize();
        HEAD.move("master");

        return commit;
    }

    @Override
    public Path getgitlet() {
        return Path.of(Repository.GITLET);
    }

    @Override
    public Path getINDEX() {
        return Path.of(Repository.INDEX);
    }

    @Override
    public Path getOBJECTS() {
        return Path.of(Repository.OBJECTS);
    }

    @Override
    public Path getHEAD() {
        return Path.of(Repository.HEAD_POINTER);
    }

    @Override
    public Path getRefs() {
        return Path.of(Repository.BRANCHES);
    }

    @Override
    public Path verifyFile(String fileName) {
        Path fullPath = Paths.get(WorkingArea.WD, fileName);
        if (!Files.exists(fullPath)) {
            throw new RuntimeException("Can't handle {" + fullPath.getFileName() + "} it isn't exist!");
        }

        return fullPath;
    }
}
