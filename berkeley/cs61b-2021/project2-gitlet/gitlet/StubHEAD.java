package gitlet;

public class StubHEAD implements IHEAD {
    @Override
    public String updateHEAD(String newHash) {
        return null;
    }

    @Override
    public String getHEAD() {
        return null;
    }
}
