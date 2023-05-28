package gitlet;

import java.nio.file.Path;

public class GitletPaths {
    public final static Path WORKING_DIRECTORY = Path.of(System.getProperty("user.dir"));
    public final static Path gitlet = Path.of(WORKING_DIRECTORY.toString(), ".gitlet");
    public final static Path INDEX = Path.of(gitlet.toString(), "index");
    public final static Path OBJECTS = Path.of(gitlet.toString(), "objects");
    public final static Path HEAD = Path.of(gitlet.toString(), "HEAD");
    public final static Path BRANCHES = Path.of(gitlet.toString(), "refs", "heads");
}
