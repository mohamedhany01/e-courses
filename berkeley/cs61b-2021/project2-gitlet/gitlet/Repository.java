package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

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
    public static final File INDEX = initializeStagingArea();
    public static final File OBJECTS = initializeRepositoryArea();
    public static final File HEAD = initializeHEAD();
    public static final File MASTER = null;

    private static LinkedList<Commit> HISTORY = null;

    public static void initializeRepository() {
        Blob blob = new Blob();
        Tree tree = new Tree(blob);
        Commit rootCommit = new Commit(
                Commit.getDefaultMessage(),
                Commit.getDefaultData(),
                Commit.getDefaultAuthorName(),
                Commit.getDefaultAuthorEmail(),
                tree.getHash(),
                Commit.calcHash(blob.getHash(),
                        tree.getHash()),
                Commit.getDefaultParent()
        );
        storeObjects(tree, rootCommit, blob);
        updateHeadPointer(HEAD, rootCommit.getHash());
    }

    // TODO: HISTORY loads in request only find a better solution
    public static void repoLog() {
        HISTORY = loadHistory();

        for (Commit c : HISTORY) {
            System.out.println(c);
        }
    }

    private static LinkedList<Commit> loadHistory() {
        Commit commit = Commit.loadCommit(readHead(HEAD));

        if (commit == null) {
            return null;
        }

        LinkedList<Commit> history = new LinkedList<>();
        while (true) {
            history.addLast(commit);
            if (commit.getParent() == null) {
                break;
            }
            commit = Commit.loadCommit(commit.getParent());
        }

        return history;
    }

    public static void repoFind(String[] args) {
        HISTORY = loadHistory();

        String commitMassage = args[1];

        for (Commit c : HISTORY) {
            if (c.getMessage().equals(commitMassage)) {
                System.out.println(c.getHash());
            }
        }

//        System.out.println(commitMassage);
    }

    public static void repoStatus() {
        HashMap<String, String> stagingArea = loadStagingArea();
        // TODO: enhance this logic, because we have redundant logic in all display methods
        displayStagedFiles(stagingArea);
        displayModifiedFiles(stagingArea);
        displayUntrackedFiles(stagingArea);
    }

    public static void repoStage(String[] args) {
        // TODO: trace this after adding `gitlet` as well
        // TODO: modify this to accept only one file, not multiple files
        // Add to the staging area
        HashMap<String, String> stagingArea = loadStagingArea();

        for (int i = 1; i < args.length; i++) {
            Path fullPath = Paths.get(CWD.getPath(), args[i]);
            if (!Files.exists(fullPath)) {
                throw new RuntimeException("Can't handle {" + fullPath.getFileName() + "} it isn't exist!");
            }

            // Create new Blob
            Blob blob = new Blob(fullPath);

            if (!stagingArea.containsValue(blob.getHash())) {
                // Modified and thus it will be updated
                if (stagingArea.containsKey(blob.getFilePath())) {
                    stagingArea.put(blob.getFilePath(), blob.getHash());
                } else {
                    // It's totally a new entry
                    stagingArea.put(blob.getFilePath(), blob.getHash());
                }
            }
        }

        updateStagingArea(stagingArea);
    }

    public static void repoUnstage(String[] args) {

        // TODO: this operation supports only one file per-time, so maybe supporting multiple file if possible
        HashMap<String, String> stagingArea = loadStagingArea();

        Path fullPath = Paths.get(CWD.getPath(), args[1]);
        if (!Files.exists(fullPath)) {
            throw new RuntimeException("Can't handle {" + fullPath.getFileName() + "} it isn't exist!");
        }

        // Create new Blob
        Blob blob = new Blob(fullPath);

        // TODO: this condition is handle only files in current working directory, no nesting is supported yet
        // If passed file is in the staging area then remove it
        if (stagingArea.containsKey(blob.getFilePath()) && stagingArea.containsValue(blob.getHash())) {
            stagingArea.remove(blob.getFilePath());
        }
        updateStagingArea(stagingArea);
    }

    // TODO: in staging algorithm we have a bug that is affect `findTheCommit()`, we should commit only the files in staging area and the file that is its  hash change only not all files!
    public static void repoCommit(String[] args) {
        String commitMessage = args[1];
        HashMap<String, String> stagingArea = loadStagingArea();

        Blob[] blobs = new Blob[stagingArea.size()];
        int counter = 0;
        for (Map.Entry<String, String> entry : stagingArea.entrySet()) {

            // TODO: modify this odd logic of splitting and make it clear
            StringTokenizer tokenizer = new StringTokenizer(entry.getKey());
            String file = null;
            while (tokenizer.hasMoreElements()) {
                file = tokenizer.nextToken("\\");
            }
            blobs[counter++] = new Blob(Paths.get(CWD.toString(), file));
        }
        Tree tree = new Tree(blobs);

        String parentHash = Commit.loadCommit(readHead(HEAD)).getHash();
        Commit commit = new Commit(
                commitMessage,
                LocalDateTime.now(),
                Commit.getDefaultAuthorName(),
                Commit.getDefaultAuthorEmail(),
                tree.getHash(),
                Commit.calcHash(Utils.sha1(tree.getContent()), tree.getHash()),
                parentHash
        );
        storeObjects(tree, commit, blobs);
        updateHeadPointer(HEAD, commit.getHash());
    }

    private static void displayUntrackedFiles(HashMap<String, String> stagingArea) {
        File workingDirectory = CWD;
        List<String> fileNames = Utils.plainFilenamesIn(workingDirectory);

        System.out.println("\n=== Untracked Files ===");
        for (String fileName : fileNames) {
            Path fullPath = Paths.get(workingDirectory.getPath(), fileName);
            String fileHash = Utils.sha1(Utils.readContents(fullPath.toFile()));
            String fileRelativePath = Paths.get(fullPath.relativize(Repository.CWD.toPath()).toString(), fileName).toString();
            /*
             * If staging area "index" doesn't have any info "empty" or doesn't have file's path and its hash
             * */
            if ((stagingArea.isEmpty() || !stagingArea.containsKey(fileRelativePath)) && !stagingArea.containsValue(fileHash)) {
                System.out.println(fullPath.getFileName());
            }
        }
    }

    private static void displayModifiedFiles(HashMap<String, String> stagingArea) {
        File workingDirectory = CWD;
        List<String> fileNames = Utils.plainFilenamesIn(workingDirectory);

        System.out.println("\n=== Modifications Not Staged For Commit ===");
        for (String fileName : fileNames) {
            Path fullPath = Paths.get(workingDirectory.getPath(), fileName);
            String fileHash = Utils.sha1(Utils.readContents(fullPath.toFile()));
            String fileRelativePath = Paths.get(fullPath.relativize(Repository.CWD.toPath()).toString(), fileName).toString();

            /*
             * If staging area "index" has file path but file's hash not the same
             * */
            if (stagingArea.containsKey(fileRelativePath) && !stagingArea.containsValue(fileHash)) {
                System.out.println(fullPath.getFileName());
            }
        }
    }

    private static void displayStagedFiles(HashMap<String, String> stagingArea) {
        File workingDirectory = CWD;
        List<String> fileNames = Utils.plainFilenamesIn(workingDirectory);

        System.out.println("\n=== Staged Files ===");
        for (String fileName : fileNames) {
            Path fullPath = Paths.get(workingDirectory.getPath(), fileName);
            String fileHash = Utils.sha1(Utils.readContents(fullPath.toFile()));
            String fileRelativePath = Paths.get(fullPath.relativize(Repository.CWD.toPath()).toString(), fileName).toString();

            /*
             * If staging area "index" has info about this blob and local repo "objects/" doesn't
             * */
            if (stagingArea.containsKey(fileRelativePath) && stagingArea.containsValue(fileHash) && !Files.exists(Paths.get(OBJECTS.toString(), fileHash))) {
                System.out.println(fullPath.getFileName());
            }
        }
    }


    public static HashMap<String, String> loadStagingArea() {
        return (HashMap<String, String>) Utils.readObject(INDEX, HashMap.class);
    }

    /////

    private static HashMap<String, String> updateStagingArea(HashMap<String, String> stagingArea) {
        writeObject(INDEX, stagingArea);
        return loadStagingArea();
    }

    private static HashMap<String, String> clearStagingArea(HashMap<String, String> stagingArea) {
        stagingArea.clear();
        return stagingArea;
    }

    private static void storeObjects(Tree tree, Commit commit, Blob... blobs) {
        File treeObject = Paths.get(OBJECTS.toString(), tree.getHash()).toFile();
        File commitObject = Paths.get(OBJECTS.toString(), commit.getHash()).toFile();

        // Write the blob directly to "objects" path, since this list of valid objects
        for (Blob blob : blobs) {
            File blobObject = Paths.get(OBJECTS.toString(), blob.getHash()).toFile();
            Utils.writeObject(blobObject, blob);
        }
        Utils.writeObject(treeObject, tree);
        Utils.writeObject(commitObject, commit);
    }

    private static File initializeGitletDirectory() {
        File gitletRepo = join(CWD, ".gitlet");

        if (!gitletRepo.exists()) {
            gitletRepo.mkdir();
            return gitletRepo;
        }
        return gitletRepo;
//        throw new RuntimeException("A Gitlet version-control system already exists in the current directory.");
    }

    private static File initializeStagingArea() {
        if (GITLET_DIR.exists()) {
            File index = join(GITLET_DIR, "index");
            if (!index.exists()) {
                try {
                    index.createNewFile();

                    // Add hash map as staging area structure
                    HashMap<String, String> stagingAreaMap = new HashMap<>();
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

    private static File initializeHEAD() {
        if (GITLET_DIR.exists()) {
            File head = join(GITLET_DIR, "HEAD");
            if (!head.exists()) {
                try {
                    head.createNewFile();
                    return head;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return head;
        }
        throw new RuntimeException("Can't find {" + GITLET_DIR.getName() + "} directory.");
    }

    private static String updateHeadPointer(File head, String commitHash) {
        Utils.writeContents(head, commitHash);
        return readHead(head);
    }

    private static String readHead(File head) {
        byte[] all = Utils.readContents(head);
        StringBuilder builder = new StringBuilder();
        for (byte b : all) {
            builder.append((char) b);
        }
        return builder.toString();
    }

    /*
     *   TODO: in staging algorithm we have duplication resolve it
     * */
    public static void repoCheckout(String[] args) {
        Commit commit = findTheCommit(args[2]);
        if (commit != null) {
            File treePath = Paths.get(OBJECTS.toString(), commit.getTree()).toFile();
            Tree treeObject = Utils.readObject(treePath, Tree.class);
            String[] blobs = treeObject.getContent();
            for (String blob : blobs) {
                File blobPath = Paths.get(OBJECTS.toString(), blob).toFile();
                Blob blobObject = Utils.readObject(blobPath, Blob.class);
                String decodedContent = new String(blobObject.getContent(), StandardCharsets.UTF_8);
                Path path = Paths.get(CWD.toString(), blobObject.getFileName());
                try {
                    Files.deleteIfExists(path);
                    Files.createFile(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Utils.writeContents(path.toFile(), decodedContent);
            }
        }
    }

    private static Commit findTheCommit(String target) {
        String fileName = target;

        HISTORY = loadHistory();

        for (Commit c : HISTORY) {
            File treePath = Paths.get(OBJECTS.toString(), c.getTree()).toFile();
            Tree treeObject = Utils.readObject(treePath, Tree.class);
            String[] blobs = treeObject.getContent();
//            System.out.println(treeObject);
            for (String blob : blobs) {
                File blobPath = Paths.get(OBJECTS.toString(), blob).toFile();
                Blob blobObject = Utils.readObject(blobPath, Blob.class);
                if (blobObject.getFileName().equals(fileName)) {
                    return c;
                }
            }
        }

        return null;
    }


}
