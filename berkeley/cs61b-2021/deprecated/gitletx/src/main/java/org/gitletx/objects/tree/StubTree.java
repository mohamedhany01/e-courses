package org.gitletx.objects.tree;

public class StubTree implements ITree {
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
