package gitlet.fakes;

import gitlet.interfaces.IHEAD;

import java.io.File;
import java.util.HashMap;

public class FakeHEAD implements IHEAD {
    String fakeBranchName = null;
    String fakeBranchHash = null;

    @Override
    public String updateHEAD(String branchName) {
        return null;
    }

    @Override
    public String getHEAD() {
        return null;
    }

    @Override
    public HashMap<String, String> getCommitFiles() {
        return null;
    }

    @Override
    public String getActiveBranchHash() {
        return fakeBranchHash;
    }

    @Override
    public String getActiveBranchName() {
        return fakeBranchName;
    }
}
