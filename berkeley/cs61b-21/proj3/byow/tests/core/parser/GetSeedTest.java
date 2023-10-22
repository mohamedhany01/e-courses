package byow.tests.core.parser;

import byow.Core.InputStringParser;
import byow.tests.core.Utilities;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetSeedTest {
    final String methodName = "getSeed";

    @Test
    public void Parser_GetSeed_ValidSeed() throws Exception {
        String input = "N999SDDDSSSDWWAAAAASSDDDD:Q";
        int expected = 999;

        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, String.class);

        int actual = (int) method.invoke(parser, input);

        assertEquals(expected, actual);
    }


    @Test
    public void Parser_GetSeed_InvalidSeedCommandThrowException() throws Exception {
        String input = "N999XDDDSSSDWWAAAAASSDDDD:Q";
        String expected = "Unknown command x at 4";

        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, String.class);


        Exception exception = assertThrows(InvocationTargetException.class, () -> method.invoke(parser, input));
        String actual = exception.getCause().getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void Parser_GetSeed_ParseBigNumber() throws Exception {
        String input = "N999843567SDDDSSSDWWAAAAASSDDDD:Q";
        int expected = 999843567;

        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, String.class);

        int actual = (int) method.invoke(parser, input);

        assertEquals(expected, actual);
    }

    @Test
    public void Parser_GetSeed_ParseSingleNumber() throws Exception {
        String input = "N1SDDDSSSDWWAAAAASSDDDD:Q";
        int expected = 1;

        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, String.class);

        int actual = (int) method.invoke(parser, input);

        assertEquals(expected, actual);
    }

    @Test
    public void Parser_GetSeed_ParseNoSeed() throws Exception {
        String input = "NSDDDSSSDWWAAAAASSDDDD:Q";
        int expected = -1;

        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, String.class);

        int actual = (int) method.invoke(parser, input);

        assertEquals(expected, actual);
    }
}
