import gitlet.UtilitiesWrapper;
import org.junit.Assert;
import org.junit.Test;

public class UtilitiesWrapperTest {
    @Test
    public void UtilitiesWrapper_getSha1_returnSha1AsString() {
        UtilitiesWrapper utilities = new UtilitiesWrapper();
        String expected = "0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33";

        String actual = utilities.sha1("foo");

        Assert.assertEquals(expected, actual);
    }
}
