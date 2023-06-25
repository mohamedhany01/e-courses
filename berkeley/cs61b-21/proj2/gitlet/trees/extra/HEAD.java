package gitlet.trees.extra;

import gitlet.Utils;
import gitlet.trees.Repository;

import java.io.File;

public class HEAD {

    public static String getName() {
        return Utils.readContentsAsString(new File(Repository.HEAD_POINTER));
    }

    public static String getHash() {
        return Utils.readContentsAsString(
                new File(Repository.getBranchesPath(HEAD.getName()).toString())
        );
    }

    public static void move(String branch) {
        Utils.writeContents(new File(Repository.HEAD_POINTER), branch);
    }

    public static boolean isPoint(String branch) {
        return HEAD.getName().equals(branch);
    }
}
