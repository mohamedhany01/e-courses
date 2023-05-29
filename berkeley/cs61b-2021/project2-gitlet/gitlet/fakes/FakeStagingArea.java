package gitlet.fakes;

import gitlet.interfaces.IBlob;
import gitlet.interfaces.IStagingArea;

import java.util.HashMap;

public class FakeStagingArea implements IStagingArea {
    @Override
    public void writeStagingArea() {

    }

    @Override
    public HashMap<String, String> loadStagingArea() {
        return null;
    }

    @Override
    public HashMap<String, String> updateStagingArea(HashMap<String, String> newStagingArea) {
        return null;
    }

    @Override
    public void getFilesStatus() {

    }

    @Override
    public boolean containsPair(String fileName, String hash) {
        return false;
    }

    @Override
    public void stage(IBlob blob) {

    }

    @Override
    public void stagManually(String key, String hash) {

    }

    @Override
    public void deleteEntry(String key) {

    }

    @Override
    public boolean hasFileName(String key) {
        return false;
    }

    @Override
    public boolean isStagingAreaInCleanState() {
        return true;
    }

    @Override
    public HashMap<String, String> getBlobsReadyToBeCommitted() {
        return new HashMap<>() {
        };
    }
}
