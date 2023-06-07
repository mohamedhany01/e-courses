package gitlet.app;

import gitlet.objects.Commit;

public class AppUtils {
    public static void formatLog(Commit commit) {
        System.out.println("===");
        System.out.println("commit " + commit.getHash());
        System.out.println("Date: " + commit.getDate());
        System.out.println(commit.getMessage() + "\n");
    }
}
