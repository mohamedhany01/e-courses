import gitlet.*;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.Path;

public class HEADTest {
    @Test
    public void HEAD_updateHEAD_returnString() {
        IUtilitiesWrapper utilities = new StubUtilitiesWrapper();
        FakeGitletPathsWrapper gitletPaths = new FakeGitletPathsWrapper();
        gitletPaths.fakePath = Path.of(System.getProperty("user.dir"));
        HEAD head = new HEAD(utilities, gitletPaths);
        String expected = "fake content";

        String actual = head.updateHEAD("foo");

        Assert.assertEquals(expected, actual);
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Test
    public void HEAD_updateHEADWhilePathInvalid_throwException() {
        IUtilitiesWrapper utilities = new StubUtilitiesWrapper();
        FakeGitletPathsWrapper gitletPaths = new FakeGitletPathsWrapper();
        gitletPaths.fakePath = Path.of(System.getProperty("user.dir"), "fake");
        HEAD head = new HEAD(utilities, gitletPaths);
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("HEAD file not found");

        String actual = head.updateHEAD("foo");

    }

    @Test
    public void HEAD_getHEAD_returnString() {
        IUtilitiesWrapper utilities = new StubUtilitiesWrapper();
        FakeGitletPathsWrapper gitletPaths = new FakeGitletPathsWrapper();
        gitletPaths.fakePath = Path.of(System.getProperty("user.dir"));
        HEAD head = new HEAD(utilities, gitletPaths);
        String expected = "fake content";

        String actual = head.getHEAD();

        Assert.assertEquals(expected, actual);
    }

}
