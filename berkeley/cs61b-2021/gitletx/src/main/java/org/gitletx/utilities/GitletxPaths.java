package org.gitletx.utilities;

import java.nio.file.Path;

public class GitletxPaths {
    public final static Path WORKING_DIRECTORY = Path.of(System.getProperty("user.dir"));
    public final static Path GITLETX = Path.of(WORKING_DIRECTORY.toString(), ".gitletx");
    public final static Path INDEX = Path.of(GITLETX.toString(), "index");
    public final static Path OBJECTS = Path.of(GITLETX.toString(), "objects");
    public final static Path HEAD = Path.of(GITLETX.toString(), "HEAD");
}
