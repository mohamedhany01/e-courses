package gitlet;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Represents a gitlet blob object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author Mohamed Hany
 */
public class Blob implements Serializable {

    private final String fileName;
    private final String filePath;
    private final String hash;
    private final String type = "blob";

    private String content;

    public Blob() {
        this.fileName = null;
        this.content = null;
        this.hash = Utils.sha1("");
        this.filePath = Repository.CWD.toString();
    }

    public Blob(Path filePath) {
        this.fileName = setFileName(filePath);
        this.hash = setHash(filePath);
        this.filePath = setFilePath(filePath);
    }

    public String getType() {
        return this.type;
    }

    private String setHash(Path path) {
        byte[] fileContent = Utils.readContents(path.toFile());

        // Set content as well
        this.content = Arrays.toString(fileContent);
        return Utils.sha1(fileContent);
    }

    private String setFilePath(Path path) {
        return Paths.get(path.relativize(Repository.CWD.toPath()).toString(), this.fileName).toString();
    }

    public String getContent() {
        return content;
    }

    private String setFileName(Path path) {
        return path.getFileName().toString();
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
