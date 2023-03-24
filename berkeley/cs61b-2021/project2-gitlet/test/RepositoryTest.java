import gitlet.*;
import gitlet.fakes.*;
import gitlet.interfaces.IBlob;
import gitlet.interfaces.ICommit;
import gitlet.interfaces.ITree;
import gitlet.interfaces.IUtilitiesWrapper;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;

public class RepositoryTest {
    @Test

    public void Repository_commitObjects_returnCommit() {
        IUtilitiesWrapper stubUtilities = new FakeUtilitiesWrapper();
        FakeGitletPathsWrapper stubGitletPaths = new FakeGitletPathsWrapper();
        stubGitletPaths.fakePath = Path.of(System.getProperty("user.dir"));
        IBlob stubBlob = new FakeBlob();
        ITree stubTree = new FakeTree();
        ICommit stubCommit = new FakeCommit();
        Repository repository = Repository.create(stubUtilities, stubGitletPaths);

        ICommit expect = repository.commitObjects(stubCommit, stubTree, stubBlob);

        Assert.assertNotNull(expect);
        Assert.assertTrue(expect instanceof ICommit);
    }
}
