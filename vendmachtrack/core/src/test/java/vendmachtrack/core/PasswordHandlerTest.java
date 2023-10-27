package vendmachtrack.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class PasswordHandlerTest {
    
    
/**
 * Tests the hashPassword method of the PasswordHandler class with regular input.
 */
  @Test
    public void testHashPasswordWithRegularInput() {
        String password = "normalPassword";
        String hashedPassword = PasswordHandler.hashPassword(password);

        assertNotNull(hashedPassword);
        assertFalse(hashedPassword.isEmpty()); 
    }

    /**
     * This test verifies that the PasswordHandler class can correctly verify a password that has been hashed.
     * It sets a password, hashes it, and then verifies it using the verifyPassword method of the PasswordHandler class.
     * If the password is verified, the test passes.
     * 
     */
    @Test
    public void testVerifyPasswordWithCorrectPassword() {
        String password = "brus"; // migth not be good security to have password in clear text
        String hashedPassword = PasswordHandler.hashPassword(password);
        assertTrue(PasswordHandler.verifyPassword(password)); 
    }

    /**
     * Tests the verifyPassword method of the PasswordHandler class with an incorrect password.
     */
    @Test
    public void testVerifyPasswordWithIncorrectPassword() {
        String password = "wrongPassword";
        assertFalse(PasswordHandler.verifyPassword(password));
    }


}
