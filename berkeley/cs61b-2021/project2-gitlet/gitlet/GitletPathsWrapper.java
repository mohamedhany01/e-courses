package gitlet;

import gitlet.interfaces.IGitletPathsWrapper;

import java.nio.file.Path;

public class GitletPathsWrapper implements IGitletPathsWrapper {
    @Override
    public Path getWorkingDirectory() {
        return Path.of(WorkingArea.WD);
    }

    @Override
    public Path getHead() {
        return Path.of(Repository.HEAD_POINTER);
    }

    @Override
    public Path getGitlet() {
        return Path.of(Repository.GITLET);
    }

    @Override
    public Path getObjects() {
        return Path.of(Repository.OBJECTS);
    }

    @Override
    public Path getIndex() {
        return Path.of(Repository.INDEX);
    }

    @Override
    public Path getRefs() {
        return Path.of(Repository.BRANCHES);
    }
}
