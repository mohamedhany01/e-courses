import gitlet.Repository;
import gitlet.fakes.*;
import gitlet.interfaces.ICommit;
import gitlet.interfaces.ITree;
import gitlet.interfaces.IUtilitiesWrapper;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RepositoryTest {
    @Test

    public void Repository_commitObjects_returnCommit() {
        IUtilitiesWrapper stubUtilities = new FakeUtilitiesWrapper();
        FakeGitletPathsWrapper stubGitletPaths = new FakeGitletPathsWrapper();
        stubGitletPaths.fakePath = Path.of(System.getProperty("user.dir"));
        List<FakeBlob> stubBlob = new ArrayList<>();
        ITree stubTree = new FakeTree();
        ICommit stubCommit = new FakeCommit();
        Repository repository = Repository.create(stubUtilities, stubGitletPaths);

        ICommit expect = repository.commitObjects(stubCommit, stubTree, stubBlob);

        Assert.assertNotNull(expect);
        Assert.assertTrue(expect instanceof ICommit);
    }

    @Test
    public void Repository_createBranch_returnBranchHash() {
        FakeUtilitiesWrapper stubUtilities = new FakeUtilitiesWrapper();
        stubUtilities.fakeContent = "fake content";
        FakeGitletPathsWrapper stubGitletPaths = new FakeGitletPathsWrapper();
        stubGitletPaths.fakePath = Path.of(System.getProperty("user.dir"));
        Repository repository = Repository.create(stubUtilities, stubGitletPaths);

        // TODO: failure due to singleton pattern when run only, replace Repository to be normal
        String expected = repository.createBranch("foo", "anything");

        Assert.assertNotNull(expected);
        Assert.assertEquals("fake\\head\\value", expected);
    }

    @Test
    public void Repository_updateBranch_returnBranchName() {
        FakeUtilitiesWrapper stubUtilities = new FakeUtilitiesWrapper();
        stubUtilities.fakeContent = "fake content";
        FakeGitletPathsWrapper stubGitletPaths = new FakeGitletPathsWrapper();
        stubGitletPaths.fakePath = Path.of(System.getProperty("user.dir"));
        Repository repository = Repository.create(stubUtilities, stubGitletPaths);

        // TODO: failure due to singleton pattern when run only, replace Repository to be normal
        String expected = repository.updateBranch("foo", "anything");

        Assert.assertNotNull(expected);
        Assert.assertEquals("fake\\head\\value", expected);
    }
}
