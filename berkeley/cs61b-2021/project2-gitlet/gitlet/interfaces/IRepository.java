package gitlet.interfaces;

public interface IRepository {
    ICommit commitObjects(ICommit commit, ITree tree, IBlob... blobs);
}
