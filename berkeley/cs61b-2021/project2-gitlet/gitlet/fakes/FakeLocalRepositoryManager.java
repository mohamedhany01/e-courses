package gitlet.fakes;

import gitlet.interfaces.IBlob;
import gitlet.interfaces.ICommit;
import gitlet.interfaces.ILocalRepositoryManager;
import gitlet.interfaces.ITree;

import java.nio.file.Path;

public class FakeLocalRepositoryManager implements ILocalRepositoryManager {
    @Override
    public ICommit commitRootCommit(IBlob blob, ITree tree, ICommit commit) {
        return new FakeCommit();
    }

    @Override
    public Path getgitlet() {
        return Path.of(System.getProperty("user.dir"));
    }

    @Override
    public Path getINDEX() {
        return Path.of(System.getProperty("user.dir"));
    }

    @Override
    public Path getOBJECTS() {
        return Path.of(System.getProperty("user.dir"));
    }

    @Override
    public Path getHEAD() {
        return Path.of(System.getProperty("user.dir"));
    }

    @Override
    public void showStatus() {

    }

    @Override
    public Path verifyFile(String fileName) {
        return null;
    }

}
