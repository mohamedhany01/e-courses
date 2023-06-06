package gitlet.fakes;

import gitlet.interfaces.ITree;

import java.util.LinkedList;
import java.util.List;

public class FakeTree implements ITree {
    @Override
    public List<Object> getContent() {
        return new LinkedList<>();
    }

    @Override
    public String getHash() {
        return "hash";
    }
}
