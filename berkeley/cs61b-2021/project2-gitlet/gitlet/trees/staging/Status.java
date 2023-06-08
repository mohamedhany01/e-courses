package gitlet.trees.staging;

import java.io.Serializable;

public enum Status implements Serializable {
    NOT_STAGED, STAGED, COMMITTED
}
