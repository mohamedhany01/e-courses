package gitlet.interfaces;

import java.io.Serializable;
import java.util.List;

public interface ITree extends Serializable {
    String getType();

    List<Object> getContent();

    String getHash();
}
