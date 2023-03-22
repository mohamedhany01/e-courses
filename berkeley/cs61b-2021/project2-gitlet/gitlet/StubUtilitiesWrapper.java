package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class StubUtilitiesWrapper implements IUtilitiesWrapper {
    @Override
    public String sha1(Object... objects) {
        return "sha1";
    }

    @Override
    public void writeObject(File file, Serializable obj) {

    }

    @Override
    public <T extends Serializable> T readObject(File file, Class<T> expectedClass) {
        return null;
    }

    @Override
    public void writeContents(File file, Object... contents) {

    }

    @Override
    public String readContentsAsString(File file) {
        return "fake content";
    }

    @Override
    public List<String> plainFilenamesIn(File dir) {
        return null;
    }

    @Override
    public byte[] readContents(File file) {
        return new byte[0];
    }
}
