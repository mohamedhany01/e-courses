package com.gitletx.objects.blob;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a gitlet blob object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author Mohamed Hany
 */
public class GLBlob implements Serializable, IGLBlob {

    private final String fileName;
    private final String filePath;
    private final String hash;
    private final String type = "blob";

    private byte[] content;

    public GLBlob() {
        this.fileName = null;
        this.content = null;
        this.hash = null;
        this.filePath = null;
//        this.hash = Utils.sha1("");
//        this.filePath = Repository.CWD.toString();
    }

    public GLBlob(Path filePath) {
        this.fileName = setFileName(filePath);
        this.hash = setHash(filePath);
        this.filePath = setFilePath(filePath);
    }

    public String getType() {
        return this.type;
    }

    private String setHash(Path path) {
        // Set content as well
//        this.content = Utils.readContents(path.toFile());
        this.content = null;

//        return Utils.sha1(this.content);
        return null;
    }

    private String setFilePath(Path path) {
        return null;
//        return Paths.get(path.relativize(Repository.CWD.toPath()).toString(), this.fileName).toString();
    }

    private String setFileName(Path path) {
        return path.getFileName().toString();
    }

    public byte[] getContent() {
        return content;
    }

    public String getFileName() {
        return fileName;
    }

    public String getHash() {
        return hash;
    }

    public String getFilePath() {
        return filePath;
    }
}
