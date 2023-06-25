package org.gitletx.manager;

import org.gitletx.objects.blob.IBlob;
import org.gitletx.objects.commit.ICommit;
import org.gitletx.objects.tree.ITree;

import java.nio.file.Path;

public interface ILocalRepositoryManager {
    ICommit commitRootCommit(IBlob blob, ITree tree, ICommit commit);

    Path getGitletx();

    Path getINDEX();

    Path getOBJECTS();

    Path getHEAD();

    void showStatus();

    Path verifyFile(String fileName);
}
