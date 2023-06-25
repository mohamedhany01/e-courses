import com.gitletx.utilities.hashing.IHashing;
import com.gitletx.utilities.hashing.StubHashing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTools {
    @Test
    public void generateSHA1() {
        IHashing hashing = new StubHashing();

        String[] expectedHashes = new String[]{
                "0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33",
                "62cdb7020ff920e5aa642c3d4066950dd1f01f4d",
                "6b369e490de120a99ec239807263a55ceec7b8a3"
        };

        String[] valuesToHash = new String[]{"foo", "bar", "buz"};

        for (int i = 0; i < valuesToHash.length; i++) {
            Assertions.assertEquals(expectedHashes[i], hashing.sha1(valuesToHash[i]));
        }
    }
}
