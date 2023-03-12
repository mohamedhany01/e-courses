package com.gitletx.utilities;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GLPaths {
    public static final Path WORKING_DIRECTORY = Path.of(System.getProperty("user.dir"));
    public static final Path GITLET_DIRECTORY = Paths.get(String.valueOf(WORKING_DIRECTORY), ".getlet");

    // Staging/Indexing area
    public static final Path INDEX = Paths.get(String.valueOf(GITLET_DIRECTORY), "index");

    // Repository area
    public static final Path OBJECTS = Paths.get(String.valueOf(GITLET_DIRECTORY), "objects");

    // HEAD pointer
    public static final Path HEAD = Paths.get(String.valueOf(GITLET_DIRECTORY), "HEAD");


}
