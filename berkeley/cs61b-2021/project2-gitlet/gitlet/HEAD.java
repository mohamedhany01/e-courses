package gitlet;

import java.nio.file.Files;

public class HEAD implements IHEAD {

    private final IUtilitiesWrapper utilities;
    private final IGitletPathsWrapper gitletPaths;

    public HEAD(IUtilitiesWrapper utilities, IGitletPathsWrapper gitletPaths) {
        this.utilities = utilities;
        this.gitletPaths = gitletPaths;
    }

    @Override
    public String updateHEAD(String newHash) {
        if (!Files.exists(gitletPaths.getHead())) {
            throw new RuntimeException("HEAD file not found");
        }
        this.utilities.writeContents(gitletPaths.getHead().toFile(), newHash);

        return getHEAD();
    }

    @Override
    public String getHEAD() {
        return this.utilities.readContentsAsString(gitletPaths.getHead().toFile());
    }
}
