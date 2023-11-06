package vendmachtrack.core;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class PasswordHandler {

    private static final String PASSWORD_FILE = "/vendmachtrack/core/password.txt"; // Renamed to all uppercase
    private static final int ARGON2_ITERATIONS = 10; // Defined magic number as constant
    private static final int ARGON2_MEMORY = 65536; // Defined magic number as constant
    private static final int ARGON2_PARALLELISM = 1; // Defined magic number as constant

    private PasswordHandler() {
        // Private constructor to prevent instantiation
    }

    /**
     * Hashes a password with Argon2 and returns the hash value as String.
     *
     * @param password Password as string from input field
     * @return Hashed password as string
     */
    static String hashPassword(final String password) {
        Argon2 argon2 = Argon2Factory.create();

        // Using constants instead of magic numbers
        return argon2.hash(ARGON2_ITERATIONS, ARGON2_MEMORY, ARGON2_PARALLELISM, password.toCharArray());
    }

    /**
     * Verifies a password against saved password. The correct password is hashed
     * and located in resources/password.txt.
     *
     * @param password Password to be verified
     * @return True if password is correct, false if not.
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
