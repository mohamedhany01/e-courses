package gitlet;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExtraUtils {

    /*
     * IO utilities
     * */
    public final static Path createPath(Path path) {
        if (!Files.isDirectory(path) && !Files.exists(path)) {
            try {
                return Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return null;
    }

    public final static int writeStringsToFileUTF8(Path path, String [] lines) {
        int linesCount = 0;
        if (path != null) {
            try(Writer writer = new OutputStreamWriter(new FileOutputStream(path.toFile()), Charset.forName("UTF-8"))) {
                for (String line: lines) {
                    writer.write(line+"\n");
                    linesCount++;
                }
                return linesCount;
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return linesCount;
    }

    public final static int readStringsFromFile(Path path) {
        if (path != null) {
            try(LineNumberReader reader = new LineNumberReader(new FileReader(path.toFile()))) {
                while (reader.readLine() != null) ;
                return reader.getLineNumber();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return 0;
    }

    public final static boolean deletePath(Path path) {
        File [] files = path.toFile().listFiles();
        if (files != null) {
            try {
                for(File f : files) {
                    Files.deleteIfExists(f.toPath());
                }
                return Files.deleteIfExists(path);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return false;
    }

    /*
    * Validation utilities
    * */
    static void validateNumArgs(String command, String [] args, int validCount) {
        if (args.length != validCount) {
            throw new RuntimeException(String.format("Invalid number of arguments for: %s.", command));
        }

    }
}