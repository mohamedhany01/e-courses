package gitlet;

import java.nio.file.Path;

public interface IGitletPathsWrapper {
    Path getWorkingDirectory();

    Path getHead();

    Path getGitlet();

    Path getObjects();

    Path getIndex();
}
