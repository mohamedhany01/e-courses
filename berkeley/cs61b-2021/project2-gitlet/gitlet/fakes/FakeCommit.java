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
    public String getDate() {
        LocalDateTime zeroDate = Instant.ofEpochSecond(0).atZone(ZoneId.of("UTC")).toLocalDateTime();
        return zeroDate.toString();
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
    public String getParent() {
        return "parent";
    }

    @Override
    public String getHash() {
        return "hash";
    }
}
