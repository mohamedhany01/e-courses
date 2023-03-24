package gitlet.fakes;

import gitlet.interfaces.IBlob;
import gitlet.interfaces.ICommit;
import gitlet.interfaces.IRepository;
import gitlet.interfaces.ITree;

public class FakeRepository implements IRepository {
    @Override
    public ICommit commitObjects(ICommit commit, ITree tree, IBlob... blobs) {
        return commit;
    }
}
