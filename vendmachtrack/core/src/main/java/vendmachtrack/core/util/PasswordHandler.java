package vendmachtrack.core.util;

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
public class PasswordHandler {

    /**
     * Path to the file containing the hashed reference password.
     */
    private static final String passwordFile = "/vendmachtrack/core/password.txt";

    /**
     * Hashes a provided password using the Argon2 algorithm and returns the
     * resultant hash.
     *
     * @param password Password provided by the user as a string.
     * @return The resultant Argon2 hash of the provided password as a string.
     */
    static String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();

        int iterations = 10;
        int memory = 65536;
        int parallelism = 1;

        return argon2.hash(iterations, memory, parallelism, password.toCharArray());
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
     *         {@code false} otherwise.
     */
    public static boolean verifyPassword(String password) {
        String validPasswordHash;
        Argon2 argon2 = Argon2Factory.create();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(PasswordHandler.class.getResourceAsStream(passwordFile)),
                        StandardCharsets.UTF_8))) {
            validPasswordHash = br.readLine();
        } catch (Exception e) {
            System.err.println("Failed to read password file: " + e);
            return false;
        }

        return argon2.verify(validPasswordHash, password.toCharArray());
    }
}
