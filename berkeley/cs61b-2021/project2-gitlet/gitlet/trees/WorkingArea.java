package gitlet.trees;

import gitlet.Utils;
import gitlet.interfaces.IStagingArea;
import gitlet.interfaces.IWorkingArea;
import gitlet.objects.Blob;
import gitlet.trees.staging.StagingArea;
import gitlet.trees.staging.StagingEntry;
import gitlet.trees.staging.Status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class WorkingArea implements IWorkingArea {
    //    public final static String WD = Path.of(System.getProperty("user.dir")).toString();
    public final static String WD = Path.of(System.getProperty("user.dir"), "TEMP_TEST").toString();

    private final static List<String> excludedFiles = new ArrayList<>() {
        {
            add(".gitlet");
            add(".gitletignore");

            // Project Data
            add("testCases.txt");
            add("pom.xml");
            add("Makefile");
            add("gitlet-design.md");
            add(".gitignore");
            add("foo-bar-buz.zip");
        }
    };

    public WorkingArea() {
    }

    public static List<String> getWorkingFiles() {
        return Utils.plainFilenamesIn(WD);
    }

    public static Path getPath(String file) {
        return Path.of(WorkingArea.WD, file);
    }

    public static boolean exists(String fileName) {
        return Files.exists(getPath(fileName));
    }

    public static void remove(String file) {
        Path filePath = Path.of(WD, file);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFileHash(String targetFile) {
        Path path = Paths.get(WD, targetFile);

        if (Files.exists(path)) {
            byte[] pathContent = Utils.readContents(path.toFile());
            return Utils.sha1(pathContent);
        }

        return null;
    }

    @Override
    public boolean isFileExist(String file) {
        Path path = Paths.get(WD, file);
        return Files.exists(path);
    }

    @Override
    public void clear() {
        List<String> workingDirectoryFiles = WorkingArea.getWorkingFiles();
        for (String file : workingDirectoryFiles) {
            if (excludedFiles.contains(file)) continue;
            WorkingArea.remove(file);
        }
    }

    @Override
    public boolean hasUntrackedFile(IStagingArea stagingArea) {
        HashMap<String, String> stagingAreaMap = stagingArea.loadStagingArea();

        for (String file : WorkingArea.getWorkingFiles()) {

            // Don't scan excluded files
            if (excludedFiles.contains(file)) continue;

            if (!stagingAreaMap.containsKey(file)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addBlobs(TreeMap<String, Blob> blobs) {
        StagingArea stagingArea = new StagingArea();

        stagingArea.clearAdditions();

        for (Map.Entry<String, Blob> blobEntry : blobs.entrySet()) {
            Blob blob = blobEntry.getValue();

            Utils.writeContents(new File(getPath(blob.getFileName()).toString()), blob.getFileContent());

            StagingEntry entry = new StagingEntry(blob.getHash());
            entry.setStatus(Status.COMMITTED);

            stagingArea.updatedEntryStatus(blob.getFileName(), entry);
        }

        stagingArea.saveChanges();
    }

    @Override
    public void addBlob(Blob blob) {
        Utils.writeContents(new File(getPath(blob.getFileName()).toString()), blob.getFileContent());
    }
}
