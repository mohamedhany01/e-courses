package gitlet.fakes;

import gitlet.interfaces.IBlob;
import gitlet.interfaces.ICommit;
import gitlet.interfaces.IRepository;
import gitlet.interfaces.ITree;

import java.util.LinkedList;
import java.util.List;

public class FakeRepository implements IRepository {
    boolean branchStatus = false;

    @Override
    public ICommit commitObjects(ICommit commit, ITree tree, List<? extends IBlob> blobs) {
        return commit;
    }

    @Override
    public String createBranch(String name, String commitHash) {
        return null;
    }

    @Override
    public boolean removeBranch(String name) {
        return branchStatus;
    }

    @Override
    public String updateBranch(String name, String commitHash) {
        return null;
    }

    @Override
    public List<String> getAllBranches() {
        return new LinkedList<>() {
        };
    }
}
