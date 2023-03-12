package com.gitletx.utilities;

import com.gitletx.utilities.IGLPaths;

public class StubGLPaths implements IGLPaths {
    @Override
    public String getCWD() {
        return "cwd";
    }
}
