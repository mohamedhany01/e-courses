import com.gitlet.global.Global;
import com.gitlet.utilities.IO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class TestGitlet {
    //    @BeforeAll
//    public static void cleanupWorkspace() {
//        System.out.println("Pre Cleaning...");
//        System.out.println("Is " + com.gitlet.global.Global.REPO_NAME + " exists: " + IO.isPathExists(com.gitlet.global.Global.REPO_PATH));
//        System.out.print("Is " + com.gitlet.global.Global.REPO_PATH + " cleaned: " + IO.deletePath(com.gitlet.global.Global.REPO_PATH));
//        System.out.println("\n================================");
//
//        System.out.println("Creating the Repo...");
//        Path repoPath = IO.createPath(com.gitlet.global.Global.REPO_PATH, 'D');
//        Assertions.assertEquals(com.gitlet.global.Global.REPO_NAME, repoPath.getFileName().toString());
//        System.out.println(com.gitlet.global.Global.REPO_NAME + " is created: " + IO.isPathExists(com.gitlet.global.Global.REPO_PATH));
//
//        System.out.println("\n================================");
//    }

    @AfterAll
    public static void deleteRepo() {
        System.out.println("Post Cleaning...");
        System.out.println("Is " + Global.REPO_NAME + " exists: " + IO.isPathExists(Global.REPO_PATH));
        System.out.print("Is " + Global.REPO_PATH + " cleaned: " + IO.deletePath(Global.REPO_PATH));
    }

    @Test
    public void createARepo() {
        Path repoPath = IO.createPath(Global.REPO_PATH, 'D');
        Assertions.assertTrue(IO.isPathExists(Global.REPO_PATH));
        Assertions.assertNull(IO.createPath(Global.REPO_PATH, 'D'));
        Assertions.assertTrue(IO.deletePath(repoPath));
    }
}
