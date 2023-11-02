package vendmachtrack.ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.Fault;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vendmachtrack.ui.access.MachineTrackerAccessRemote;

public class ServerControllerTest extends ApplicationTest {

    @Mock
    private App mockApp;
    
    private WireMockServer wireMockServer;
    private ServerController serverController;

    @Override
    public void start(Stage stage) throws Exception {
        // Set up the GUI
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Server.fxml"));
        Parent root = loader.load();
        serverController = loader.getController();

        stage.setScene(new Scene(root));
        stage.show();
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8080));
        wireMockServer.start();
        setupStub();
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    public void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo("/health"))
                .willReturn(aResponse().withStatus(200)));  // Mock a healthy response
    }

    @Test
    public void testHealthyServer() {
        
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
       
    @Test
    public void testUnhealthyServer() {
        
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

    @Test
    public void testServerTimeout() {

        //Arrange
        wireMockServer.stubFor(get(urlEqualTo("/health"))
        .willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    
    
        // Act
        clickOn("#serverUrlField").write(wireMockServer.baseUrl() + "/");
        clickOn("#trackerFileNameField").write("tracker.json");
        clickOn("Submit");
        System.setOut(originalOut);
        

        // Assert
        assertTrue(outContent.toString().contains("Error during server health check"), "Expected error during server health check but didn't get one");
        assertTrue(outContent.toString().contains("Using local access as a fallback"), "Expected local access as a fallback but got something else");
    }
    
}
