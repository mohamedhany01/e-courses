package byow.Utilities;

public class IntegerUtilities {
    public boolean isValidInteger(String str) {
        return String.valueOf(Integer.MAX_VALUE).length() - 1 <= str.length();
    }

    public boolean reachedLimit(String str) {
        return String.valueOf(Integer.MAX_VALUE).length() - 1 == str.length();
    }

    public boolean isValidDigit(Character c) {
        return Character.isDigit(c);
    }

}
