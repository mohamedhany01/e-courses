package gitlet.app;

import gitlet.objects.Commit;
import gitlet.trees.Repository;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AppUtils {
    public static void printFormatted(Commit commit) {
        System.out.println("===");
        System.out.println("commit " + commit.getHash());
        System.out.println("Date: " + commit.getDate());
        System.out.println(commit.getMessage() + "\n");
    }

    public static List<Path> listDirectoriesOfDirectory(Path dir) {
        try {
            return Files.list(dir).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
