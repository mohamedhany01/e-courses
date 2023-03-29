import gitlet.Tree;
import gitlet.fakes.FakeBlob;
import gitlet.fakes.FakeUtilitiesWrapper;
import gitlet.interfaces.IBlob;
import gitlet.interfaces.IUtilitiesWrapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class TreeTest {

    @Test
    public void Tree_getType_returnString() {
        IUtilitiesWrapper utilities = new FakeUtilitiesWrapper();
        IBlob[] blobs = new FakeBlob[]{new FakeBlob(), new FakeBlob(), new FakeBlob()};
        Tree tree = new Tree();
        for (IBlob blob : blobs) {
            tree.setBlob(blob.getHash());
        }
        tree.calculateContentHash(utilities);
        String expected = "tree";

        String actual = tree.getType();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Tree_getContent_returnArrayOfString() {
        IUtilitiesWrapper utilities = new FakeUtilitiesWrapper();
        IBlob[] blobs = new FakeBlob[]{new FakeBlob(), new FakeBlob(), new FakeBlob()};
        Tree tree = new Tree();
        for (IBlob blob : blobs) {
            tree.setBlob(blob.getHash());
        }
        tree.setBlob("hash");
        tree.setBlob("hash");
        tree.setBlob("hash");
        tree.calculateContentHash(utilities);
        List<Object> expected = new LinkedList<>();
        expected.add("hash");
        expected.add("hash");
        expected.add("hash");

        List<Object> actual = tree.getContent();

        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    public void Tree_getHash_returnString() {
        IUtilitiesWrapper utilities = new FakeUtilitiesWrapper();
        IBlob[] blobs = new FakeBlob[]{new FakeBlob(), new FakeBlob(), new FakeBlob()};
        Tree tree = new Tree();
        for (IBlob blob : blobs) {
            tree.setBlob(blob.getHash());
        }
        String expected = "sha1";
        tree.calculateContentHash(utilities);

        String actual = tree.getHash();

        Assert.assertEquals(expected, actual);
    }

}
