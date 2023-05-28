package gitlet;

import gitlet.interfaces.IGitletPathsWrapper;

import java.nio.file.Path;

public class GitletPathsWrapper implements IGitletPathsWrapper {
    @Override
    public Path getWorkingDirectory() {
        return GitletPaths.WORKING_DIRECTORY;
    }

    @Override
    public Path getHead() {
        return GitletPaths.HEAD;
    }

    @Override
    public Path getGitlet() {
        return GitletPaths.gitlet;
    }

    @Override
    public Path getObjects() {
        return GitletPaths.OBJECTS;
    }

    @Override
    public Path getIndex() {
        return GitletPaths.INDEX;
    }

    @Override
    public Path getRefs() {
        return GitletPaths.BRANCHES;
    }
}
