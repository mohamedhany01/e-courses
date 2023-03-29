package gitlet.interfaces;

import java.util.List;

public interface IRepository {
    ICommit commitObjects(ICommit commit, ITree tree, List< ? extends IBlob > blobs);
}
