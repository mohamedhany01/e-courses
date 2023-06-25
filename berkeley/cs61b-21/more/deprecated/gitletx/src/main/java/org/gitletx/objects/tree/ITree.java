package org.gitletx.objects.tree;

import java.io.Serializable;

public interface ITree extends Serializable {
    String getType();

    String[] getContent();

    String getHash();
}
