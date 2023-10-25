package core;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class PasswordHandler {

    private static final String passwordFile = "/password.txt";

    /**
     * Hashes a password with Argon2 and returns the hash value as String.
     *
     * @param password Password as string from input field
     * @return Hashed password as string
     */
    static String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();

        int iterations = 10;
        int memory = 65536;
        int parallelism = 1;

        return argon2.hash(iterations, memory, parallelism, password.toCharArray());
    }

    /**
     * Verifies a password against saved password. The correct password is hashed and located in resources/password.txt.
     *
     * @param password Password to be verified
     * @return True if password is correct, false if not.
     */
    static boolean verifyPassword(String password) {
        String validPasswordHash;
        Argon2 argon2 = Argon2Factory.create();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(PasswordHandler.class.getResourceAsStream(passwordFile)), StandardCharsets.UTF_8))) {
            validPasswordHash = br.readLine();
        } catch (Exception e) {
            System.err.println("Failed to read password file: " + e);
            return false;
        }

        return argon2.verify(validPasswordHash, password.toCharArray());
    }
}
