package org.gitletx.trees.staging;

import org.gitletx.objects.blob.IBlob;

import java.util.HashMap;
import java.util.List;

public class StubStagingArea implements IStagingArea {
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
    public void displayStagedFiles() {

    }

    @Override
    public void displayUntrackedFiles() {

    }

    @Override
    public void displayModifiedFiles() {

    }

    @Override
    public void stage(List<IBlob> blobs) {

    }

    @Override
    public HashMap<String, String> getBlobsReadyToBeCommitted() {
        return new HashMap<>() {
        };
    }
}
