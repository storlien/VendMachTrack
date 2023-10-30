package vendmachtrack.ui.access;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class MachineTrackerAccessRemoteTest {

    private WireMockServer wireMockServer;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8080));
        wireMockServer.start();
    }

    @Test
    public void MachineTrackerAccessRemote_getVendMachList_returnVendingMachineAllOk() {
        
        //Arrange
        // Stubbing the API response to return 200 OK
        stubFor(get(urlEqualTo("/vendmachtrack"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"1\":\"Oslo\",\"2\":\"Trondheim\"}")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));
        
        //Act
        HashMap<Integer, String> result = accessRemote.getVendMachList();

        //Assert
        assertEquals(2, result.size());
        assertEquals("Oslo", result.get(1));
        assertEquals("Trondheim", result.get(2));
    }

    @Test
    public void MachineTrackerAccessRemote_getVendMachList_badRequest() {
    
    //Arrange
    // Stubbing the API response to return 400 Bad Request
        stubFor(get(urlEqualTo("/vendmachtrack"))
                .willReturn(aResponse()
                            .withStatus(400)
                            .withBody("{\"error\":\"Bad Request\"}")));

        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));
    
        // Act & Assert
        // Expecting a RuntimeException to be thrown
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accessRemote.getVendMachList();
        });
        assertTrue(exception.getMessage().contains("Bad Request"));
    }

    @Test
    public void MachineTrackerAccessRemote_getVendMachLocation_returnLocationAllOk() {
        
        //Arrange
        int id = 1;
        // Stubbing the API response to return 200 OK
        stubFor(get(urlEqualTo("/vendmachtrack/" + id + "/name"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("\"Oslo\"")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));
        
        //Act
        String result = accessRemote.getVendMachLocation(id);

        //Assert
        assertEquals("Oslo", result);
    }

    @Test
    public void MachineTrackerAccessRemote_getVendMachLocation_ServerReturnsBadRequest() {
        
        //Arrange
        int id = 1;
        // Stubbing the API response to return 400 Bad Request
        stubFor(get(urlEqualTo("/vendmachtrack/" + id + "/name"))
                .willReturn(aResponse()
                            .withStatus(400)
                            .withBody("{\"error\":\"Bad Request\"}")));

        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act & Assert
        // Expecting a RuntimeException to be thrown due to the checkError method in the class
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accessRemote.getVendMachLocation(id);
        });
        assertTrue(exception.getMessage().contains("Bad Request"));
    }


    @Test
    public void MachineTrackerAccessRemote_getInventory_returnsInventoryAllOk() {
        
        //Arrange
        int id = 1;
        // Stubbing the API response to return 200 OK 
        stubFor(get(urlEqualTo("/vendmachtrack/" + id))
                .willReturn(aResponse()
                            .withStatus(200)
                            .withBody("{\"Cola\":10,\"Øl\":20}")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));
        
        //Act
        HashMap<String, Integer> result = accessRemote.getInventory(id);

        //Assert
        assertEquals(2, result.size());
        assertEquals(10, result.get("Cola").intValue());
        assertEquals(20, result.get("Øl").intValue());
    }

    @Test
    public void MachineTrackerAccessRemote_getInventory_ServerReturnsBadRequest() {
        
        //Arrange
        int id = 1;
        // Stubbing the API response to return 400 Bad Request
        stubFor(get(urlEqualTo("/vendmachtrack/" + id))
                .willReturn(aResponse()
                            .withStatus(400)
                            .withBody("{\"error\":\"Bad Request\"}")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act & Assert
        // Expecting a RuntimeException to be thrown
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accessRemote.getInventory(id);
        });
        assertTrue(exception.getMessage().contains("Bad Request"));
    }


    @Test
    public void MachineTrackerAccessRemote_addItem_addsItemAllOk() {
        
        //Arrange
        int id = 1;
        String item = "Cola";
        int amount = 5;
        // Stubbing the API response to return 200 OK
        stubFor(put(urlEqualTo("/vendmachtrack/" + id + "/add?item=" + URLEncoder.encode(item, StandardCharsets.UTF_8) + "&amount=" + amount))
                .willReturn(aResponse()
                            .withStatus(200)
                            .withBody("{\"Cola\":5}")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));
        
        //Act
        HashMap<String, Integer> result = accessRemote.addItem(id, item, amount);

        //Assert
        assertEquals(1, result.size());
        assertTrue(result.containsKey("Cola"));
        assertEquals(5, result.get("Cola").intValue());
    }

    @Test
    public void MachineTrackerAccessRemote_addItem_ServerReturnsBadRequest() {
        
        //Arrange
        int id = 1;
        String item = "Cola";
        int amount = 5;
        // Stubbing the API response to return 400 Bad Request
        stubFor(put(urlEqualTo("/vendmachtrack/" + id + "/add?item=" + URLEncoder.encode(item, StandardCharsets.UTF_8) + "&amount=" + amount))
                .willReturn(aResponse()
                            .withStatus(400)
                            .withBody("{\"error\":\"Bad Request\"}")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accessRemote.addItem(id, item, amount);
        });
        assertTrue(exception.getMessage().contains("Bad Request"));
    }


    @Test
    public void MachineTrackerAccessRemote_removeItem_removesItemAllOk() {
        
        //Arrange
        int id = 1;
        String item = "Cola";
        int amount = 3;
        // Stubbing the API response to return 200 OK
        stubFor(put(urlEqualTo("/vendmachtrack/" + id + "/remove?item=" + URLEncoder.encode(item, StandardCharsets.UTF_8) + "&amount=" + amount))
                .willReturn(aResponse()
                            .withStatus(200)
                            .withBody("{\"Cola\":2}")));  // Assuming that there were originally 5"Colas and 3 were removed
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));
        
        //Act
        HashMap<String, Integer> result = accessRemote.removeItem(id, item, amount);

        //Assert
        assertEquals(1, result.size());
        assertTrue(result.containsKey("Cola"));
        assertEquals(2, result.get("Cola").intValue()); // Expecting 2 remaining after removal
    }

    @Test
    public void MachineTrackerAccessRemote_removeItem_ServerReturnsBadRequest() {
        
        //Arrange
        int id = 1;
        String item = "Cola";
        int amount = 3;

        // Stubbing the API response to return 400 Bad Request
        stubFor(put(urlEqualTo("/vendmachtrack/" + id + "/remove?item=" + URLEncoder.encode(item, StandardCharsets.UTF_8) + "&amount=" + amount))
                .willReturn(aResponse()
                            .withStatus(400)
                            .withBody("{\"error\":\"Bad Request\"}")));

        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accessRemote.removeItem(id, item, amount);
        });
        assertTrue(exception.getMessage().contains("Bad Request"));
    }

    @Test
    public void MachineTrackerAccessRemote_addVendMach_RetrunsVendMachAllOk() {
        
        //Arrange
        int id = 1;
        String location = "Oslo";

        // Stubbing the API response to return 200 OK
        stubFor(post(urlEqualTo("/vendmachtrack/add?id=" + id + "&location=" + URLEncoder.encode(location, StandardCharsets.UTF_8)))
                .willReturn(aResponse()
                            .withStatus(200)
                            .withBody("{\"1\":\"Oslo\"}")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));
        
        //Act
        HashMap<Integer, String> result = accessRemote.addVendMach(id, location);

        //Assert
        assertEquals(1, result.size());
        assertTrue(result.containsKey(1));
        assertEquals("Oslo", result.get(1));
    }

    @Test
    public void MachineTrackerAccessRemote_addVendMach_ServerReturnsBadRequest() {
       
        //Arrange
        int id = 1;
        String location = "Oslo";
        // Stubbing the API response to return 400 Bad Request
        stubFor(post(urlEqualTo("/vendmachtrack/add?id=" + id + "&location=" + URLEncoder.encode(location, StandardCharsets.UTF_8)))
                .willReturn(aResponse()
                            .withStatus(400)
                            .withBody("{\"error\":\"Bad Request\"}")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accessRemote.addVendMach(id, location);
        });
        assertTrue(exception.getMessage().contains("Bad Request"));
    }

    @Test
    public void MachineTrackerAccessRemote_removeVendMach_returnsRemovedVendMachAllOk() {
        
        //Arrange
        int id = 1;
        // Stubbing the API response to return 200 OK
        stubFor(delete(urlEqualTo("/vendmachtrack/" + id))
                .willReturn(aResponse()
                            .withStatus(200)
                            .withBody("{\"2\":\"Stockholm\"}")));  // Assuming that after removing the machine with ID 1, we have one with ID 2 in Stockholm
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));
        
        //Act
        HashMap<Integer, String> result = accessRemote.removeVendMach(id);

        //Assert
        assertEquals(1, result.size());
        assertTrue(result.containsKey(2));
        assertEquals("Stockholm", result.get(2));
    }


    @Test
    public void MachineTrackerAccessRemote_removeVendMach_ServerReturnsBadRequest() {
        
        //Arrange
        int id = 1;
        // Stubbing the API response to return 400 Bad Request
        stubFor(delete(urlEqualTo("/vendmachtrack/" + id))
                .willReturn(aResponse()
                            .withStatus(400)
                            .withBody("{\"error\":\"Bad Request\"}")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accessRemote.removeVendMach(id);
        });
        assertTrue(exception.getMessage().contains("Bad Request"));
    }
    @Test
    public void MachineTrackerAccessRemote_changeLocation_returnsUpdatedLocationAllOk() {
        
        // Arrange
        int id = 1;
        String newLocation = "Bergen";
        // Stubbing the API response to return 200 OK
        stubFor(put(urlEqualTo("/vendmachtrack/" + id + "?location=" + URLEncoder.encode(newLocation, StandardCharsets.UTF_8)))
                .willReturn(aResponse()
                            .withStatus(200)
                            .withBody("{\"1\":\"Bergen\"}")));  // Assuming that after updating the location of the machine with ID 1, its location is Bergen
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));
        
        // Act
        HashMap<Integer, String> result = accessRemote.changeLocation(id, newLocation);
    
        // Assert
        assertEquals(1, result.size());
        assertTrue(result.containsKey(1));
        assertEquals("Bergen", result.get(1));
    }
    

    @Test
    public void MachineTrackerAccessRemote_changeLocation_ServerReturnsBadRequest() {
        
        // Arrange
        int id = 1;
        String newLocation = "Bergen";
        // Stubbing the API response to return 400 Bad Request
        stubFor(put(urlEqualTo("/vendmachtrack/" + id + "?location=" + URLEncoder.encode(newLocation, StandardCharsets.UTF_8)))
                .willReturn(aResponse()
                            .withStatus(400)
                            .withBody("{\"error\":\"Bad Request\"}")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accessRemote.changeLocation(id, newLocation);
        });
        assertTrue(exception.getMessage().contains("Bad Request"));
    }

















    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }
}
