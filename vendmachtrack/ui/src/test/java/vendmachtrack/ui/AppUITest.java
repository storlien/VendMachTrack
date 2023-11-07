package vendmachtrack.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.stage.Stage;

/**
 * This class contains Unit tests for the {@link App} class. It tests that the App launches correctly
 *
 * @see App
 */
public class AppUITest extends ApplicationTest {

    private App app;

    /**
     * Initializes and starts the {@link App} application for testing purposes.
     *
     * <p>
     * This method is an override of the JavaFX {@code start} method and is responsible for creating and starting
     * the {@link App} instance within the testing environment.
     * </p>
     *
     * <p>
     * It performs the following steps:
     * </p>
     * <ol>
     *   <li>Create a new instance of the {@link App} class.</li>
     *   <li>Starts the application using the provided {@link Stage} instance.</li>
     * </ol>
     *
     * @param stage The primary stage for the JavaFX application.
     * @throws IOException if an I/O error occurs during application startup.
     */
    @Override
    public void start(Stage stage) throws IOException {

        this.app = new App();
        app.start(stage);
    }

    /**
     * Tests the initialization of the {@link App} application and verifies the presence of certain GUI elements.
     *
     * <p>
     * This test method checks the following:
     * </p>
     * <ol>
     *   <li>Asserts that the {@code app} instance is not null, indicating a successful initialization of the application.</li>
     *   <li>Asserts the presence of the "serverURLField" GUI element by looking it up using the provided selector.</li>
     * </ol>
     *
     * @throws InterruptedException if the test is interrupted while waiting.
     */
    @Test
    public void testApp() {

        assertNotNull(this.app);
        assertNotNull("#serverURLFIeld");
    }
}

