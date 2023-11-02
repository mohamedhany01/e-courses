package byow.Core.gui;

import byow.Utilities.IntegerUtilities;
import byow.Utilities.ErrorHandler;
import byow.lab13.Position;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class Window {
    private final int width;
    private final int height;

    public Window(int width, int height) {
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getUserInput(int inputSize) {
        StringBuilder userInput = new StringBuilder();

       while (userInput.toString().length() < inputSize) {
//           System.out.println("Waiting input...");
//           StdDraw.pause(300);
           if (StdDraw.hasNextKeyTyped()) {
               userInput.append(StdDraw.nextKeyTyped());
               StdDraw.show();
           }
       }

       return userInput.toString().toLowerCase();
    }

    public String printInputOn(Position inputPosition, String placeholder, Position placeholderPosition) {
        StringBuilder userInput = new StringBuilder("0");
        IntegerUtilities utilities = new IntegerUtilities();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String input = userInput.toString();
                Character cInput = StdDraw.nextKeyTyped();

                // Clean temp zero at the beginning of the string
                if (input.charAt(0) == '0') {
                    userInput.deleteCharAt(0);
                }

                // In case of the number of digits reached to max limit or the current char is the Enter key
                if (utilities.reachedLimit(userInput.toString()) || cInput == '\n') {
                    break;
                }

                if (!utilities.isValidDigit(cInput)) {
                    ErrorHandler handler = new ErrorHandler();
                    handler.throwError("Digit " + cInput + " not a number.");
                }

                userInput.append(cInput);

                StdDraw.clear(Color.BLACK);

                // Placeholder
                StdDraw.text(placeholderPosition.getX(), placeholderPosition.getY(), placeholder);

                // Other text
                StdDraw.text(inputPosition.getX(), inputPosition.getY(), userInput.toString());
                StdDraw.show();
            }
        }
        return userInput.toString();
    }
}
