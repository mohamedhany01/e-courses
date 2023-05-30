package gitlet.fakes;

import gitlet.interfaces.IUtilitiesWrapper;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FakeUtilitiesWrapper implements IUtilitiesWrapper {

    // TODO: to solve Repository_createBranch_returnBranchHash for now, until replace singleton in Repository class
    public String fakeContent = "fake" + File.separator + "head" + File.separator + "value";

    @Override
    public String sha1(Object... objects) {
        return "sha1";
    }

    @Override
    public String sha1(List<Object> vals) {
        return "sha1";
    }

    @Override
    public void writeObject(File file, Serializable obj) {

    }

    @Override
    public <T extends Serializable> T readObject(File file, Class<T> expectedClass) {
        return (T) new HashMap<>();
    }

    @Override
    public void writeContents(File file, Object... contents) {

    }

    @Override
    public String readContentsAsString(File file) {
        return fakeContent;
    }

    @Override
    public List<String> plainFilenamesIn(File dir) {
        return new ArrayList<>();
    }

    @Override
    public byte[] readContents(File file) {
        return new byte[0];
    }
}
