//import gitlet.objects.Blob;
//import gitlet.Utils;
//import org.junit.Assert;
//import org.junit.Test;
//
//public class BlobTest {
//    /*
//     * TODO: remove IUtilitiesWrapper dependency from Blob constructor:
//     *
//     * - Generate hash and pass to constructor
//     * - Or, use a mock to mimic the Utilities
//     * */
//
//    @Test
//    public void Blob_getContent_returnArrayOfBytes() {
//        byte[] dummyBytes = new byte[]{77, 120, -20};
//        String dummyFileName = "foo.txt";
//        String dummyFilePath = System.getProperty("user.dir");
//        Blob blob = new Blob(dummyBytes, dummyFileName, dummyFilePath);
//        byte[] expected = new byte[]{77, 120, -20};
//
//        byte[] actual = blob.getFileContent();
//
//        Assert.assertArrayEquals(expected, actual);
//    }
//
//    @Test
//    public void Blob_getHash_returnString() {
//        byte[] dummyBytes = new byte[]{77, 120, -20};
//        String dummyFileName = "foo.txt";
//        String dummyFilePath = System.getProperty("user.dir");
//        Blob blob = new Blob(dummyBytes, dummyFileName, dummyFilePath);
//        String expected = Utils.sha1(dummyBytes);
//
//
//        String actual = blob.getHash();
//
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void Blob_getFileName_returnString() {
//        byte[] dummyBytes = new byte[]{77, 120, -20};
//        String dummyFileName = "foo.txt";
//        String dummyFilePath = System.getProperty("user.dir");
//        Blob blob = new Blob(dummyBytes, dummyFileName, dummyFilePath);
//        String expected = "foo.txt";
//
//        String actual = blob.getFileName();
//
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void Blob_getFilePath_returnString() {
//        byte[] dummyBytes = new byte[]{77, 120, -20};
//        String dummyFileName = "foo.txt";
//        String dummyFilePath = System.getProperty("user.dir");
//        Blob blob = new Blob(dummyBytes, dummyFileName, dummyFilePath);
//        String expected = System.getProperty("user.dir");
//
//        String actual = blob.getFilePath();
//
//        Assert.assertEquals(expected, actual);
//    }
//}
