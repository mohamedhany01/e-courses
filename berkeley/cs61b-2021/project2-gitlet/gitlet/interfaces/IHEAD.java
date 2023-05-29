package gitlet.interfaces;

import java.util.HashMap;

public interface IHEAD {
    String updateHEAD(String branchName);

    String getHEAD();

    HashMap<String, String> getCommitFiles();

    String getActiveBranchHash();
    String getActiveBranchName();
}
