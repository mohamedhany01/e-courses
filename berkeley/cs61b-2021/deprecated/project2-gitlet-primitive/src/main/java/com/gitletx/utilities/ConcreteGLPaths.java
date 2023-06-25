package com.gitletx.utilities;

import com.gitletx.utilities.GLPaths;
import com.gitletx.utilities.IGLPaths;

public class ConcreteGLPaths implements IGLPaths {
    @Override
    public String getCWD() {
        return GLPaths.WORKING_DIRECTORY.toString();
    }
}
