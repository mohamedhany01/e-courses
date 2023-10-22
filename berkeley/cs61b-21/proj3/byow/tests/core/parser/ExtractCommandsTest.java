package byow.tests.core.parser;

import byow.Core.InputStringParser;
import byow.Utilities.Instruction;
import byow.tests.core.Utilities;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class ExtractCommandsTest {
    final String methodName = "extractInstructions";
    final int loadGameIndex = 1;

    @Test
    public void Parser_ExtractInstructions_NewGameSingleCommandNoQuit() throws Exception {
        Queue<Instruction> instructions;
        String input = "N999SD".toLowerCase();
        final int newGameIndex = input.indexOf('s') + 1;


        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, int.class, String.class);

        instructions = (Queue<Instruction>) method.invoke(parser, newGameIndex, input);

        assertEquals(1, instructions.size());
        assertEquals(1, instructions.peek().getCount());
    }

    @Test
    public void Parser_ExtractInstructions_NewGameSingleCommandWithQuit() throws Exception {
        Queue<Instruction> instructions;
        String input = "N999SD:Q".toLowerCase();
        final int newGameIndex = input.indexOf('s') + 1;


        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, int.class, String.class);

        instructions = (Queue<Instruction>) method.invoke(parser, newGameIndex, input);

        assertEquals(2, instructions.size());
        assertEquals(1, instructions.peek().getCount());
        assertEquals(1, instructions.peek().getCount());
    }

    @Test
    public void Parser_ExtractInstructions_NewGameSingleCommandQuit() throws Exception {
        Queue<Instruction> instructions;
        String input = "N999S:Q".toLowerCase();
        final int newGameIndex = input.indexOf('s') + 1;


        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, int.class, String.class);

        instructions = (Queue<Instruction>) method.invoke(parser, newGameIndex, input);

        assertEquals(1, instructions.size());
        assertEquals(1, instructions.peek().getCount());
    }

    @Test
    public void Parser_ExtractInstructions_NewGameMultipleCommandNoQuit() throws Exception {
        Queue<Instruction> instructions;
        String input = "N999SSSWWAAWWDD".toLowerCase();
        final int newGameIndex = input.indexOf('s') + 1;


        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, int.class, String.class);

        instructions = (Queue<Instruction>) method.invoke(parser, newGameIndex, input);

        assertEquals(5, instructions.size());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(0, instructions.size());
    }

    @Test
    public void Parser_ExtractInstructions_NewGameMultipleCommandWithQuit() throws Exception {
        Queue<Instruction> instructions;
        String input = "N999SSSWWAAWWDD:Q".toLowerCase();
        final int newGameIndex = input.indexOf('s') + 1;


        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, int.class, String.class);

        instructions = (Queue<Instruction>) method.invoke(parser, newGameIndex, input);

        assertEquals(6, instructions.size());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(1, instructions.poll().getCount());
        assertEquals(0, instructions.size());
    }

    @Test
    public void Parser_ExtractInstructions_NewGameNoCommands() throws Exception {
        Queue<Instruction> instructions;
        String input = "N999S".toLowerCase();
        final int newGameIndex = input.indexOf('s') + 1;


        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, int.class, String.class);

        instructions = (Queue<Instruction>) method.invoke(parser, newGameIndex, input);

        assertEquals(0, instructions.size());
        assertNull(instructions.peek());
    }

    @Test
    public void Parser_ExtractInstructions_NewGameUnknownCommandThrowException() {
        String input = "N999S$".toLowerCase();
        final int newGameIndex = input.indexOf('s') + 1;
        String expected = "Unknown command $ at 5";


        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, int.class, String.class);

        Exception exception = assertThrows(InvocationTargetException.class, () -> method.invoke(parser, newGameIndex, input));
        String actual = exception.getCause().getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void Parser_ExtractInstructions_LoadGameSingleCommandNoQuit() throws Exception {
        Queue<Instruction> instructions;
        String input = "LS".toLowerCase();


        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, int.class, String.class);

        instructions = (Queue<Instruction>) method.invoke(parser, loadGameIndex, input);

        assertEquals(1, instructions.size());
        assertEquals(1, instructions.peek().getCount());
    }

    @Test
    public void Parser_ExtractInstructions_LoadGameSingleCommandWithQuit() throws Exception {
        Queue<Instruction> instructions;
        String input = "LD:Q".toLowerCase();

        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, int.class, String.class);

        instructions = (Queue<Instruction>) method.invoke(parser, loadGameIndex, input);

        assertEquals(2, instructions.size());
        assertEquals(1, instructions.peek().getCount());
        assertEquals(1, instructions.peek().getCount());
    }

    @Test
    public void Parser_ExtractInstructions_LoadGameSingleCommandQuit() throws Exception {
        Queue<Instruction> instructions;
        String input = "L:Q".toLowerCase();


        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, int.class, String.class);

        instructions = (Queue<Instruction>) method.invoke(parser, loadGameIndex, input);

        assertEquals(1, instructions.size());
        assertEquals(1, instructions.peek().getCount());
    }

    @Test
    public void Parser_ExtractInstructions_LoadGameMultipleCommandNoQuit() throws Exception {
        Queue<Instruction> instructions;
        String input = "LSSWWAAWWDD".toLowerCase();

        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, int.class, String.class);

        instructions = (Queue<Instruction>) method.invoke(parser, loadGameIndex, input);

        assertEquals(5, instructions.size());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(0, instructions.size());
    }

    @Test
    public void Parser_ExtractInstructions_LoadGameMultipleCommandWithQuit() throws Exception {
        Queue<Instruction> instructions;
        String input = "LSSWWAAWWDD:Q".toLowerCase();


        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, int.class, String.class);

        instructions = (Queue<Instruction>) method.invoke(parser, loadGameIndex, input);

        assertEquals(6, instructions.size());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(2, instructions.poll().getCount());
        assertEquals(1, instructions.poll().getCount());
        assertEquals(0, instructions.size());
    }

    @Test
    public void Parser_ExtractInstructions_LoadGameNoCommands() throws Exception {
        Queue<Instruction> instructions;
        String input = "L".toLowerCase();

        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, int.class, String.class);

        instructions = (Queue<Instruction>) method.invoke(parser, loadGameIndex, input);

        assertEquals(0, instructions.size());
        assertNull(instructions.peek());
    }

    @Test
    public void Parser_ExtractInstructions_LoadGameUnknownCommandThrowException() {
        String input = "L$".toLowerCase();
        String expected = "Unknown command $ at 1";

        InputStringParser parser = new InputStringParser(input);
        Utilities utilities = new Utilities();
        Method method = utilities.getPrivateMethod(parser.getClass(), methodName, int.class, String.class);


        Exception exception = assertThrows(InvocationTargetException.class, () -> method.invoke(parser, loadGameIndex, input));
        String actual = exception.getCause().getMessage();

        assertEquals(expected, actual);
    }

}
