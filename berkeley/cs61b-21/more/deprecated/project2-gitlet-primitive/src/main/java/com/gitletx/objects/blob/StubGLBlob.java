package com.gitletx.objects.blob;

public class StubGLBlob implements IGLBlob{
    @Override
    public byte[] getContent() {
        return new byte[0];
    }

    @Override
    public String getFileName() {
        return null;
    }

    @Override
    public String getHash() {
        return null;
    }

    @Override
    public String getFilePath() {
        return null;
    }
}
