package org.gitletx.utilities;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class UtilitiesWrapper implements IUtilitiesWrapper {
    @Override
    public String sha1(Object... objects) {
        return Utilities.sha1(objects);
    }

    @Override
    public void writeObject(File file, Serializable obj) {
        Utilities.writeObject(file, obj);
    }

    @Override
    public <T extends Serializable> T readObject(File file, Class<T> expectedClass) {
        return Utilities.readObject(file, expectedClass);
    }

    @Override
    public void writeContents(File file, Object... contents) {
        Utilities.writeContents(file, contents);
    }

    @Override
    public String readContentsAsString(File file) {
        return Utilities.readContentsAsString(file);
    }

    @Override
    public List<String> plainFilenamesIn(File dir) {
        return Utilities.plainFilenamesIn(dir);
    }

    @Override
    public byte[] readContents(File file) {
        return Utilities.readContents(file);
    }


}
