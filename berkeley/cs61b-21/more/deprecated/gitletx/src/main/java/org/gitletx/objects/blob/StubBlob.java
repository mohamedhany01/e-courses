package org.gitletx.objects.blob;

public class StubBlob implements IBlob {

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
