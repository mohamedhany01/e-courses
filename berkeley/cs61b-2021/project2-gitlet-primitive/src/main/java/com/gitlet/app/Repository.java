package com.gitlet.app;

import com.gitlet.global.Global;
import com.gitlet.utilities.IO;

import java.nio.file.Path;
import java.util.List;

public class Repository {
    public static Path initializeNewRepository() {
        Path repoFullPath = IO.buildNewPath(Global.REPO_PATH);
        Path createdPath = IO.createPath(repoFullPath, 'D');
        if ( createdPath == null) {
            throw new RuntimeException("ERROR: Creating a new repo wasn't successful.");
        }

        System.out.println("New repo created: " + createdPath);
        return createdPath;
    }

    public List<Path> getIgnoredFiles() {
        return IO.readPathsFromFile(Global.REPO_PATH.getParent(), Global.GITLET_IGNORE);
    }
}
