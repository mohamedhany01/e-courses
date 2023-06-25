package gitlet.fakes;

import gitlet.interfaces.IHEAD;

public class FakeHEAD implements IHEAD {
    String fakeBranchName = null;
    String fakeBranchHash = null;

    @Override
    public String getHEAD() {
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
