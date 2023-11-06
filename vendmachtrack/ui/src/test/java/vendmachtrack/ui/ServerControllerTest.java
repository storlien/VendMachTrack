package vendmachtrack.ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class contains JUnit tests for the {@link ServerController} class. It tests the behavior of the server configuration interface.
 *
 * @see ServerController
 */
public class ServerControllerTest extends ApplicationTest {

    @Mock
    private App mockApp;
    
    private WireMockServer wireMockServer;
  

    /**
     * Initializes and starts the JavaFX application, including the server controller and WireMock server.
     * 
     * <p>
     * This method is an override of the JavaFX {@code start} method and is responsible for setting up the graphical user interface (GUI) and
     * starting the WireMock server for testing purposes.
     * </p>
     * 
     * <p>
     * It performs the following steps:
     * </p>
     * <ol>
     *   <li>Set up the GUI by loading the "Server.fxml" resource file and initializing the server controller.</li>
     *   <li>Create and configure a new JavaFX stage with the loaded GUI and display it.</li>
     *   <li>Initialize and start a WireMock server on port 8080 to mock server responses.</li>
     *   <li>Call the {@code setupStub} method to set up a WireMock stub for mocking a healthy server response.</li>
     * </ol>
     * 
     * @param stage The primary stage for the JavaFX application.
     * @throws Exception if any error occurs during the initialization.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Server.fxml"));
        Parent root = loader.load();
       
        stage.setScene(new Scene(root));
        stage.show();
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8080));
        wireMockServer.start();
        setupStub();
    }

    /**
     * Performs cleanup after each test by stopping the WireMock server.
     * 
     * <p>
     * This method is annotated with {@code @AfterEach} and runs after each individual test method.
     * It is responsible for stopping the WireMock server to release any resources.
     * </p>
     */
    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    /**
     * Sets up a WireMock stub to mock a healthy server response.
     * 
     * <p>
     * This method configures a WireMock stub that simulates a healthy server response with an HTTP status code of 200.
     * It can be used to prepare the server for testing scenarios where a healthy response is expected.
     * </p>
     */
    public void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo("/health"))
                .willReturn(aResponse().withStatus(200))); 
    }

    /**
     * Tests the behavior of the {@link ServerController} when connecting to a healthy server.
     * 
     * <p>
     * This test case focuses on verifying how the controller handles the scenario where it successfully connects to a healthy server.
     * </p>
     * 
     * <ol>
     *   <li>Arrange: Redirect the standard output stream to capture console output.</li>
     *   <li>Act: Simulate user input by providing the server URL and tracker file name, and clicking the "Submit" button and 
     *             restore the original standard output stream.</li>
     *   <li>Assert: Verify that the captured console output indicates the use of remote access due to the healthy server connection.</li>
     * </ol>
     */
    @Test
    public void ServerController_testHealthyServer() {
        
        // Arrange
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Act
        clickOn("#serverUrlField").write(wireMockServer.baseUrl() + "/");
        clickOn("#trackerFileNameField").write("tracker.json");
        clickOn("Submit");
        System.setOut(originalOut);

        // Assert
        assertTrue(outContent.toString().contains("Using remote access"), "Expected remote access but got local access");
    }
       
    /**
     * Tests the behavior of the {@link ServerController} when connecting to an unhealthy server.
     * 
     * <p>
     * This test case focuses on verifying how the controller handles the scenario where it attempts to connect to an unhealthy server.
     * </p>
     * 
     * <ol>
     *   <li>Arrange: Set up a WireMock stub to simulate an unhealthy server response (HTTP 500 status).
     *                And redirect the standard output stream to capture console output.
     *   <li>Act: Simulate user input by providing the server URL and tracker file name, and clicking the "Submit" button 
     *            and restore the original standard output stream.</li>
     *   <li>Assert: Verify that the captured console output indicates the use of local access due to the unhealthy server response.</li>
     * </ol>
 */
    @Test
    public void ServerController_testUnhealthyServer() {
        
        // Arrange
        wireMockServer.stubFor(get(urlEqualTo("/health"))
        .willReturn(aResponse().withStatus(500))); 
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Act
        clickOn("#serverUrlField").write(wireMockServer.baseUrl() + "/");
        clickOn("#trackerFileNameField").write("tracker.json");
        clickOn("Submit");
        System.setOut(originalOut);

        // Assert
        assertTrue(outContent.toString().contains("Using local access"), "Expected local access but got remote access");
    } 

    /**
     * Tests the behavior of the {@link ServerController} when submitting an invalid server URL.
     * 
     * <p>
     * This test case focuses on verifying that the controller handles the scenario where an invalid server URL is submitted.
     * </p>
     * 
     * <ol>
     *   <li>Arrange: Set up an invalid URL and an expected error message. Simulate user input by writing the invalid URL to the server URL field.</li>
     *   <li>Act: Trigger the submission by clicking the "Submit" button.</li>
     *   <li>Assert: Verify that a label displays the expected error message indicating the invalid URL.</li>
     * </ol>
     */
    @Test
    public void ServerController_testInvalidURLSubmission() {
        
        // Arrange
        final String invalidURL = "htp:/invalidurl"; 
        String expectedErrorMessage = "Invalid input in server URL: " + invalidURL;
        clickOn("#serverUrlField").write(invalidURL); 
        clickOn("#trackerFileNameField").write("tracker.json"); 

        // Act
        clickOn("Submit"); // The ID for the submit button should be set in the FXML or the controller

        // Assert
         Label errorLabel = lookup("#label").query();
         assertEquals(expectedErrorMessage, errorLabel.getText(), "Label should display invalid URL message");
    }

}
