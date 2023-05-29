package gitlet;

import gitlet.interfaces.IGitletPathsWrapper;
import gitlet.interfaces.IHEAD;
import gitlet.interfaces.IUtilitiesWrapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class HEAD implements IHEAD {

    private final IUtilitiesWrapper utilities;
    private final IGitletPathsWrapper gitletPaths;

    public HEAD(IUtilitiesWrapper utilities, IGitletPathsWrapper gitletPaths) {
        this.utilities = utilities;
        this.gitletPaths = gitletPaths;
    }

    @Override
    public String updateHEAD(String branchName) {
        if (!Files.exists(gitletPaths.getHead())) {
            throw new RuntimeException("HEAD file not found");
        }

        String symbolicName = Path.of("refs", "heads", branchName).toString();

        this.utilities.writeContents(gitletPaths.getHead().toFile(), symbolicName);

        return getHEAD();
    }

    @Override
    public String getHEAD() {
        return this.utilities.readContentsAsString(gitletPaths.getHead().toFile());
    }

    @Override
    public HashMap<String, String> getCommitFiles() {
        String commitHash = getActiveBranchHash();
        Commit currentCommit = Commit.getCommit(commitHash, utilities);
        Tree currentTree = Tree.getTree(currentCommit.getTree(), utilities);
        HashMap<String, String> commitFiles = new HashMap<>();

        currentTree.getContent().forEach((Object blobHash) -> {
            Blob blob = Blob.getBlob((String) blobHash, utilities);
            commitFiles.put(blob.getFileName(), blob.getHash());
        });
        return commitFiles;
    }

    @Override
    public String getActiveBranchHash() {
       String activeBranchName = getActiveBranchName();
        String branchPath = Path.of(gitletPaths.getRefs().toString(), activeBranchName).toString();
        return utilities.readContentsAsString(new File(branchPath));
    }

    @Override
    public String getActiveBranchName() {
        // https://stackoverflow.com/a/28630124
        String pathSeparatorPattern = Pattern.quote(File.separator);
        String [] activeBranch = getHEAD().split(pathSeparatorPattern);
        return activeBranch[2];
    }
}
