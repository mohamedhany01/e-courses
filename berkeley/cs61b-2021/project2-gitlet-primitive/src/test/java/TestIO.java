import com.gitletx.global.Global;
import com.gitletx.utilities.io.IO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestIO {

    @Test
    public void joinPath_newPath_returnValidPath() {
        String newPathName = "foo";
        Path currentDirectory = Global.CURRENT_WORKING_DIRECTORY;
        Path newPath = IO.joinPathToWorkingDirectory(currentDirectory, newPathName);
        String expectPath = Paths.get(currentDirectory.toString(), newPathName).toString();

        Assertions.assertEquals(expectPath, newPath.toString());
    }

    @Test
    public void buildPath_newPathInCurrentDir_returnValidPath() {
        String newPathName = "foo";
        String newPath = IO.buildNewPath(newPathName).toString();
        String expectPath = Global.CURRENT_WORKING_DIRECTORY + File.separator + newPathName;

        Assertions.assertEquals(expectPath, newPath.toString());
    }

    @Test
    public void createFile_newFileInCurrentDirectory_returnNewFile() {
        String fileName = "simple_file.txt";
        Path newFilePath = IO.buildNewPath(fileName);
        Path createdFile = IO.createPath(newFilePath, 'f');

        Assertions.assertNotEquals(null, createdFile);
        Assertions.assertNull(IO.createPath(newFilePath, 'f'));
        Assertions.assertEquals(true, IO.isPathExists(createdFile));
        Assertions.assertTrue(IO.deletePath(createdFile));
    }

    @Test
    public void createDirectory_newDirectoryInCurrentDirectory_returnNewDirectory() {
        Path createdDirectory = IO.createPath(IO.buildNewPath("simple_directory"), 'D');

        Assertions.assertTrue(IO.isPathExists(createdDirectory));
        Assertions.assertNull(IO.createPath(createdDirectory, 'D'));
        Assertions.assertEquals(true, IO.isPathExists(createdDirectory));
        Assertions.assertTrue(IO.deletePath(createdDirectory));
    }

    @Test
    public void foo() {
        Assertions.assertNotEquals(null, IO.filterDirectory(Global.CURRENT_WORKING_DIRECTORY, Global.GITLET_IGNORE));
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
