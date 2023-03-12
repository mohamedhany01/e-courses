package gitlet;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Blob implements IBlob, Serializable {
    private final static String type = "blob";
    private final byte[] content;
    private final String hash;
    private final String fileName;
    private final String filePath;

    public Blob(IUtilitiesWrapper utilities, byte[] content, String fileName, String filePath) {
        this.content = content;
        this.hash = utilities.sha1(content);
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public static Blob getBlob(String hash, IUtilitiesWrapper utilities) {
        Path blobFullPath = Paths.get(GitletPaths.OBJECTS.toString(), hash);

        if (!Files.exists(blobFullPath)) {
            throw new RuntimeException("Blob with " + hash + " not found");
        }

        Blob blob = utilities.readObject(blobFullPath.toFile(), Blob.class);

        return blob;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public byte[] getContent() {
        return content;
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
                "content=" + Arrays.toString(content) +
                ", hash='" + hash + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
