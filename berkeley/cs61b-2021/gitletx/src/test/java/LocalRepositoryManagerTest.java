import org.gitletx.etc.head.IHEAD;
import org.gitletx.etc.head.StubHEAD;
import org.gitletx.manager.LocalRepositoryManager;
import org.gitletx.objects.blob.IBlob;
import org.gitletx.objects.blob.StubBlob;
import org.gitletx.objects.commit.ICommit;
import org.gitletx.objects.commit.StubCommit;
import org.gitletx.objects.tree.ITree;
import org.gitletx.objects.tree.StubTree;
import org.gitletx.trees.staging.IStagingArea;
import org.gitletx.trees.staging.StagingArea;
import org.gitletx.utilities.IUtilitiesWrapper;
import org.gitletx.utilities.StubUtilitiesWrapper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalRepositoryManagerTest {

    static LocalRepositoryManager manager = null;

    @AfterClass
    public static void clean() {
        Path repoPath = Path.of(LocalRepositoryManager.WORKING_DIRECTORY.toString(), ".gitletx");

        File[] files = repoPath.toFile().listFiles();
        if (files != null) {
            try {
                for (File f : files) {
                    Files.deleteIfExists(f.toPath());
                }
                Files.deleteIfExists(repoPath);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    @Before
    public void setup() {
        IUtilitiesWrapper utilities = new StubUtilitiesWrapper();
        IStagingArea stagingArea = new StagingArea(utilities);
        IHEAD head = new StubHEAD();

        manager = LocalRepositoryManager.create(utilities, stagingArea, head);
    }

    @Test
    public void LocalRepositoryManager_isRepoExists_returnTrue() {
        boolean actual = LocalRepositoryManager.isGitletxExists();

        Assert.assertTrue(actual);
    }

    @Test
    public void LocalRepositoryManager_getGitletx_returnPath() {
        Path expected = Path.of(LocalRepositoryManager.WORKING_DIRECTORY.toString(), ".gitletx");

        Path actual = manager.getGitletx();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void LocalRepositoryManager_getIndex_returnPath() {
        Path expected = Path.of(LocalRepositoryManager.WORKING_DIRECTORY.toString(), ".gitletx", "INDEX");

        Path actual = manager.getINDEX();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void LocalRepositoryManager_getObjects_returnPath() {
        Path expected = Path.of(LocalRepositoryManager.WORKING_DIRECTORY.toString(), ".gitletx", "objects");

        Path actual = manager.getOBJECTS();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void LocalRepositoryManager_getHead_returnPath() {
        Path expected = Path.of(LocalRepositoryManager.WORKING_DIRECTORY.toString(), ".gitletx", "HEAD");

        Path actual = manager.getHEAD();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void LocalRepositoryManager_storeRootCommit_returnNotNull() {
        IBlob blob = new StubBlob();
        ITree tree = new StubTree();
        ICommit commit = new StubCommit();

        ICommit actual = manager.commitRootCommit(blob, tree, commit);

        Assert.assertNotNull(actual);
    }
}
