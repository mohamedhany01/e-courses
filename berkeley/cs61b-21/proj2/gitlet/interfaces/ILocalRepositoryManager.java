package gitlet.interfaces;

import java.nio.file.Path;

public interface ILocalRepositoryManager {
    ICommit commitRootCommit(IBlob blob, ITree tree, ICommit commit);

    Path getgitlet();

    Path getINDEX();

    Path getOBJECTS();

    Path getHEAD();

    Path getRefs();

    Path verifyFile(String fileName);
}
