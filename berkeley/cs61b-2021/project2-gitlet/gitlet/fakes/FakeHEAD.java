package gitlet.fakes;

import gitlet.interfaces.IHEAD;

import java.util.HashMap;

public class FakeHEAD implements IHEAD {
    @Override
    public String updateHEAD(String newHash) {
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
}
