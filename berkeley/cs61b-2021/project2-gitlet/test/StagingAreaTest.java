import gitlet.StagingArea;
import gitlet.fakes.FakeGitletPathsWrapper;
import gitlet.fakes.FakeUtilitiesWrapper;
import gitlet.interfaces.IUtilitiesWrapper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.Path;
import java.util.HashMap;

public class StagingAreaTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Ignore
    @Test
    public void StagingArea_writeStagingAreaIndexInvalidPath_throwException() {
        IUtilitiesWrapper stubUtilities = new FakeUtilitiesWrapper();
        FakeGitletPathsWrapper stubGitletPaths = new FakeGitletPathsWrapper();
        stubGitletPaths.fakePath = Path.of(System.getProperty("user.dir"), "fake");
        StagingArea stagingArea = new StagingArea();

        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("index file not found");

        stagingArea.writeStagingArea();
    }

    @Ignore
    @Test
    public void StagingArea_writeStagingAreaWithStagingAreaIndexInvalidPath_throwException() {
        IUtilitiesWrapper stubUtilities = new FakeUtilitiesWrapper();
        FakeGitletPathsWrapper stubGitletPaths = new FakeGitletPathsWrapper();
        stubGitletPaths.fakePath = Path.of(System.getProperty("user.dir"), "fake");
        StagingArea stagingArea = new StagingArea();

        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("index file not found");

        stagingArea.writeStagingArea(new HashMap<>());
    }

    @Ignore
    @Test
    public void StagingArea_loadStagingAreaIndexInvalidPath_throwException() {
        IUtilitiesWrapper stubUtilities = new FakeUtilitiesWrapper();
        FakeGitletPathsWrapper stubGitletPaths = new FakeGitletPathsWrapper();
        stubGitletPaths.fakePath = Path.of(System.getProperty("user.dir"), "fake");
        StagingArea stagingArea = new StagingArea();

        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("index file not found");

        stagingArea.loadStagingArea();
    }

    @Ignore
    @Test
    public void StagingArea_loadStagingAreaIndexValidPath_returnHashMap() {
        FakeUtilitiesWrapper stubUtilities = new FakeUtilitiesWrapper();
        FakeGitletPathsWrapper stubGitletPaths = new FakeGitletPathsWrapper();
        stubGitletPaths.fakePath = Path.of(System.getProperty("user.dir"));
        StagingArea stagingArea = new StagingArea();

        HashMap<String, String> actual = stagingArea.loadStagingArea();

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual instanceof HashMap<String, String>);
        Assert.assertTrue(actual.isEmpty());
    }

    @Ignore
    @Test
    public void StagingArea_updateStagingAreaInvalidPath_throwException() {
        IUtilitiesWrapper stubUtilities = new FakeUtilitiesWrapper();
        FakeGitletPathsWrapper stubGitletPaths = new FakeGitletPathsWrapper();
        stubGitletPaths.fakePath = Path.of(System.getProperty("user.dir"), "fake");
        StagingArea stagingArea = new StagingArea();

        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("index file not found");

        stagingArea.updateStagingArea(new HashMap<>());
    }

    @Ignore
    @Test
    public void StagingArea_updateStagingAreaValidPath_returnHashMap() {
        IUtilitiesWrapper stubUtilities = new FakeUtilitiesWrapper();
        FakeGitletPathsWrapper stubGitletPaths = new FakeGitletPathsWrapper();
        stubGitletPaths.fakePath = Path.of(System.getProperty("user.dir"));
        StagingArea stagingArea = new StagingArea();

        HashMap<String, String> actual = stagingArea.updateStagingArea(new HashMap<>());

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual instanceof HashMap<String, String>);
        Assert.assertTrue(actual.isEmpty());
    }

    @Ignore
    @Test
    public void StagingArea_getBlobsReadyToBeCommitted_returnHashMap() {
        IUtilitiesWrapper stubUtilities = new FakeUtilitiesWrapper();
        FakeGitletPathsWrapper stubGitletPaths = new FakeGitletPathsWrapper();
        stubGitletPaths.fakePath = Path.of(System.getProperty("user.dir"));
        StagingArea stagingArea = new StagingArea();

        HashMap<String, String> actual = stagingArea.getBlobsReadyToBeCommitted();

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual instanceof HashMap<String, String>);
        Assert.assertTrue(actual.isEmpty());
    }

    @Ignore
    @Test
    public void StagingArea_isStagingAreaInCleanState_returnBoolean() {
        IUtilitiesWrapper stubUtilities = new FakeUtilitiesWrapper();
        FakeGitletPathsWrapper stubGitletPaths = new FakeGitletPathsWrapper();
        stubGitletPaths.fakePath = Path.of(System.getProperty("user.dir"));
        StagingArea stagingArea = new StagingArea();

        boolean expected = stagingArea.isStagingAreaInCleanState();

        Assert.assertTrue(expected);
    }

}
