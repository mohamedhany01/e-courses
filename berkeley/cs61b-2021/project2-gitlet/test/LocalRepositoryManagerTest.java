import gitlet.*;
import gitlet.fakes.*;
import gitlet.interfaces.*;
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
                System.out.println(e.toString());
            }
        }
    }

    @Before
    public void setup() {
        IUtilitiesWrapper utilities = new FakeUtilitiesWrapper();
        IGitletPathsWrapper gitletPaths = new GitletPathsWrapper();
        IStagingArea stagingArea = new StagingArea(utilities, gitletPaths);
        IHEAD head = new FakeHEAD();

        manager = LocalRepositoryManager.create(utilities, stagingArea, head);
    }

    @Test
    public void LocalRepositoryManager_isRepoExists_returnTrue() {
        boolean actual = LocalRepositoryManager.isgitletExists();

        Assert.assertTrue(actual);
    }

    @Test
    public void LocalRepositoryManager_getGitletx_returnPath() {
        Path expected = Path.of(LocalRepositoryManager.WORKING_DIRECTORY.toString(), ".gitlet");

        Path actual = manager.getgitlet();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void LocalRepositoryManager_getIndex_returnPath() {
        Path expected = Path.of(LocalRepositoryManager.WORKING_DIRECTORY.toString(), ".gitlet", "INDEX");

        Path actual = manager.getINDEX();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void LocalRepositoryManager_getObjects_returnPath() {
        Path expected = Path.of(LocalRepositoryManager.WORKING_DIRECTORY.toString(), ".gitlet", "objects");

        Path actual = manager.getOBJECTS();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void LocalRepositoryManager_getHead_returnPath() {
        Path expected = Path.of(LocalRepositoryManager.WORKING_DIRECTORY.toString(), ".gitlet", "HEAD");

        Path actual = manager.getHEAD();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void LocalRepositoryManager_storeRootCommit_returnNotNull() {
        IBlob blob = new FakeBlob();
        ITree tree = new FakeTree();
        ICommit commit = new FakeCommit();

        ICommit actual = manager.commitRootCommit(blob, tree, commit);

        Assert.assertNotNull(actual);
    }
}
