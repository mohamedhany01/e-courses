package gitlet.fakes;

import gitlet.interfaces.IGitletPathsWrapper;

import java.nio.file.Path;

public class FakeGitletPathsWrapper implements IGitletPathsWrapper {
    public Path fakePath = null;

    @Override
    public Path getWorkingDirectory() {
        return fakePath;
    }

    @Override
    public Path getHead() {
        return fakePath;
    }

    @Override
    public Path getGitlet() {
        return fakePath;
    }

    @Override
    public Path getObjects() {
        return fakePath;
    }

    @Override
    public Path getIndex() {
        return fakePath;
    }

    @Override
    public Path getRefs() {
        return fakePath;
    }
}
