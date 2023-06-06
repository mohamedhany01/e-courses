package gitlet.fakes;

import gitlet.interfaces.IBlob;

public class FakeBlob implements IBlob {

    @Override
    public byte[] getFileContent() {
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
