package org.gitletx.manager;

import org.gitletx.etc.head.IHEAD;
import org.gitletx.objects.blob.IBlob;
import org.gitletx.objects.commit.ICommit;
import org.gitletx.objects.tree.ITree;
import org.gitletx.trees.staging.IStagingArea;
import org.gitletx.utilities.GitletxPaths;
import org.gitletx.utilities.IUtilitiesWrapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalRepositoryManager implements ILocalRepositoryManager {
    public final static Path WORKING_DIRECTORY = Path.of(System.getProperty("user.dir"));
    private static LocalRepositoryManager instance;
    private final Path GITLETX;
    private final Path INDEX;
    private final Path OBJECTS;
    private final Path HEAD;
    private final IUtilitiesWrapper utilities;

    private final IStagingArea stagingArea;

    private final IHEAD head;

    private LocalRepositoryManager(IUtilitiesWrapper utilities, IStagingArea stagingArea, IHEAD head) {
        GITLETX = initializePath(WORKING_DIRECTORY.toString(), ".gitletx", 'D');
        INDEX = initializePath(GITLETX.toString(), "index", 'F');
        OBJECTS = initializePath(GITLETX.toString(), "objects", 'D');
        HEAD = initializePath(GITLETX.toString(), "HEAD", 'F');

        this.utilities = utilities;
        this.stagingArea = stagingArea;
        this.head = head;
    }

    private static Path initializePath(String parent, String pathName, char type) {
        Path newPath = Paths.get(parent, pathName);

        if (!Files.exists(newPath)) {
            if (type == 'D') {
                try {
                    return Files.createDirectory(newPath);
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

    public static boolean isGitletxExists() {
        return Files.exists(Paths.get(WORKING_DIRECTORY.toString(), ".gitletx"));
    }

    public static LocalRepositoryManager create(IUtilitiesWrapper utilities, IStagingArea stagingArea, IHEAD head) {
        if (instance == null) {
            return instance = new LocalRepositoryManager(utilities, stagingArea, head);
        }
        return instance;
    }

    @Override
    public ICommit commitRootCommit(IBlob blob, ITree tree, ICommit commit) {

        // Store the root commit
        Path commitPath = Path.of(OBJECTS.toString(), commit.getHash());
        Path treePath = Path.of(OBJECTS.toString(), tree.getHash());
        Path blobPath = Path.of(OBJECTS.toString(), blob.getHash());

        utilities.writeObject(blobPath.toFile(), blob);
        utilities.writeObject(treePath.toFile(), tree);
        utilities.writeObject(commitPath.toFile(), commit);

        stagingArea.writeStagingArea();

        head.updateHEAD(commit.getHash());

        return commit;
    }

    @Override
    public Path getGitletx() {
        return GITLETX;
    }

    @Override
    public Path getINDEX() {
        return INDEX;
    }

    @Override
    public Path getOBJECTS() {
        return OBJECTS;
    }

    @Override
    public Path getHEAD() {
        return HEAD;
    }

    @Override
    public void showStatus() {
        stagingArea.displayUntrackedFiles();
        stagingArea.displayStagedFiles();
        stagingArea.displayModifiedFiles();
    }

    @Override
    public Path verifyFile(String fileName) {
        Path fullPath = Paths.get(GitletxPaths.WORKING_DIRECTORY.toString(), fileName);
        if (!Files.exists(fullPath)) {
            throw new RuntimeException("Can't handle {" + fullPath.getFileName() + "} it isn't exist!");
        }

        return fullPath;
    }
}
