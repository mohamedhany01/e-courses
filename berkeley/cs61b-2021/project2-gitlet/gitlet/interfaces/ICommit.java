package gitlet.interfaces;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface ICommit extends Serializable {
    String getMessage();

    String getDate();

    String getAuthorName();

    String getAuthorEmail();

    String getTree();

    String getParent();

    String getHash();
}
