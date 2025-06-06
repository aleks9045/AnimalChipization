package org.example.animalchipization.service.auth.util;

/**
 * @author Aleksey
 */
public class UserAuthentication {
    public static String encodeEmailAndPassword(String email, String password){
        return Base64Coder.encodeBase64(email + ":" + password);
    }
}
