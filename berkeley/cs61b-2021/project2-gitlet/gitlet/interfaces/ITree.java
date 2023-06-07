package gitlet.interfaces;

import java.io.Serializable;
import java.util.List;

public interface ITree extends Serializable {
    List<Object> getBlobs();

    String getHash();
}
