package vendmachtrack.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class PasswordHandlerTest {
    
    
/**
 * Tests the hashPassword method of the PasswordHandler class with regular input.
 */
  @Test
    public void testHashPasswordWithRegularInput() {
        //Arrange
        String password = "normalPassword";

        //Act
        String hashedPassword = PasswordHandler.hashPassword(password);

        //Assert
        assertNotNull(hashedPassword);
        assertFalse(hashedPassword.isEmpty()); 
    }

    /**
     * This test verifies that the PasswordHandler class can correctly verify a password that has been hashed.
     * If the password is verified, i.e is correct, the test passes.
     * 
     */
    @Test
    public void testVerifyPasswordWithCorrectPassword() {
        //Arrange
        String password = "brus"; // migth not be good security to have password in clear text
        
        //Act & Assert
        assertTrue(PasswordHandler.verifyPassword(password)); 
    }

    /**
     * Tests the verifyPassword method of the PasswordHandler class with an incorrect password.
     */
    @Test
    public void testVerifyPasswordWithIncorrectPassword() {
        //Arrange
        String password = "wrongPassword";
        
        //Act & Assert
        assertFalse(PasswordHandler.verifyPassword(password));
    }


}
