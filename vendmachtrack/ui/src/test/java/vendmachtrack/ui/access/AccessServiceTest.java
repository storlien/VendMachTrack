package vendmachtrack.ui.access;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.Fault;

public class AccessServiceTest {

    private WireMockServer wireMockServer;

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

    @Test
    public void AccessService_testAccessServiceWithHealthyServer() {
        //Arrange
        URI mockedServerUri = URI.create("http://localhost:8080/");
    
        //Act
        AccessService accessService = new AccessService(mockedServerUri, "FileNameNotMatter");

        //Assert
        assertTrue(accessService.getAccess() instanceof MachineTrackerAccessRemote, "Expected remote access");
    }

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
    
    @AfterEach
    public void tearDown() {
        // Stop the WireMock server
        wireMockServer.stop();
    }
}
