package org.gitletx.objects.blob;

import java.io.Serializable;

public interface IBlob extends Serializable {
    String getType();

    byte[] getContent();

    String getHash();

    String getFileName();

    String getFilePath();
}
