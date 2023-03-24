import gitlet.*;
import gitlet.fakes.FakeBlob;
import gitlet.fakes.FakeUtilitiesWrapper;
import gitlet.interfaces.IBlob;
import gitlet.interfaces.IUtilitiesWrapper;
import org.junit.Assert;
import org.junit.Test;

public class TreeTest {

    @Test
    public void Tree_getType_returnString() {
        IUtilitiesWrapper utilities = new FakeUtilitiesWrapper();
        IBlob[] blobs = new FakeBlob[]{new FakeBlob(), new FakeBlob(), new FakeBlob()};
        Tree tree = new Tree(utilities, blobs);
        String expected = "tree";

        String actual = tree.getType();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Tree_getContent_returnArrayOfString() {
        IUtilitiesWrapper utilities = new FakeUtilitiesWrapper();
        IBlob[] blobs = new FakeBlob[]{new FakeBlob(), new FakeBlob(), new FakeBlob()};
        Tree tree = new Tree(utilities, blobs);

        String[] expected = new String[]{"hash", "hash", "hash"};

        String[] actual = tree.getContent();

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void Tree_getHash_returnString() {
        IUtilitiesWrapper utilities = new FakeUtilitiesWrapper();
        IBlob[] blobs = new FakeBlob[]{new FakeBlob(), new FakeBlob(), new FakeBlob()};
        Tree tree = new Tree(utilities, blobs);
        String expected = "sha1";

        String actual = tree.getHash();

        Assert.assertEquals(expected, actual);
    }

}
