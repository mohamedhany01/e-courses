package gitlet.fakes;

import gitlet.interfaces.IHEAD;

public class FakeHEAD implements IHEAD {
    @Override
    public String updateHEAD(String newHash) {
        return null;
    }

    @Override
    public String getHEAD() {
        return null;
    }
}
