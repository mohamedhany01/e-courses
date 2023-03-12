package org.gitletx.utilities;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public interface IUtilitiesWrapper {
    String sha1(Object... objects);

    void writeObject(File file, Serializable obj);

    <T extends Serializable> T readObject(File file, Class<T> expectedClass);

    void writeContents(File file, Object... contents);

    String readContentsAsString(File file);

    List<String> plainFilenamesIn(File dir);

    byte[] readContents(File file);
}
