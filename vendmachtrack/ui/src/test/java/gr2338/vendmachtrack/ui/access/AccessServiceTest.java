package gr2338.vendmachtrack.ui.access;

import gr2338.vendmachtrack.ui.access.AccessService;
import gr2338.vendmachtrack.ui.access.MachineTrackerAccessLocal;
import gr2338.vendmachtrack.ui.access.MachineTrackerAccessRemote;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.Fault;


/**
 * This class contains unit tests for the {@link AccessService} class, which is responsible for providing access to a vending machine tracker.
 * The tests cover various scenarios, including healthy and unhealthy remote server states, and exception handling.
 *
 * <p>
 * <p>
 * The WireMock framework is used to simulate HTTP responses from a remote server and ensure the expected behavior of the AccessService.
 * For More info about the library see: <a href="https://github.com/tomakehurst/wiremock" target="_blank">WireMock repository</a>.
 *
 * <p>
 * <p>
 * Each test method in this class corresponds to a specific scenario and tests the behavior of the AccessService based on the provided conditions.
 *
 * <p>
 * <p>
 * The WireMockServer is used to start a mock HTTP server to simulate different server states and responses.
 *
 * <p>
 * <p>
 * The {@link #setUp()} method starts the WireMock server and sets up stubs for HTTP endpoints.
 * The {@link #tearDown()} method stops the WireMock server after each test.
 */
public class AccessServiceTest {


    private WireMockServer wireMockServer;

    /**
     * Sets up the environment before each test by starting the WireMock server and configuring stubs for HTTP endpoints.
     * This method is annotated with {@link BeforeEach}, ensuring that it runs before each test method in the test class.
     *
     * <p>
     * <p>
     * The WireMock server is started on port 8080, allowing it to simulate HTTP responses from a remote server.
     *
     * <p>
     * <p>
     * The stub for the health check endpoint is configured to return a 200 OK response, simulating a healthy server state.
     */
    @BeforeEach
    public void setup() {
        // Start the WireMock server
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8080));
        wireMockServer.start();

        // Setup the stub for the health check endpoint
        stubFor(get(urlEqualTo("/health"))
                .willReturn(aResponse()
                        .withStatus(200)));
    }

    /**
     * Tests the {@link AccessService} constructur to ensure that it initialize the {@link MachineTrackerAccessRemote} when the remote server is healthy.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>The remote server is healthy.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create an URI object for the remote server (mockedServerUri).</li>
     *   <li>Act: Initialize a new AccessService object with mockedServerUri as URI and an arbitrary string as a filename </li>
     *   <li>Assert: Assert that the AccessService initialices the {@link MachinetrackerAccessRemote} object</li>
     * </ol>
     */
    @Test
    public void AccessService_testAccessServiceWithHealthyServer() {
        //Arrange
        URI mockedServerUri = URI.create("http://localhost:8080/");

        //Act
        AccessService accessService = new AccessService(mockedServerUri, "FileNameNotMatter");

        //Assert
        assertTrue(accessService.getAccess() instanceof MachineTrackerAccessRemote, "Expected remote access");
    }

    /**
     * Tests the behavior of the AccessService when created with an unhealthy remote server.
     *
     * <p>
     * This test case focuses on the scenario where:
     * </p>
     * <ul>
     *   <li>The remote server is unhealthy (returns a 500 status code).</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up a stub for the health check endpoint to return a 500 status code (unhealthy server).</li>
     *   <li>Act: Initialize a new AccessService object with a URI for the mocked unhealthy server and an arbitrary filename.</li>
     *   <li>Assert: Assert that the AccessService falls back to using {@link MachineTrackerAccessLocal}, indicating local access when the server is unhealthy.</li>
     * </ol>
     */
    @Test
    public void AccessService_testAccessServiceWithUnhealthyServer() {

        //Arrange
        stubFor(get(urlEqualTo("/health"))
                .willReturn(aResponse()
                        .withStatus(500)));
        URI mockedServerUri = URI.create("http://localhost:8080/");

        //Act
        AccessService accessService = new AccessService(mockedServerUri, "FileNameNotMatter");

        //Assert
        assertTrue(accessService.getAccess() instanceof MachineTrackerAccessLocal, "Expected local access");
    }

    /**
     * Tests the behavior of the AccessService when it encounters an exception during server communication and falls back to using local access.
     *
     * <p>
     * This test case focuses on the scenario where:
     * </p>
     * <ul>
     *   <li>An exception occurs during server communication (malformed response chunk).</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up a stub for the health check endpoint to return a malformed response chunk.</li>
     *   <li>Act: Initialize a new AccessService object with a URI for the mocked server and an arbitrary filename.</li>
     *   <li> Assert: Assert that the AccessService falls back to using {@link MachineTrackerAccessLocal}, indicating local access after encountering an exception.</li>
     * </ol>
     */
    @Test
    public void AccessService_testAccessServiceThrowsExceptionAndFallsBackToLocalAccess() {

        //Arrange
        stubFor(get(urlEqualTo("/health"))
                .willReturn(aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
        URI mockedServerUri = URI.create("http://localhost:8080/");

        //Act
        AccessService accessService = new AccessService(mockedServerUri, "someFileName");

        //Assert
        assertTrue(accessService.getAccess() instanceof MachineTrackerAccessLocal, "Expected local access after exception");
    }

    /**
     * Performs cleanup after each test by stopping the WireMock server used for simulating HTTP responses.
     *
     * <p>
     * To clean up, this method stops the WireMock server to ensure that resources are released and the server is shut down.
     * </p>
     */
    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }
}
