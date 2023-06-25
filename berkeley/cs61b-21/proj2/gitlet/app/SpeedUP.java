package gitlet.app;

import gitlet.Utils;
import gitlet.trees.Repository;
import gitlet.trees.WorkingArea;

import java.io.File;
import java.nio.file.Path;

public class SpeedUP {
    public static void step1() {
        String[] add = new String[]{
                "a.txt",
                "b.txt",
                "c.txt",
                "d.txt",
                "e.txt",
                "x.txt"
        };

        File file = null;
        String TEMP_TESTING = Path.of(System.getProperty("user.dir"), "TEMP_TEST").toString();

        for (int i = 0; i < add.length; i++) {
            if (App.mode.equals("dev")) {
                Utils.writeContents(new File(Path.of(TEMP_TESTING, add[i]).toString()), add[i]);
            } else {
                Utils.writeContents(new File(Path.of(Repository.GITLET, add[i]).toString()), add[i]);
            }
        }

        for (int i = 0; i < add.length; i++) {
            App.add(add[i]);
        }

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < add.length; i++) {
            message.append(add[i] + ",");
        }
        message.append(" Added [Parent commit|Split point]");
        App.status();
        App.commit(message.toString());

        App.branch("foo");
        String[][] foo = new String[][]{{"branch", "foo"}};
        App.status();
        App.checkout(foo[0]);
        App.status();
        App.log();
        System.out.println("\n========================== STEP 1 is finished ==========================\n");

    }

    public static void main(String[] args) {
        App.init();
        new WorkingArea().clear();

        // Split point
        step1();

        // foo
        step2();

        // Master
        step3();

        // Merge
        App.merge("master");

    }

    private static void step3() {
        StringBuilder message = new StringBuilder();

        String[] add = new String[]{
                "a.txt",
                "b.txt",
                "e.txt",
                "x.txt",
                "f.txt",
        };

        File file = null;
        String TEMP_TESTING = Path.of(System.getProperty("user.dir"), "TEMP_TEST").toString();
        for (int i = 0; i < add.length; i++) {
            if (App.mode.equals("dev")) {
                file = new File(Path.of(TEMP_TESTING, add[i]).toString());
                if (add[i] == "a.txt" || add[i] == "f.txt") {
                    Utils.writeContents(file, "!" + add[i]);
                    message.append(add[i] + " [U], ");
                } else if (add[i] == "x.txt") {
                    Utils.writeContents(file, "!!" + add[i]);
                    message.append(add[i] + " [U], ");
                } else {
                    Utils.writeContents(file, add[i]);
                    message.append(add[i] + " [A], ");
                }
            } else {
                file = new File(Path.of(Repository.GITLET, add[i]).toString());
                if (add[i] == "a.txt" || add[i] == "f.txt") {
                    Utils.writeContents(file, "!" + add[i]);
                    message.append(add[i] + " [U], ");
                } else if (add[i] == "x.txt") {
                    Utils.writeContents(file, "!!" + add[i]);
                    message.append(add[i] + " [U], ");
                } else {
                    Utils.writeContents(file, add[i]);
                    message.append(add[i] + " [A], ");
                }
            }


        }

        for (int i = 0; i < add.length; i++) {
            App.add(add[i]);
        }

        String[] rm = new String[]{
                "c.txt",
                "d.txt",
        };

        for (int i = 0; i < rm.length; i++) {
            App.rm(rm[i]);
            message.append(add[i] + " [R], ");
        }
        App.status();

        App.commit(message.toString());

        App.log();

        String[][] foo = new String[][]{{"branch", "foo"}};
        App.status();
        App.checkout(foo[0]);
        App.status();
        App.log();
        System.out.println("\n========================== STEP 3 is finished ==========================\n");
    }

    private static void step2() {
        StringBuilder message = new StringBuilder();

        String[] add = new String[]{
                "a.txt",
                "b.txt",
                "d.txt",
                "x.txt",
                "g.txt",
        };

        File file = null;
        String TEMP_TESTING = Path.of(System.getProperty("user.dir"), "TEMP_TEST").toString();
        for (int i = 0; i < add.length; i++) {
            if (App.mode.equals("dev")) {
                file = new File(Path.of(TEMP_TESTING, add[i]).toString());
            } else {
                file = new File(Path.of(Repository.GITLET, add[i]).toString());
            }

            if (add[i] == "b.txt" || add[i] == "x.txt") {
                Utils.writeContents(file, "!" + add[i]);
                message.append(add[i] + " [U], ");
            } else {
                Utils.writeContents(file, add[i]);
                message.append(add[i] + " [A], ");
            }

        }

        for (int i = 0; i < add.length; i++) {
            App.add(add[i]);
        }

        String[] rm = new String[]{
                "c.txt",
                "e.txt",
        };

        for (int i = 0; i < rm.length; i++) {
            App.rm(rm[i]);
            message.append(add[i] + " [R], ");
        }
        App.status();

        App.commit(message.toString());

        App.log();

        String[][] master = new String[][]{{"branch", "master"}};
        App.status();
        App.checkout(master[0]);
        App.status();
        App.log();
        System.out.println("\n========================== STEP 2 is finished ==========================\n");

    }
}
