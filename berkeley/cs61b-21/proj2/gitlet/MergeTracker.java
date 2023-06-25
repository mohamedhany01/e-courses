package gitlet;

import gitlet.trees.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MergeTracker {
    public final static Path MERGE_DATA = Path.of(Repository.GITLET, "MERGE_DATA");

    public static void writeEntry(MergeEntry entry) {
        Utils.writeObject(MERGE_DATA.toFile(), entry);
    }

    public static void removeEntry() {
        try {
            Files.deleteIfExists(MERGE_DATA);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MergeEntry loadEntry() {
        if (!hasMergeData()) {
            return null;
        }
        return Utils.readObject(MERGE_DATA.toFile(), MergeEntry.class);
    }

    public static boolean hasMergeData() {
        return Files.exists(MERGE_DATA);
    }
}
