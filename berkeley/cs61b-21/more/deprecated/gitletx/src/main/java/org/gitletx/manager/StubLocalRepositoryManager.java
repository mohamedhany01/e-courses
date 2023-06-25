package org.gitletx.manager;

import org.gitletx.objects.blob.IBlob;
import org.gitletx.objects.commit.ICommit;
import org.gitletx.objects.commit.StubCommit;
import org.gitletx.objects.tree.ITree;

import java.nio.file.Path;

public class StubLocalRepositoryManager implements ILocalRepositoryManager {
    @Override
    public ICommit commitRootCommit(IBlob blob, ITree tree, ICommit commit) {
        return new StubCommit();
    }

    @Override
    public Path getGitletx() {
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
