package org.gitletx;

import java.util.Arrays;

public class Runner {
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