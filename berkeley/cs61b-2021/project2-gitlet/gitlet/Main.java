package gitlet;

import java.util.Arrays;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author Mohamed Hany
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                App.init();
                break;
            case "status":
                App.status();
                break;
            case "add":
                App.stage(args);
                break;
            case "commit":
                App.commit(args);
                break;
            case "log":
                App.log();
                break;
            case "rm":
                App.unstage(args);
                break;
            case "find":
                App.find(args);
                break;
            case "checkout":
                App.checkout(args);
                break;
//            case "debug":
//                System.out.println(Repository.loadStagingArea());
//                break;
        }
    }
}
