package gitlet.fakes;

import gitlet.interfaces.IBlob;
import gitlet.interfaces.ICommit;
import gitlet.interfaces.IRepository;
import gitlet.interfaces.ITree;

import java.util.List;

public class FakeRepository implements IRepository {
    @Override
    public ICommit commitObjects(ICommit commit, ITree tree, List<? extends IBlob> blobs) {
        return commit;
    }
}
