package org.gitletx.trees.repository;

import org.gitletx.objects.blob.IBlob;
import org.gitletx.objects.commit.ICommit;
import org.gitletx.objects.tree.ITree;

public interface IRepository {
    ICommit commitObjects(ICommit commit, ITree tree, IBlob... blobs);
}
