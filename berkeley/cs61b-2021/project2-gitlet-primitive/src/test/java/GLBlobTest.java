import com.gitletx.objects.blob.GLBlob;
import com.gitletx.utilities.IGLPaths;
import com.gitletx.utilities.StubGLPaths;
import com.gitletx.utilities.hashing.IHashing;
import com.gitletx.utilities.hashing.StubHashing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class GLBlobTest {

    @Test
    public void GLBlob_getTreeType_returnStringType() {
        IHashing hashing = new StubHashing();
        IGLPaths paths = new StubGLPaths();
        GLBlob blob = new GLBlob(hashing, paths);
        String expected = "blob";

        String actual = blob.getType();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void GLBlob_setHash_returnStringType() {
        IHashing hashing = new StubHashing();
        IGLPaths paths = new StubGLPaths();
        GLBlob blob = new GLBlob(hashing, paths);

        String actual = blob.setHashWrapper("foo");

        Assertions.assertNotNull(actual);
    }

    @Test
    public void GLBlob_setFilePath_returnStringType() {
        IHashing hashing = new StubHashing();
        IGLPaths paths = new StubGLPaths();
        String currentPath = Path.of(System.getProperty("user.dir")).toString();
        GLBlob blob = new GLBlob(Path.of(currentPath), hashing);

        String actual = blob.setFilePathWrapper(currentPath);

//        System.out.println(actual);

        Assertions.assertNotNull(actual);
    }

}
