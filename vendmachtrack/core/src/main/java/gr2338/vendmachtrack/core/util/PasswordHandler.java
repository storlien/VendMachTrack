package gr2338.vendmachtrack.core.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * A utility class for password hashing and verification using the Argon2
 * hashing algorithm.
 * <p>
 * This class is responsible for generating password hashes and verifying them
 * against stored hashes.
 * It is designed to use the Argon2 algorithm for password security, which is
 * considered one of
 * the most secure and modern hashing algorithms available.
 * </p>
 * <p>
 * The reference password is stored as a hash in the
 * {@code /vendmachtrack/core/password.txt} file,
 * providing an abstraction and security layer against raw password exposure.
 * </p>
 */
public final class PasswordHandler {

    private static final String PASSWORD_FILE = "password.txt";
    private static final int ARGON2_ITERATIONS = 10;
    private static final int ARGON2_MEMORY = 65536;
    private static final int ARGON2_PARALLELISM = 1;

    /**
     * Private constructor to prevent instantiation of class
     */
    private PasswordHandler() {
    }

    /**
     * Hashes a provided password using the Argon2 algorithm and returns the
     * resultant hash.
     *
     * @param password Password provided by the user as a string.
     * @return The resultant Argon2 hash of the provided password as a string.
     */
    static String hashPassword(final String password) {
        Argon2 argon2 = Argon2Factory.create();

        // Using constants instead of magic numbers
        return argon2.hash(ARGON2_ITERATIONS, ARGON2_MEMORY, ARGON2_PARALLELISM, password.toCharArray());
    }

    /**
     * Verifies a provided password against the stored reference password hash.
     * <p>
     * This method reads the stored hash from the {@code password.txt} file and uses
     * the Argon2 algorithm's built-in verification mechanism to determine if the
     * provided
     * password matches the reference.
     * </p>
     *
     * @param password The password provided by the user to be verified.
     * @return {@code true} if the provided password matches the reference;
     * {@code false} otherwise.
     */
    public static boolean verifyPassword(final String password) {
        String validPasswordHash;
        Argon2 argon2 = Argon2Factory.create();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(PasswordHandler.class.getResourceAsStream(PASSWORD_FILE)),
                        StandardCharsets.UTF_8))) {
            validPasswordHash = br.readLine();
        } catch (Exception e) {
            System.err.println("Failed to read password file: " + e);
            return false;
        }

        return argon2.verify(validPasswordHash, password.toCharArray());
    }
}
