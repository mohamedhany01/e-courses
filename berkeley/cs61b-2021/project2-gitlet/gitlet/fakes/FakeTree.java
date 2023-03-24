package gitlet.fakes;

import gitlet.interfaces.ITree;

public class FakeTree implements ITree {
    @Override
    public String getType() {
        return "tree";
    }

    @Override
    public String[] getContent() {
        return new String[]{};
    }

    @Override
    public String getHash() {
        return "hash";
    }
}
