package com.gitletx.objects.blob;

public interface IGLBlob {
    public byte[] getContent();
    public String getFileName();
    public String getHash();
    public String getFilePath();
}
