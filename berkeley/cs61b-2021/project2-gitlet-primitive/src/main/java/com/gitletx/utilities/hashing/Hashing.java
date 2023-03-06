package com.gitletx.utilities.hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing implements IHashing{
    @Override
    public String sha1(Object... objects) {
        try {
            StringBuilder bytesHash = new StringBuilder();
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            for (Object obj : objects) {
                digest.update(String.valueOf(obj).getBytes(StandardCharsets.UTF_8));
            }

            /*
             * "%02x" is :
             *   - % is the format specifier character.
             *   - 0 is a flag that specifies zero padding.
             *   - 2 is the width that specifies the minimum number of characters to be written in the output string.
             *   - x is the conversion character that specifies the format to be in hexadecimal.
             *
             * By using the %02x format, a single byte value will be displayed as two characters.
             * If the value is less than 16, a zero will be added as padding to the left of the hexadecimal
             * representation to make it two characters long.
             * */
            for (byte b : digest.digest()) {
                bytesHash.append(String.format("%02x", b));
            }

            return bytesHash.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
