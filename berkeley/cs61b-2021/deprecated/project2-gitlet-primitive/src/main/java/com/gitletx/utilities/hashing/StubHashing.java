package com.gitletx.utilities.hashing;

public class StubHashing implements IHashing{
    @Override
    public String sha1(Object... objects) {
        return "sha1";
    }
}
