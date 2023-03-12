import com.gitletx.utilities.GLPaths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GLPathsTest {

    Path WORKING_DIRECTORY = Path.of(System.getProperty("user.dir"));
    Path GITLET_DIRECTORY = Paths.get(String.valueOf(WORKING_DIRECTORY), ".getlet");

    @Test
    public void GLPathsTest_isGitletPathValid_returnTrue() {
        String gitletExpected = Paths.get(String.valueOf(WORKING_DIRECTORY), ".getlet").toString();
        String gitletActual = GLPaths.GITLET_DIRECTORY.toString();

        Assertions.assertEquals(gitletExpected, gitletActual);
    }

    @Test
    public void GLPathsTest_isGitletPathInvalid_returnFalse() {
        String gitletExpected = Paths.get(String.valueOf(WORKING_DIRECTORY), ".get1et").toString();
        String gitletActual = GLPaths.GITLET_DIRECTORY.toString();

        Assertions.assertNotEquals(gitletExpected, gitletActual);
    }

    @Test
    public void GLPathsTest_isIndexPathValid_returnTrue() {
        String indexExpected = Paths.get(String.valueOf(GITLET_DIRECTORY), "index").toString();
        String indexActual = GLPaths.INDEX.toString();

        Assertions.assertEquals(indexExpected, indexActual);
    }

    @Test
    public void GLPathsTest_isIndexPathInvalid_returnFalse() {
        String indexExpected = Paths.get(String.valueOf(GITLET_DIRECTORY), "1ndex").toString();
        String indexActual = GLPaths.INDEX.toString();

        Assertions.assertNotEquals(indexExpected, indexActual);
    }

    @Test
    public void GLPathsTest_isObjectsPathValid_returnTrue() {
        String objectsExpected = Paths.get(String.valueOf(GITLET_DIRECTORY), "objects").toString();
        String objectsActual = GLPaths.OBJECTS.toString();

        Assertions.assertEquals(objectsExpected, objectsActual);
    }

    @Test
    public void GLPathsTest_isObjectsPathInvalid_returnFalse() {
        String objectsExpected = Paths.get(String.valueOf(GITLET_DIRECTORY), "0bjects").toString();
        String objectsActual = GLPaths.OBJECTS.toString();

        Assertions.assertNotEquals(objectsExpected, objectsActual);
    }

    @Test
    public void GLPathsTest_isHeadPathValid_returnTrue() {
        String headExpected = Paths.get(String.valueOf(GITLET_DIRECTORY), "HEAD").toString();
        String headActual = GLPaths.HEAD.toString();

        Assertions.assertEquals(headExpected, headActual);
    }

    @Test
    public void GLPathsTest_isHeadPathInvalid_returnFalse() {
        String headExpected = Paths.get(String.valueOf(GITLET_DIRECTORY), "HEaD").toString();
        String headActual = GLPaths.HEAD.toString();

        Assertions.assertNotEquals(headExpected, headActual);
    }
}
