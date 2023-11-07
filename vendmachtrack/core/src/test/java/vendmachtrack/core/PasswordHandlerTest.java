package vendmachtrack.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


    /**
    * This class contains test methods for testing the functionality of the {@link PasswordHandler} class.
    *
    *<p>
    *
    * The tests in this class focus on various aspects of the PasswordHandler class, including hashing and verifying passwords.
    *
    * </p>
    *
    * NOTE: Due to security reasons the test case where the correct password is verified is not included in this test class
     */
public class PasswordHandlerTest {

    /**
     * Tests the {@link PasswordHandler#hashPassword(String)} method with a regular input password.
     *
     * <p>
     *
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Define a regular input password ("normalPassword").</li>
     *   <li>Act: Call the hashPassword(password) method on the PasswordHandler object to hash the password.</li>
     *   <li>Assert: Verify that the hashed password is not null and not empty, indicating that the hashing process was successful.</li>
     * </ol>
     */
    @Test
    public void PasswordHandler_testHashPasswordWithRegularInput() {
        // Arrange
        String password = "normalPassword";

        // Act
        String hashedPassword = PasswordHandler.hashPassword(password);

        // Assert
        assertNotNull(hashedPassword);
        assertFalse(hashedPassword.isEmpty());
    }

    /**
     * Tests the {@link PasswordHandler#verifyPassword(String)} method with an incorrect password.
     *
     * This test case focuses on verifying that the verifyPassword() method of a {@link PasswordHandler} object correctly returns false when verifying an incorrect password.
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Define an incorrect password ("wrongPassword").</li>
     *   <li>Act & Assert: Call the verifyPassword(password) method on the PasswordHandler object with the incorrect password and assert that it returns false, indicating that the password verification failed.</li>
     * </ol>
     */
    @Test
    public void PasswordHandler_testVerifyPasswordWithIncorrectPassword() {
        //Arrange
        String password = "wrongPassword";

        //Act & Assert
        assertFalse(PasswordHandler.verifyPassword(password));
    }

}
