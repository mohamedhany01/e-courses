package gitlet.interfaces;

import java.io.Serializable;

public interface ICommit extends Serializable {
    String getMessage();

    void setMessage(String message);

    String getDate();

    void setDate(String date);

    String getAuthorName();

    String getAuthorEmail();

    String getTree();

    void setTree(String tree);

    String getParent();

    void setParent(String parent);

    String getHash();

    void setHash(String hash);

    String getMergeMessage();

    void setMergeMessage(String mergeMessage);
}
