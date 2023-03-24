package gitlet.fakes;

import gitlet.interfaces.IBlob;

public class FakeBlob implements IBlob {

    @Override
    public String getType() {
        return "blob";
    }

    @Override
    public byte[] getContent() {
        return new byte[]{};
    }

    @Override
    public String getHash() {
        return "hash";
    }

    @Override
    public String getFileName() {
        return "name";
    }

    @Override
    public String getFilePath() {
        return "path";
    }
}
