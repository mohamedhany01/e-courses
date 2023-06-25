import com.gitletx.objects.GLTree;
import com.gitletx.objects.blob.IGLBlob;
import com.gitletx.objects.blob.StubGLBlob;
import com.gitletx.utilities.hashing.IHashing;
import com.gitletx.utilities.hashing.StubHashing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GLTreeTest {
    @Test
    public void GLTree_getTreeType_returnStringType() {
        GLTree tree = new GLTree();
        String expected = "tree";

        String actual = tree.getType();

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void GLTree_getHashButNoContentNoHashing_returnNull() {
        GLTree tree = new GLTree();

        String actual = tree.getHash();

        Assertions.assertNull(actual);
    }
    @Test
    public void GLTree_getHash_returnString() {
        // Stubs
        Object [] blobs = new IGLBlob[]{new StubGLBlob(), new StubGLBlob()};
        IHashing hashing = new StubHashing();

        GLTree tree = new GLTree();
        tree.setContent(blobs);
        tree.setHash(hashing);

        // SUT
        String actual = tree.getHash();

        Assertions.assertNotNull(actual);
    }
    @Test
    public void GLTree_setHashButNoContent_returnException() {
        IHashing hashing = new StubHashing();
        GLTree tree = new GLTree();

        final Exception exception = Assertions.assertThrows(RuntimeException.class, () -> tree.setHash(hashing));

        Assertions.assertEquals("The content isn't initialized yet", exception.getMessage());
    }
    @Test
    public void GLTree_setHashButWithContent_returnString() {
        // Stubs
        Object [] blobs = new IGLBlob[]{new StubGLBlob(), new StubGLBlob()};
        IHashing hashing = new StubHashing();

        GLTree tree = new GLTree();
        tree.setContent(blobs);

        // SUT
        String result = tree.setHash(hashing);

        Assertions.assertNotNull(result);
    }
    @Test
    public void GLTree_setContent_returnCount() {
        Object [] blobs = new IGLBlob[]{new StubGLBlob(), new StubGLBlob()}; // Stubs
        GLTree tree = new GLTree();
        int expected = blobs.length;

        int actual = tree.setContent(blobs);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void GLTree_getContent_returnCount() {
        Object [] blobs = new IGLBlob[]{new StubGLBlob(), new StubGLBlob()}; // Stubs
        GLTree tree = new GLTree();
        tree.setContent(blobs);
        int expected = blobs.length;

        int actual = tree.getContent().length;

        Assertions.assertEquals(expected, actual);
    }
}
