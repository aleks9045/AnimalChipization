package org.example.animalchipization.util;

import java.util.Base64;

/**
 * @author Aleksey
 */
public class Base64Coder {
    public static String encodeBase64(String s) {
        byte[] bytesEncoded = Base64.getMimeEncoder().encode(s.getBytes());
        return new String(bytesEncoded);
    }

    public static String decodeBase64(String s) {
        byte[] bytesEncoded = Base64.getMimeDecoder().decode(s.getBytes());
        return new String(bytesEncoded);
    }
}
