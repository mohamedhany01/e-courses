package gitlet.interfaces;

import java.io.Serializable;

public interface ICommit extends Serializable {
    String getMessage();

    String getDate();

    String getAuthorName();

    String getAuthorEmail();

    String getTree();

    String getParent();

    String getHash();
}
