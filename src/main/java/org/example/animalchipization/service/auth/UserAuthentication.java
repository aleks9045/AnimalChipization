package org.example.animalchipization.service.auth;

/**
 * @author Aleksey
 */
public class UserAuthentication {
    public static String hashEmailAndPassword(String email, String password){
        return Hasher.hashToBase64(email + ":" + password);
    }
}
