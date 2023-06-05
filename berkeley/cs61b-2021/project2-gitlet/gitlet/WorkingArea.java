package gitlet;

import gitlet.interfaces.IGitletPathsWrapper;
import gitlet.interfaces.IStagingArea;
import gitlet.interfaces.IUtilitiesWrapper;
import gitlet.interfaces.IWorkingArea;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkingArea implements IWorkingArea {
    public final static String WD = Path.of(System.getProperty("user.dir")).toString();
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
    private final IUtilitiesWrapper utilities;
    private final IGitletPathsWrapper gitletPaths;
    private final String currentWorkingDirectory;


    public WorkingArea(IUtilitiesWrapper utilities, IGitletPathsWrapper gitletPaths) {
        this.utilities = utilities;
        this.gitletPaths = gitletPaths;
        this.currentWorkingDirectory = gitletPaths.getWorkingDirectory().toString();
    }

    public static List<String> getWorkingFiles() {
        return Utils.plainFilenamesIn(WorkingArea.WD);
    }

    public static boolean exists(String fileName) {
        Path filePath = Path.of(WD, fileName);
        return Files.exists(filePath);
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
    public List<String> getFiles() {
        return utilities.plainFilenamesIn(new File(currentWorkingDirectory));
    }

    @Override
    public String getFileHash(String targetFile) {
        Path path = Paths.get(currentWorkingDirectory, targetFile);

        if (Files.exists(path)) {
            byte[] pathContent = utilities.readContents(path.toFile());
            return utilities.sha1(pathContent);
        }

        throw new RuntimeException("Can't find " + targetFile);
    }

    @Override
    public String getFileHash(Path targetFile) {
        if (Files.exists(targetFile)) {
            byte[] pathContent = utilities.readContents(targetFile.toFile());
            return utilities.sha1(pathContent);
        }

        throw new RuntimeException("Can't find " + targetFile);
    }

    @Override
    public String getFileHash(File targetFile) {
        if (targetFile.exists()) {
            byte[] pathContent = utilities.readContents(targetFile);
            return utilities.sha1(pathContent);
        }

        throw new RuntimeException("Can't find " + targetFile);
    }

    @Override
    public boolean isFileExist(String file) {
        Path path = Paths.get(currentWorkingDirectory, file);
        return Files.exists(path);
    }

    @Override
    public void clear() {
        List<String> workingDirectoryFiles = utilities.plainFilenamesIn(gitletPaths.getWorkingDirectory().toFile());
        for (String file : workingDirectoryFiles) {
            if (excludedFiles.contains(file)) continue;
            WorkingArea.remove(file);
        }
    }

    @Override
    public boolean hasUntrackedFile(IStagingArea stagingArea) {
        HashMap<String, String> stagingAreaMap = stagingArea.loadStagingArea();

        for (String file : getFiles()) {

            // Don't scan excluded files
            if (excludedFiles.contains(file)) continue;

            if (!stagingAreaMap.containsKey(file)) {
                return true;
            }
        }
        return false;
    }
}
