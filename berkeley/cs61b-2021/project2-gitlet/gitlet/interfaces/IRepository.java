package gitlet.interfaces;

import java.util.List;

public interface IRepository {
    ICommit commitObjects(ICommit commit, ITree tree, List<? extends IBlob> blobs);

    String createBranch(String name, String commitHash);

    boolean removeBranch(String name);

    boolean hasBranch(String branchName);

    String updateBranch(String name, String commitHash);

    String getBranchHash(String name);

    List<String> getAllBranches();
}
