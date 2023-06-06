package gitlet.interfaces;

public interface IWorkingArea {
    String getFileHash(String targetFile);

    boolean isFileExist(String file);

    void clear();

    boolean hasUntrackedFile(IStagingArea stagingArea);
}
