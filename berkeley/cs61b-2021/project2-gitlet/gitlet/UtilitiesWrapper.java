package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class UtilitiesWrapper implements IUtilitiesWrapper {
    @Override
    public String sha1(Object... objects) {
        return Utils.sha1(objects);
    }

    @Override
    public void writeObject(File file, Serializable obj) {
        Utils.writeObject(file, obj);
    }

    @Override
    public <T extends Serializable> T readObject(File file, Class<T> expectedClass) {
        return Utils.readObject(file, expectedClass);
    }

    @Override
    public void writeContents(File file, Object... contents) {
        Utils.writeContents(file, contents);
    }

    @Override
    public String readContentsAsString(File file) {
        return Utils.readContentsAsString(file);
    }

    @Override
    public List<String> plainFilenamesIn(File dir) {
        return Utils.plainFilenamesIn(dir);
    }

    @Override
    public byte[] readContents(File file) {
        return Utils.readContents(file);
    }


}
