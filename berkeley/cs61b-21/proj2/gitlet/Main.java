package gitlet;

import gitlet.app.App;
import gitlet.trees.Repository;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author Mohamed Hany
 * <p>
 * GITLET 2021: https://sp21.datastructur.es/materials/proj/proj2/proj2
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */

    /*
        - If a user doesn’t input any arguments, print the message Please enter a command. and exit. [DONE]

        - If a user inputs a command that doesn’t exist, print the message No command with that name exists.
            and exit. [DONE]

        - If a user inputs a command with the wrong number or format of operands,
            print the message Incorrect operands. and exit. [DONE]

        - If a user inputs a command that requires being in an initialized Gitlet working directory
            (i.e., one containing a .gitlet subdirectory), but is not in such a directory,
             print the message Not in an initialized Gitlet directory. [DONE]
    */
    // DONE
    public static void main(String[] args) {
        if (args.length == 0) {
            Utils.exit("Please enter a command.");
        }

        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                App.init();
                break;
            case "status":
                if (!Repository.gitletDirectoryExist(Repository.GITLET)) {
                    Utils.exit("Not in an initialized Gitlet directory.");
                }
                App.status();
                break;
            case "add":
                if (!Repository.gitletDirectoryExist(Repository.GITLET)) {
                    Utils.exit("Not in an initialized Gitlet directory.");
                }
                if (!Utils.isValidArgsCount(args, 2)) {
                    Utils.exit("Incorrect operands.");
                }
                App.add(args[1]);
                break;
            case "commit":
                if (!Repository.gitletDirectoryExist(Repository.GITLET)) {
                    Utils.exit("Not in an initialized Gitlet directory.");
                }
                if (args.length < 2) {
                    Utils.exit("Please enter a commit message.");
                }
                App.commit(args[1]);
                break;
            case "log":
                if (!Repository.gitletDirectoryExist(Repository.OBJECTS)) {
                    Utils.exit("Not in an initialized Gitlet directory.");
                }
                App.log();
                break;
            case "global-log":
                if (!Repository.gitletDirectoryExist(Repository.OBJECTS)) {
                    Utils.exit("Not in an initialized Gitlet directory.");
                }
                App.globalLog();
                break;
            case "rm":
                if (!Repository.gitletDirectoryExist(Repository.GITLET)) {
                    Utils.exit("Not in an initialized Gitlet directory.");
                }
                if (!Utils.isValidArgsCount(args, 2)) {
                    Utils.exit("Incorrect operands.");
                }
                App.rm(args[1]);
                break;
            case "find":
                if (!Repository.gitletDirectoryExist(Repository.OBJECTS)) {
                    Utils.exit("Not in an initialized Gitlet directory.");
                }
                if (!Utils.isValidArgsCount(args, 2)) {
                    Utils.exit("Incorrect operands.");
                }
                App.find(args);
                break;
            case "checkout":
                if (!Repository.gitletDirectoryExist(Repository.OBJECTS)) {
                    Utils.exit("Not in an initialized Gitlet directory.");
                }
                if (!Utils.isValidArgsCount(args, 1)) {
                    Utils.exit("Incorrect operands.");
                }
                App.checkout(args);
                break;
            case "branch":
                if (!Repository.gitletDirectoryExist(Repository.BRANCHES)) {
                    Utils.exit("Not in an initialized Gitlet directory.");
                }
                if (!Utils.isValidArgsCount(args, 2)) {
                    Utils.exit("Incorrect operands.");
                }
                App.branch(args[1]);
                break;
            case "rm-branch":
                if (!Repository.gitletDirectoryExist(Repository.BRANCHES)) {
                    Utils.exit("Not in an initialized Gitlet directory.");
                }
                if (!Utils.isValidArgsCount(args, 2)) {
                    Utils.exit("Incorrect operands.");
                }
                App.removeBranch(args);
                break;
            case "reset":
                if (!Repository.gitletDirectoryExist(Repository.GITLET)) {
                    Utils.exit("Not in an initialized Gitlet directory.");
                }
                if (!Utils.isValidArgsCount(args, 2)) {
                    Utils.exit("Incorrect operands.");
                }
                App.reset(args);
                break;
            case "merge":
                if (!Repository.gitletDirectoryExist(Repository.GITLET)) {
                    Utils.exit("Not in an initialized Gitlet directory.");
                }
                if (!Utils.isValidArgsCount(args, 2)) {
                    Utils.exit("Incorrect operands.");
                }
                App.merge(args[1]);
            default:
                Utils.exit("No command with that name exists.");
        }
    }
}
