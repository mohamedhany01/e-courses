package capers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import static capers.Utils.join;

/**
 * Represents a dog that can be serialized.
 * 
 * @author Mohamed Hany
 */
public class Dog implements Serializable {

    static final File CWD = new File(System.getProperty("user.dir"));

    /** Folder that dogs live in. */
    static final File DOG_FOLDER = join(CWD, ".capers", "dogs");

    /** Age of dog. */
    private int age;
    /** Breed of dog. */
    private String breed;
    /** Name of dog. */
    private String name;

    /**
     * Creates a dog object with the specified parameters.
     * 
     * @param name  Name of dog
     * @param breed Breed of dog
     * @param age   Age of dog
     */
    public Dog(String name, String breed, int age) {
        this.age = age;
        this.breed = breed;
        this.name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        File dogFile = new File(DOG_FOLDER, name);
        Dog tempDog = null;
        if (dogFile.exists()) {
            try (ObjectInputStream objOut = new ObjectInputStream(new FileInputStream(dogFile))) {
                
                // Read the whole dog info to temp reference variable
                tempDog = (Dog) objOut.readObject();

                // Delete the old file and higher up the age value
                tempDog.writeDogToItsUniqueFile(dogFile.getAbsolutePath(),
                        new Dog(tempDog.name, tempDog.breed, tempDog.age + 1), true);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        return tempDog;
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        age += 1;
        System.out.println(toString());
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     */
    public void saveDog() {
        System.out.println(this.toString());
        File dogFile = new File(DOG_FOLDER, name);

        if (!dogFile.exists()) {
            // Create a new file with same as dog name "unique name"
            try {
                if (dogFile.createNewFile()) {
                    writeDogToItsUniqueFile(dogFile.getAbsolutePath(), this, false);
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    /* 
     * Write a dog object to a file
     */
    private void writeDogToItsUniqueFile(String createdDogFilePath, Dog dog, boolean deleteFile) {
        File file = new File(createdDogFilePath);

        if (deleteFile) {
            file.delete();
        }

        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file))) {

            // Write the whole dog object to the file
            objOut.writeObject(dog);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
                name, breed, age);
    }

}
