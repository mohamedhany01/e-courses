package gitlet.interfaces;

import gitlet.objects.Blob;

import java.util.TreeMap;

public interface IWorkingArea {
    String getFileHash(String targetFile);

    boolean isFileExist(String file);

    void clear();

    boolean hasUntrackedFile(IStagingArea stagingArea);

    void addBlobs(TreeMap<String, Blob> blobs);

    void addBlob(Blob blob);

    void addBlob(Blob blob, String content);
}
