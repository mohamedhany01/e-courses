package gitlet.objects;

import gitlet.Utils;
import gitlet.interfaces.IBlob;
import gitlet.trees.WorkingArea;

import java.io.File;
import java.io.Serializable;

public class Blob implements IBlob, Serializable {
    private byte[] fileContent;
    private String hash;
    private String fileName;

    public Blob() {
    }

    @Override
    public byte[] getFileContent() {
        return fileContent;
    }

    private void setFileContent(String fileName) {
        String fullPath = WorkingArea.getPath(fileName).toString();
        this.fileContent = fileName.isEmpty() ? "".getBytes() : Utils.readContents(new File(fullPath));
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void setFileName(String name) {
        this.fileName = name;
        setFileContent(name);
        setHash();
    }

    private void setHash() {
        this.hash = Utils.sha1(getFileContent());
    }
}
