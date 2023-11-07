package vendmachtrack.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vendmachtrack.core.util.PasswordHandler;
import vendmachtrack.ui.access.AccessService;

/**
 * This class contains JUnit tests for the {@link PasswordHandlerController} class.
 * It focuses on testing the behavior and functionality of the password handling controller.
 * For Security reasons it will not test the case where the correct password is written.
 *
 * @see PasswordHandlerController
 */
public class PasswordHandlerControllerTest extends ApplicationTest {

    @Mock
    private PasswordHandler passwordHandler;

    @Mock
    private AccessService accessService;

    private PasswordHandlerController controller;

    /**
     * Initializes and sets up the graphical user interface for the password handling application.
     *
     * <p>
     * This method is responsible for creating and configuring the graphical user interface (GUI) components.
     * It loads the FXML file "PasswordApp.fxml," sets up the scene, and displays the application's main stage.
     * The scene is configured with an external stylesheet defined in "styles.css."
     * </p>
     *
     * @param stage The primary stage where the GUI will be displayed.
     * @throws Exception if any error occurs during GUI initialization.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Set up the GUI
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PasswordApp.fxml"));
        Parent parent = fxmlLoader.load();

        // Set the scene and show the stage
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets up the test environment before each individual test method is executed.
     *
     * <p>
     * This method is annotated with {@code @BeforeEach} and runs before each test method.
     * It is responsible for creating a new instance of the {@link PasswordHandlerController} and
     * setting the provided {@code accessService} as its access service.
     * </p>
     */
    @BeforeEach
    public void setUp() {
        controller = new PasswordHandlerController();
        controller.setAccessService(accessService);
    }


    /**
     * Tests the behavior of the application when an incorrect password is provided.
     *
     * <p>
     * This test verifies that the application correctly handles the case of an incorrect password entry.
     * it simulates the following scenario:
     * </p>
     *
     * <ol>
     *   <li>Act: User enters an incorrect password ("WRONG") into the password field.
     *            Afterwards press the confirm button</li>
     *   <li>Assert: verify that the error label with the ID "label" displays the message "Incorrect password. Please try again."</li>
     * </ol>
     *
     * <p>
     * This test verifies that the application correctly handles the case of an incorrect password entry.
     * </p>
     */
    @Test
    public void whenPasswordIsIncorrect_thenShouldShowErrorLabel() {

        // Act
        clickOn("#passwordField").write("WRONG");
        clickOn("#confirmButton");

        // Assert
        verifyThat("#label", hasText("Incorrect password. Please try again"));
    }

}
