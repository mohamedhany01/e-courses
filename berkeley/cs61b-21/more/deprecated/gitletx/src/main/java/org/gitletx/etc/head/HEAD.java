package org.gitletx.etc.head;

import org.gitletx.utilities.GitletxPaths;
import org.gitletx.utilities.IUtilitiesWrapper;

import java.nio.file.Files;

public class HEAD implements IHEAD {

    private final IUtilitiesWrapper utilities;

    public HEAD(IUtilitiesWrapper utilities) {
        this.utilities = utilities;
    }

    @Override
    public String updateHEAD(String newHash) {
        if (!Files.exists(GitletxPaths.HEAD)) {
            throw new RuntimeException("HEAD file not found");
        }
        this.utilities.writeContents(GitletxPaths.HEAD.toFile(), newHash);

        return getHEAD();
    }

    @Override
    public String getHEAD() {
        return this.utilities.readContentsAsString(GitletxPaths.HEAD.toFile());
    }
}
