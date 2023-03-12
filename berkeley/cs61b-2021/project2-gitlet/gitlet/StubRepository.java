package gitlet;

public class StubRepository implements IRepository {
    @Override
    public ICommit commitObjects(ICommit commit, ITree tree, IBlob... blobs) {
        return commit;
    }
}
