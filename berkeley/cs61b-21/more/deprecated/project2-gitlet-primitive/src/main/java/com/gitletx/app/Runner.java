package com.gitletx.app;

import com.gitletx.app.Repository;
import com.gitletx.utilities.Validation;

public class Runner {
    public static void main(String[] args) {

        String command = args[1];
        switch (command) {
            case "init":
                Validation.validateArguments(args, "init", 4);
                Repository.initializeNewRepository();
            break;

            default:
                System.out.println("Nothing could be done!");
        }
    }
}
