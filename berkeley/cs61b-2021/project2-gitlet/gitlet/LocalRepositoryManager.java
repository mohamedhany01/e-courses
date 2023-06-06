package gitlet;

import gitlet.interfaces.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalRepositoryManager implements ILocalRepositoryManager {
    private final Path gitlet;
    private final Path INDEX;
    private final Path OBJECTS;
    private final Path HEAD;
    private final Path BRANCHES;
    private final IUtilitiesWrapper utilities;

    private final IStagingArea stagingArea;

    private final IHEAD head;

    public LocalRepositoryManager(IUtilitiesWrapper utilities, IStagingArea stagingArea, IHEAD head) {
        gitlet = initializePath(WorkingArea.WD, ".gitlet", 'D');
        INDEX = initializePath(Repository.GITLET, "index", 'F');
        OBJECTS = initializePath(Repository.GITLET, "objects", 'D');
        HEAD = initializePath(Repository.GITLET, "HEAD", 'F');
        BRANCHES = initializePath(Repository.GITLET, Path.of(
                "refs",
                "heads"
        ).toString(), 'D');

        this.utilities = utilities;
        this.stagingArea = stagingArea;
        this.head = head;
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
        head.updateHEAD(commit.getHash());

        return commit;
    }

    @Override
    public Path getgitlet() {
        return gitlet;
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
    public void showStatus() {
        stagingArea.getFilesStatus();
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
