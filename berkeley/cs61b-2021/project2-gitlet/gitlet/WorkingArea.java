package gitlet;

import gitlet.interfaces.IGitletPathsWrapper;
import gitlet.interfaces.IUtilitiesWrapper;
import gitlet.interfaces.IWorkingArea;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class WorkingArea implements IWorkingArea {
    private final IUtilitiesWrapper utilities;
    private final IGitletPathsWrapper gitletPaths;
    private final String currentWorkingDirectory;

    public WorkingArea(IUtilitiesWrapper utilities, IGitletPathsWrapper gitletPaths) {
        this.utilities = utilities;
        this.gitletPaths = gitletPaths;
        this.currentWorkingDirectory = gitletPaths.getWorkingDirectory().toString();
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
}
