import gitlet.*;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;

public class RepositoryTest {
    @Test

    public void Repository_commitObjects_returnCommit() {
        IUtilitiesWrapper stubUtilities = new StubUtilitiesWrapper();
        FakeGitletPathsWrapper stubGitletPaths = new FakeGitletPathsWrapper();
        stubGitletPaths.fakePath = Path.of(System.getProperty("user.dir"));
        IBlob stubBlob = new StubBlob();
        ITree stubTree = new StubTree();
        ICommit stubCommit = new StubCommit();
        Repository repository = Repository.create(stubUtilities, stubGitletPaths);

        ICommit expect = repository.commitObjects(stubCommit, stubTree, stubBlob);

        Assert.assertNotNull(expect);
        Assert.assertTrue(expect instanceof ICommit);
    }
}
