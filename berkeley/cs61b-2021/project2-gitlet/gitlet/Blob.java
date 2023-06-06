package gitlet;

import gitlet.interfaces.IBlob;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.Arrays;

public class Blob implements IBlob, Serializable {
    private final byte[] fileContent;
    private final String hash;
    private final String fileName;
    private final String filePath;

    public Blob(byte[] fileContent, String fileName, String filePath) {
        this.fileContent = fileContent;
        this.hash = Utils.sha1(this.fileContent);
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public static Blob getBlob(String hash) {
        String blob = Paths.get(Repository.OBJECTS, hash).toString();

        if (!Repository.isInRepository(hash, new GitletPathsWrapper())) {
            return null;
        }

        return Utils.readObject(new File(blob), Blob.class);
    }

    @Override
    public byte[] getFileContent() {
        return fileContent;
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
    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return "Blob{" +
                "fileContent=" + Arrays.toString(fileContent) +
                ", hash='" + hash + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
