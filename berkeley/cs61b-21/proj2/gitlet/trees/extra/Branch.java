package gitlet.trees.extra;

import gitlet.Utils;
import gitlet.trees.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Branch {
    public static void create(String name, String commitHash) {
        String branch = Repository.getBranchesPath(name).toString();
        Utils.writeContents(new File(branch), commitHash);
    }

    public static void remove(String name) {
        try {
            Files.deleteIfExists(Repository.getBranchesPath(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean exists(String branch) {
        return Files.exists(Repository.getBranchesPath(branch));
    }

    public static void update(String name, String commitHash) {
        Branch.create(name, commitHash);
    }

    public static void updateActive(String commitHash) {
        Branch.create(HEAD.getName(), commitHash);
    }

    public static String getHash(String name) {
        if (exists(name)) {
            return Utils.readContentsAsString(new File(Repository.getBranchesPath(name).toString()));
        }
        return null;
    }

    public static List<String> getAllBranches() {
        return Utils.plainFilenamesIn(Repository.BRANCHES);
    }
}
