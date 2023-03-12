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
                Repository.initializeRepository();
                break;
            case "status":
                Repository.repoStatus();
                break;
            case "add":
                Repository.repoStage(args);
                break;
            case "commit":
                Repository.repoCommit(args);
                break;
            case "log":
                Repository.repoLog();
                break;
            case "rm":
                Repository.repoUnstage(args);
                break;
            case "find":
                Repository.repoFind(args);
                break;
            case "checkout":
                Repository.repoCheckout(args);
                break;
            case "debug":
                System.out.println(Repository.loadStagingArea());
                break;
            // TODO: FILL THE REST IN
        }
    }
}
