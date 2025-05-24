package org.example.animalchipization.service.auth;

import java.util.Base64;

/**
 * @author Aleksey
 */
public class Hasher {
    public static String hashToBase64(String s) {
        byte[] bytesEncoded = Base64.getMimeEncoder().encode(s.getBytes());
        return new String(bytesEncoded);
    }
}
