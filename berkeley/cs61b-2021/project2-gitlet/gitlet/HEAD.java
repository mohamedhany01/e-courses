package gitlet;

import gitlet.interfaces.IGitletPathsWrapper;
import gitlet.interfaces.IHEAD;
import gitlet.interfaces.IUtilitiesWrapper;

import java.nio.file.Files;
import java.util.HashMap;

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

    @Override
    public HashMap<String, String> getCommitFiles() {
        String commitHash = getHEAD();
        Commit currentCommit = Commit.getCommit(commitHash, utilities);
        Tree currentTree = Tree.getTree(currentCommit.getTree(), utilities);
        HashMap<String, String> commitFiles = new HashMap<>();

        currentTree.getContent().forEach((Object blobHash) -> {
            Blob blob = Blob.getBlob((String) blobHash, utilities);
            commitFiles.put(blob.getFileName(), blob.getHash());
        });
        return commitFiles;
    }
}
