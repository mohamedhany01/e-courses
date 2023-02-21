package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gitlet.Utils.join;
import static gitlet.Utils.writeObject;

// TODO: any imports you need here

/**
 * Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = initializeGitletDirectory();
    public static final File TEMP = initializeTempDirectory();
    public static final File NULL_FILE = initializeNULLFile();
    public static final File INDEX = initializeStagingArea();
    public static final File OBJECTS = initializeRepositoryArea();
    public static final File HEAD = null;
    public static final File MASTER = null;

    public static void initializeRepository() {
        Blob blob = new Blob(NULL_FILE.toPath());
        Tree tree = new Tree(blob.getHash());
        Commit commit = new Commit(
                Commit.getDefaultMessage(),
                Commit.getDefaultData(),
                Commit.getDefaultAuthorName(),
                Commit.getDefaultAuthorEmail(),
                tree.getHash(),
                Commit.calcHash(blob.getHash(), tree.getHash()),
                Commit.getDefaultParent()
        );
        storeObjects(blob, tree, commit);
    }

    public static void repoStatus() {
        HashMap<String, Blob> stagingArea = loadStagingArea();
        displayUntrackedFiles(stagingArea);
    }

    public static void repoStage(String [] args) {
        // TODO: trace this after adding `gitlet` as well
        // Add to the staging area
        HashMap<String, Blob> stagingArea = loadStagingArea();

        for (int i = 1; i < args.length; i++) {
            Path fullPath = Paths.get(CWD.getPath(), args[i]);
            if (!Files.exists(fullPath)) {
                throw new RuntimeException("Can't handle {" + fullPath.getFileName() + "} it isn't exist!" );
            }

            // Create new Blob
            Blob blob = new Blob(fullPath);
            stagingArea.put(blob.getHash(), blob);
        }

        stagingArea = updateStagingArea(stagingArea);
        
        for (Map.Entry<String, Blob> entry:stagingArea.entrySet()) {
            System.out.println(entry.getValue().getFileName() + " " + entry.getKey());
        }
    }

    private static void displayUntrackedFiles(HashMap<String, Blob> stagingArea) {
        File workingDirectory = CWD;
        List<String> fileNames = Utils.plainFilenamesIn(workingDirectory);

        System.out.println("Untracked Files");
        for (String fileName : fileNames) {
            Path fullPath = Paths.get(workingDirectory.getPath(), fileName);
            String fileHash = Utils.sha1(Utils.readContents(fullPath.toFile()));
            if (!stagingArea.containsKey(fileHash)) {
                System.out.println("\t" + fullPath.getFileName());
            }
        }
    }

    private static HashMap<String, Blob> loadStagingArea() {
        return (HashMap<String, Blob>) Utils.readObject(INDEX, HashMap.class);
    }

    private static HashMap<String, Blob> updateStagingArea(HashMap<String, Blob> stagingArea) {
        writeObject(INDEX, stagingArea);
        return loadStagingArea();
    }

    private static void storeObjects(Blob blob, Tree tree, Commit commit) {
        File blobObject = Paths.get(OBJECTS.toString(), blob.getHash()).toFile();
        File treeObject = Paths.get(OBJECTS.toString(), tree.getHash()).toFile();
        File commitObject = Paths.get(OBJECTS.toString(), commit.getHash()).toFile();

        Utils.writeObject(blobObject, blob);
        Utils.writeObject(treeObject, tree);
        Utils.writeObject(commitObject, commit);
    }

    private static File initializeNULLFile() {
        if (TEMP.exists()) {
            File nullFile = join(TEMP, "NULL");
            if (!nullFile.exists()) {
                try {
                    nullFile.createNewFile();
                    return nullFile;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return nullFile;
        }
        throw new RuntimeException("Can't find {" + TEMP.getName() + "} directory.");
    }

    private static File initializeGitletDirectory() {
        File gitletRepo = join(CWD, ".gitlet");

        if (!gitletRepo.exists()) {
            gitletRepo.mkdir();
            return gitletRepo;
        }
        return gitletRepo;
    }

    private static File initializeStagingArea() {
        if (GITLET_DIR.exists()) {
            File index = join(GITLET_DIR, "index");
            if (!index.exists()) {
                try {
                    index.createNewFile();

                    // Add hash map as staging area structure
                    HashMap<String, Blob> stagingAreaMap = new HashMap<>();
                    writeObject(index, stagingAreaMap);
                    return index;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return index;
        }
        throw new RuntimeException("Can't find {" + GITLET_DIR.getName() + "} directory.");
    }

    private static File initializeRepositoryArea() {
        if (GITLET_DIR.exists()) {
            File objects = join(GITLET_DIR, "objects");
            if (!objects.exists()) {
                objects.mkdir();
                return objects;
            }
            return objects;
        }
        throw new RuntimeException("Can't find {" + GITLET_DIR.getName() + "} directory.");
    }

    private static File initializeTempDirectory() {
        if (GITLET_DIR.exists()) {
            File temp = join(GITLET_DIR, "temp");
            if (!temp.exists()) {
                temp.mkdir();
                return temp;
            }
            return temp;
        }
        throw new RuntimeException("Can't find {" + GITLET_DIR.getName() + "} directory.");
    }
}
