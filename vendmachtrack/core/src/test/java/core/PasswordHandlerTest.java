package core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class PasswordHandlerTest {
    
    
  @Test
    public void testHashPasswordWithRegularInput() {
        String password = "normalPassword";
        String hashedPassword = PasswordHandler.hashPassword(password);

        assertNotNull(hashedPassword);
        assertFalse(hashedPassword.isEmpty()); 
    }

    @Test
    public void testVerifyPasswordWithCorrectPassword() {
        
    }

    @Test
    public void testVerifyPasswordWithIncorrectPassword() {
        String password = "wrongPassword";
        assertFalse(PasswordHandler.verifyPassword(password));
    }


}
