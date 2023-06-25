package com.gitletx.global;
import com.gitletx.utilities.io.IO;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Global {

    public final static String REPO_NAME = ".gitlet";
    public final static String GITLET_IGNORE = ".gitletignore";

    public final static Path CURRENT_WORKING_DIRECTORY = Paths.get(System.getProperty("user.dir"));
    public final static Path REPO_PATH = IO.joinPathToWorkingDirectory(CURRENT_WORKING_DIRECTORY, REPO_NAME);
    public static final Path USER_INFO = IO.joinPathToWorkingDirectory(REPO_PATH, "config");
    public static final Path GITLET_IGNORE_PATH = IO.joinPathToWorkingDirectory(REPO_PATH, GITLET_IGNORE);
}
