import gitlet.objects.Tree;
import gitlet.Utils;
import gitlet.fakes.FakeBlob;
import gitlet.interfaces.IBlob;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class TreeTest {
    @Test
    public void Tree_getContent_returnArrayOfString() {
        IBlob[] blobs = new FakeBlob[]{new FakeBlob(), new FakeBlob(), new FakeBlob()};
        Tree tree = new Tree();
        for (IBlob blob : blobs) {
            tree.setBlob(blob.getHash());
        }
        tree.setBlob("hash");
        tree.setBlob("hash");
        tree.setBlob("hash");
        tree.calculateContentHash();
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
        List<Object> blobs = new LinkedList<>() {{
            add(new FakeBlob());
            add(new FakeBlob());
            add(new FakeBlob());
        }};
        List<Object> blobsSHA = new LinkedList<>();
        Tree tree = new Tree();
        for (Object blob : blobs) {
            String sha = ((FakeBlob) blob).getHash();
            tree.setBlob(sha);
            blobsSHA.add(sha);
        }
        String expected = Utils.sha1(blobsSHA);
        tree.calculateContentHash();

        String actual = tree.getHash();

        Assert.assertEquals(expected, actual);
    }

}
