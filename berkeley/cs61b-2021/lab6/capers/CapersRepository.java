package capers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import static capers.Utils.join;

/**
 * A repository for Capers
 * 
 * @author Mohamed Hany
 *         The structure of a Capers Repository is as follows:
 *
 *         .capers/ -- top level folder for all persistent data in your lab12
 *         folder
 *         - dogs/ -- folder containing all of the persistent data for dogs
 *         - story -- file containing the current story
 *
 *         change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = join(CWD, ".capers");
    // function in Utils

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     * - dogs/ -- folder containing all of the persistent data for dogs
     * - story -- file containing the current story
     */
    public static void setupPersistence() {
        File rootDataDir = new File(".capers");

        // Clean files
        // new File(CAPERS_FOLDER + "/dogs").delete();
        // new File(CAPERS_FOLDER + "/story").delete();
        // rootDataDir.delete();

        if (!rootDataDir.exists()) {
            if (rootDataDir.mkdir()) {
                File dogsDir = new File(CAPERS_FOLDER, "dogs");
                if (!dogsDir.exists()) {
                    dogsDir.mkdir();
                }
            }
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * 
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        try (FileOutputStream outSource = new FileOutputStream(new File(CAPERS_FOLDER, "story"), true);
                BufferedOutputStream bufOut = new BufferedOutputStream(outSource);) {

            byte[] storyBytes = text.getBytes();
            bufOut.write(storyBytes);
            bufOut.write('\n');
        } catch (Exception e) {
            System.err.println(e);
        }

        try (
                BufferedReader bufIn = new BufferedReader(new FileReader(join(CAPERS_FOLDER, "story")));) {
            String storyLine = bufIn.readLine();
            while (true) {
                if (storyLine == null) {
                    break;
                }
                System.out.println(storyLine);

                storyLine = bufIn.readLine();
            }

        } catch (Exception e) {
            System.err.println(e);

        }
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        Dog newDog = new Dog(name, breed, age);
        newDog.saveDog();
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * 
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        Dog storedDog = Dog.fromFile(name);
        storedDog.haveBirthday();
    }
}
