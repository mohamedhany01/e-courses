package com.gitletx.objects.blob;

import com.gitletx.utilities.IGLPaths;
import com.gitletx.utilities.hashing.IHashing;

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
    private String filePath;
    private String hash;
    private final String type = "blob";
    private byte[] content;

    transient private IHashing hashing;
    transient private IGLPaths paths;

    public GLBlob(IHashing hashing, IGLPaths paths) {
        this.hashing = hashing;
        this.fileName = null;
        this.content = null;
        this.hash = null;
        this.filePath = null;
        this.hash = this.hashing.sha1("");
        this.paths = paths;
        this.filePath = this.paths.getCWD();
    }

    public GLBlob(Path filePath, IHashing hashing) {
        this.hashing = hashing;
        this.fileName = setFileName(filePath);
        this.hash = setHash(filePath.toString());
        this.filePath = setFilePath(filePath.toString());
    }

    public String getType() {
        return this.type;
    }

    private String setHash(String path) {
        Path realPath = Path.of(path);

        // Set content as well
//        this.content = Utils.readContents(realPath.toFile());
        this.content = null;

        return this.hashing.sha1(this.content);
    }

    public String setHashWrapper(String filePath) {
        return setHash(filePath);
    }

    private String setFilePath(String path) {
        Path realPath = Path.of(path);
        System.out.println(realPath);
        return Paths.get(realPath.relativize(Path.of(this.paths.getCWD())).toString(), this.fileName).toString();
    }

    public String setFilePathWrapper(String filePath) {
        return setFilePath(filePath);
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
