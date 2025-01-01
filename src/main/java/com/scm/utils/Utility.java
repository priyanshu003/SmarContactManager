package com.scm.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Utility {
     private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Encrypts the plain text password.
     *
     * @param plainPassword the plain text password
     * @return the encrypted password
     */
    public static String encryptPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }

    /**
     * Verifies if the plain password matches the encrypted password.
     *
     * @param plainPassword     the plain text password
     * @param encryptedPassword the encrypted password
     * @return true if the passwords match, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String encryptedPassword) {
        return encoder.matches(plainPassword, encryptedPassword);
    }

}
