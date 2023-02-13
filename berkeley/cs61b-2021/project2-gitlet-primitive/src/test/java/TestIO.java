import com.gitlet.global.Global;
import com.gitlet.utilities.IO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestIO {

    @Test
    public void buildNewPath() {
        String pathName = "foo";
        Path newPath = IO.buildNewPath(pathName);
        Path expectedPath = Paths.get(Global.CURRENT_WORKING_DIRECTORY.toString(), pathName);
        Assertions.assertEquals(expectedPath, newPath);
    }

    @Test
    public void createAFile() {
        Path newPath = IO.createPath(IO.buildNewPath("simple_file.txt"), 'F');
        Assertions.assertTrue(IO.isPathExists(newPath));
        Assertions.assertNull(IO.createPath(newPath, 'F'));
        Assertions.assertTrue(IO.deletePath(newPath));
    }

    @Test
    public void createADirectory() {
        Path newPath = IO.createPath(IO.buildNewPath("simple_directory"), 'D');
        Assertions.assertTrue(IO.isPathExists(newPath));
        Assertions.assertNull(IO.createPath(newPath, 'D'));
        Assertions.assertTrue(IO.deletePath(newPath));
    }

    @Test
    public void write3linesToAFile() {
        Path tempDir = IO.buildNewPath("temp_dir");
        Path file = IO.buildNewPath(tempDir, "temp_file.txt");

        IO.createPath(tempDir, 'D');

        int wittenLinesCount = IO.writeStringsToFileUTF8(IO.createPath(file, 'F'), "foo", "baz", "buz");

        Assertions.assertTrue(IO.isPathExists(tempDir));

        int actualLinesCount = IO.readStringsFromFile(file);

        Assertions.assertEquals(wittenLinesCount, 3);
        Assertions.assertEquals(actualLinesCount, 3);
        Assertions.assertEquals(actualLinesCount, wittenLinesCount);

        Assertions.assertTrue(IO.deletePath(tempDir));

    }
}
