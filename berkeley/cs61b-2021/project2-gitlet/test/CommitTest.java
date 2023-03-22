import gitlet.Commit;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class CommitTest {
    @Test
    public void Commit_getMessage_returnString() {
        LocalDateTime dateTime = LocalDateTime.now();
        Commit commit = new Commit(
                "foo",
                dateTime,
                "Foo",
                "foo@foo.com",
                "tree hash",
                "parent",
                "hash"
        );
        String expected = "foo";

        String actual = commit.getMessage();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Commit_getDate_returnString() {
        LocalDateTime dateTime = LocalDateTime.now();
        Commit commit = new Commit(
                "foo",
                dateTime,
                "Foo",
                "foo@foo.com",
                "tree hash",
                "parent",
                "hash"
        );
        String expected = dateTime.toString();

        String actual = commit.getDate().toString();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Commit_getAuthorName_returnString() {
        LocalDateTime dateTime = LocalDateTime.now();
        Commit commit = new Commit(
                "foo",
                dateTime,
                "Foo",
                "foo@foo.com",
                "tree hash",
                "parent",
                "hash"
        );
        String expected = "Foo";

        String actual = commit.getAuthorName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Commit_getAuthorEmail_returnString() {
        LocalDateTime dateTime = LocalDateTime.now();
        Commit commit = new Commit(
                "foo",
                dateTime,
                "Foo",
                "foo@foo.com",
                "tree hash",
                "parent",
                "hash"
        );
        String expected = "foo@foo.com";

        String actual = commit.getAuthorEmail();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Commit_getTree_returnString() {
        LocalDateTime dateTime = LocalDateTime.now();
        Commit commit = new Commit(
                "foo",
                dateTime,
                "Foo",
                "foo@foo.com",
                "tree hash",
                "parent",
                "hash"
        );
        String expected = "tree hash";

        String actual = commit.getTree();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Commit_getParent_returnString() {
        LocalDateTime dateTime = LocalDateTime.now();
        Commit commit = new Commit(
                "foo",
                dateTime,
                "Foo",
                "foo@foo.com",
                "tree hash",
                "parent",
                "hash"
        );
        String expected = "parent";

        String actual = commit.getParent();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Commit_getHash_returnString() {
        LocalDateTime dateTime = LocalDateTime.now();
        Commit commit = new Commit(
                "foo",
                dateTime,
                "Foo",
                "foo@foo.com",
                "tree hash",
                "parent",
                "hash"
        );
        String expected = "hash";

        String actual = commit.getHash();

        Assert.assertEquals(expected, actual);
    }
}
