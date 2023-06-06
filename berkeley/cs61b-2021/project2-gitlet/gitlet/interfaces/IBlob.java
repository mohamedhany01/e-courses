package gitlet.interfaces;

import java.io.Serializable;

public interface IBlob extends Serializable {
    byte[] getFileContent();

    String getHash();

    String getFileName();

    String getFilePath();
}
