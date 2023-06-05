package gitlet.interfaces;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface IWorkingArea {
    List<String> getFiles();

    String getFileHash(String targetFile);

    String getFileHash(Path targetFile);

    String getFileHash(File targetFile);

    boolean isFileExist(String file);

    void clear();

    boolean hasUntrackedFile(IStagingArea stagingArea);
}
