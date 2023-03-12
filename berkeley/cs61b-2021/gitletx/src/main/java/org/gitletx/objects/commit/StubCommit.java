package org.gitletx.objects.commit;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class StubCommit implements ICommit {
    @Override
    public String getMessage() {
        return "message";
    }

    @Override
    public LocalDateTime getDate() {
        LocalDateTime zeroDate = Instant.ofEpochSecond(0).atZone(ZoneId.of("UTC")).toLocalDateTime();
        return zeroDate;
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
