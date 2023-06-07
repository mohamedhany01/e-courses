package gitlet;

import gitlet.interfaces.IHEAD;
import gitlet.objects.Blob;
import gitlet.objects.Commit;
import gitlet.objects.Tree;
import gitlet.trees.Repository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.regex.Pattern;

public class HEAD implements IHEAD {
    public HEAD() {

    }

    public static String getBranchName() {
        return Utils.readContentsAsString(new File(Repository.HEAD_POINTER));
    }

    public static String getBranchHash() {
        return Utils.readContentsAsString(
                new File(
                        Repository.BRANCHES,
                        HEAD.getBranchName())
        );
    }

    public static void move(String branch) {
        Utils.writeContents(new File(Repository.HEAD_POINTER), branch);
    }

    @Override
    public String updateHEAD(String branchName) {
        if (!Files.exists(Path.of(Repository.HEAD_POINTER))) {
            throw new RuntimeException("HEAD file not found");
        }

        String symbolicName = Path.of("refs", "heads", branchName).toString();

        Utils.writeContents(new File(Repository.HEAD_POINTER), symbolicName);

        return getHEAD();
    }

    @Override
    public String getHEAD() {
        return Utils.readContentsAsString(new File(Repository.HEAD_POINTER));
    }

    @Override
    public HashMap<String, String> getCommitFiles() {
        String commitHash = getActiveBranchHash();
        HashMap<String, String> commitFiles = new HashMap<>();
        Commit currentCommit = Commit.getCommit(commitHash);
        Tree currentTree = Tree.getTree(currentCommit.getTree());

        currentTree.getBlobs().forEach((Object blobHash) -> {
            Blob blob = Blob.getBlob((String) blobHash);
            commitFiles.put(blob.getFileName(), blob.getHash());
        });
        return commitFiles;
    }

    @Override
    public String getActiveBranchHash() {
        String activeBranchName = getActiveBranchName();
        String branchPath = Path.of(Repository.BRANCHES, activeBranchName).toString();
        return Utils.readContentsAsString(new File(branchPath));
    }

    @Override
    public String getActiveBranchName() {
        // https://stackoverflow.com/a/28630124
        String pathSeparatorPattern = Pattern.quote(File.separator);
        String[] activeBranch = getHEAD().split(pathSeparatorPattern);
        return activeBranch[2];
    }
}
