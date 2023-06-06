import gitlet.HEAD;
import gitlet.fakes.FakeGitletPathsWrapper;
import gitlet.fakes.FakeUtilitiesWrapper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.nio.file.Path;

public class HEADTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void HEAD_updateHEAD_returnString() {
        FakeUtilitiesWrapper utilities = new FakeUtilitiesWrapper();
        utilities.fakeContent = "fake content";
        FakeGitletPathsWrapper gitletPaths = new FakeGitletPathsWrapper();
        gitletPaths.fakePath = Path.of(System.getProperty("user.dir"));
        HEAD head = new HEAD();
        String expected = "refs\\heads\\foo";

        String actual = head.updateHEAD("foo");

        Assert.assertEquals(expected, actual);
    }

    @Ignore
    @Test
    public void HEAD_updateHEADWhilePathInvalid_throwException() {
        FakeUtilitiesWrapper utilities = new FakeUtilitiesWrapper();
        utilities.fakeContent = "fake content";
        FakeGitletPathsWrapper gitletPaths = new FakeGitletPathsWrapper();
        gitletPaths.fakePath = Path.of(System.getProperty("user.dir"), "fake");
        HEAD head = new HEAD();
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("HEAD file not found");

        String actual = head.updateHEAD("foo");

    }

    @Test
    public void HEAD_getHEAD_returnString() {
        FakeUtilitiesWrapper utilities = new FakeUtilitiesWrapper();
        utilities.fakeContent = "fake content";
        FakeGitletPathsWrapper gitletPaths = new FakeGitletPathsWrapper();
        gitletPaths.fakePath = Path.of(System.getProperty("user.dir"));
        HEAD head = new HEAD();
        String expected = "refs\\heads\\foo";

        String actual = head.getHEAD();

        Assert.assertEquals(expected, actual);
    }

    @Ignore
    @Test
    public void HEAD_getActiveBranchHash_returnString() {
        FakeUtilitiesWrapper utilities = new FakeUtilitiesWrapper();
        utilities.fakeContent = "fake" + File.separator + "head" + File.separator + "value";
        FakeGitletPathsWrapper gitletPaths = new FakeGitletPathsWrapper();
        gitletPaths.fakePath = Path.of(System.getProperty("user.dir"));
        HEAD head = new HEAD();
        String expected = "fake" + File.separator + "head" + File.separator + "value";

        String actual = head.getActiveBranchHash();

        Assert.assertEquals(expected, actual);
    }

}
