package com.gitletx.utilities;

public class Validation {
    public final static void validateArguments(String[] args, String command, int validCount) {
        if (args.length != validCount) {
            throw new RuntimeException("Wrong arguments count of " + "'" + command + "'" + " command, valid number is " + validCount);
        }
    }
}
