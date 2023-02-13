package com.gitlet.utilities;
import com.gitlet.global.Global;
import java.io.IOException;
import java.io.File;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.LineNumberReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IO {
    public final static Path joinPathToWorkingDirectory(Path main, String extend) {
        return Paths.get(String.valueOf(main), extend);
    }

    public final static Path buildNewPath(String newPath) {
        return joinPathToWorkingDirectory(Global.CURRENT_WORKING_DIRECTORY, newPath);
    }

    public final static Path buildNewPath(Path main, String... paths) {
        return Paths.get(main.toString(), paths);
    }

    public final static boolean isPathExists(Path path) {
        return Files.exists(path);
    }

    public final static Path createPath(Path path, char type) {
        if (!Files.exists(path)) {
            try {
                if (type == 'F' || type == 'f') {
                    return Files.createFile(path);
                } else if (type == 'D' || type == 'd') {
                    return Files.createDirectory(path);
                }
                throw new RuntimeException(type + " is undefined");
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return null;
    }

    public final static boolean deletePath(Path path) {

        // If file
        try {
            if (Files.isRegularFile(path)) {
                return Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        // If directory
        File[] files = path.toFile().listFiles();
        if (files != null) {
            try {
                for (File f : files) {
                    Files.deleteIfExists(f.toPath());
                }
                return Files.deleteIfExists(path);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return false;
    }

    public final static int writeStringsToFileUTF8(Path path, String... lines) {
        int linesCount = 0;
        if (path != null) {
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(path.toFile()), StandardCharsets.UTF_8)) {
                if (lines instanceof String[]) {
                    for (String line : lines) {
                        writer.write(line + "\n");
                        linesCount++;
                    }
                    return linesCount;
                }
                throw new RuntimeException("These lines aren't String");
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return linesCount;
    }

    public final static int readStringsFromFile(Path path) {
        if (path != null) {
            try (LineNumberReader reader = new LineNumberReader(new FileReader(path.toFile()))) {
                while (reader.readLine() != null) ;
                return reader.getLineNumber();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return 0;
    }
}
