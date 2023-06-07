package gitlet.fakes;

import gitlet.interfaces.ICommit;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FakeCommit implements ICommit {
    @Override
    public String getMessage() {
        return "message";
    }

    @Override
    public void setMessage(String message) {

    }

    @Override
    public String getDate() {
        LocalDateTime zeroDate = Instant.ofEpochSecond(0).atZone(ZoneId.of("UTC")).toLocalDateTime();
        return zeroDate.toString();
    }

    @Override
    public void setDate(String date) {

    }

    @Override
    public String getAuthorName() {
        return "author";
    }

    @Override
    public String getAuthorEmail() {
        return "email";
    }

    @Override
    public String getTree() {
        return "treeHash";
    }

    @Override
    public void setTree(String tree) {

    }

    @Override
    public String getParent() {
        return "parent";
    }

    @Override
    public void setParent(String parent) {

    }

    @Override
    public String getHash() {
        return "hash";
    }

    @Override
    public void setHash(String hash) {

    }
}
