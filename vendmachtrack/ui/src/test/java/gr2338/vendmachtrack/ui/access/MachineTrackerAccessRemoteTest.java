package gr2338.vendmachtrack.ui.access;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import gr2338.vendmachtrack.ui.access.MachineTrackerAccessRemote;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the {@link MachineTrackerAccessRemote}
 * class, which is responsible for providing remote access to a vending machine
 * tracker.
 * The tests cover various scenarios, including successful API responses and
 * error handling for remote access.
 *
 * <p>
 * <p>
 * The WireMock framework is used to simulate HTTP responses from a remote
 * server and ensure the expected behavior of the MachineTrackerAccessRemote
 * class.
 * To read more about this library see:
 * <a href="https://github.com/tomakehurst/wiremock" target="_blank">WireMock
 * repository</a>.
 *
 * <p>
 * <p>
 * Each test method in this class corresponds to a specific scenario and tests
 * the behavior of the MachineTrackerAccessRemote class based on the provided
 * conditions.
 *
 * <p>
 * <p>
 * The WireMockServer is used to start a mock HTTP server to simulate different
 * server states and responses .
 *
 * <p>
 * <p>
 * The {@link #setup()} method starts the WireMock server before each test, and
 * the {@link #tearDown()} method stops the WireMock server after each test.
 */
public class MachineTrackerAccessRemoteTest {

    private WireMockServer wireMockServer;

    /**
     * Sets up the environment before each test by starting the WireMock server and
     * configuring stubs for HTTP endpoints.
     * This method is annotated with {@link BeforeEach}, ensuring that it runs
     * before each test method in the test class.
     *
     * <p>
     * <p>
     * The WireMock server is started on port 8080, allowing it to simulate HTTP
     * responses from a remote server.
     */
    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8080));
        wireMockServer.start();
    }

    /**
     * Tests the {@link MachineTrackerAccessRemote#getVendMachList()} method to
     * ensure it returns a vending machine list when the remote server responds with
     * a 200 OK status.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>The remote server responds with a 200 OK status.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return a JSON representation of the
     * vending machine list with two entries (IDs 1 and 2) and corresponding
     * locations (Oslo and Trondheim).</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI, then invoke the getVendMachList() method.</li>
     * <li>Assert: Verify that the returned HashMap contains two entries with the
     * expected IDs and locations.</li>
     * </ol>
     *
     * @throws ConnectException
     */
    @Test
    public void MachineTrackerAccessRemote_getVendMachList_returnVendingMachineAllOk() throws ConnectException {

        // Arrange
        // Stubbing the API response to return 200 OK
        stubFor(get(urlEqualTo("/vendmachtrack"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"1\":\"Oslo\",\"2\":\"Trondheim\"}")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act
        HashMap<Integer, String> result = accessRemote.getVendMachList();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Oslo", result.get(1));
        assertEquals("Trondheim", result.get(2));
    }

    /**
     * Tests the {@link MachineTrackerAccessRemote#getVendMachList()} method to
     * ensure it handles a bad request response from the remote server correctly.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>The remote server responds with a 400 Bad Request status.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return a 400 Bad Request status code
     * with a JSON body containing an error message.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI, then invoke the getVendMachList() method.</li>
     * <li>Assert: Expect a {@link RuntimeException} to be thrown and verify that
     * the exception message contains "Bad Request".</li>
     * </ol>
     */
    @Test
    public void MachineTrackerAccessRemote_getVendMachList_badRequest() {

        // Arrange
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

    /**
     * Tests the {@link MachineTrackerAccessRemote#getVendMachLocation(int)} method
     * to ensure it returns the location of a vending machine for a valid ID.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>The remote server responds with a 200 OK status, and the location is
     * successfully retrieved.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return 200 OK with the location "Oslo"
     * for a specific vending machine ID.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI, then invoke the getVendMachLocation() method with a valid
     * ID.</li>
     * <li>Assert: Verify that the returned location matches the expected value
     * "Oslo".</li>
     * </ol>
     *
     * @throws ConnectException
     */
    @Test
    public void MachineTrackerAccessRemote_getVendMachLocation_returnLocationAllOk() throws ConnectException {

        // Arrange
        int id = 1;
        // Stubbing the API response to return 200 OK
        stubFor(get(urlEqualTo("/vendmachtrack/" + id + "/name"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("\"Oslo\"")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act
        String result = accessRemote.getVendMachLocation(id);

        // Assert
        assertEquals("Oslo", result);
    }

    /**
     * Tests the {@link MachineTrackerAccessRemote#getVendMachLocation(int)} method
     * when the server returns a Bad Request (400) status code.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>The remote server responds with a 400 Bad Request status.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return a 400 Bad Request status code
     * with a JSON body containing an error message.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI, then invoke the getVendMachLocation() method with the ID.</li>
     * <li>Assert: Expect a {@link RuntimeException} to be thrown and verify that
     * the exception message contains "Bad Request".</li>
     * </ol>
     */
    @Test
    public void MachineTrackerAccessRemote_getVendMachLocation_ServerReturnsBadRequest() {

        // Arrange
        int id = 1;
        // Stubbing the API response to return 400 Bad Request
        stubFor(get(urlEqualTo("/vendmachtrack/" + id + "/name"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody("{\"error\":\"Bad Request\"}")));

        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act & Assert
        // Expecting a RuntimeException to be thrown due to the checkError method in the
        // class
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accessRemote.getVendMachLocation(id);
        });
        assertTrue(exception.getMessage().contains("Bad Request"));
    }

    /**
     * Tests the {@link MachineTrackerAccessRemote#getInventory(int)} method when
     * the server returns a successful 200 OK status code.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>The remote server responds with a 200 OK status.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return 200 OK with an inventory JSON
     * response for a specific vending machine ID.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI, then invoke the getInventory() method with the ID.</li>
     * <li>Assert: Verify that the returned inventory map contains the expected
     * items and quantities.</li>
     * </ol>
     *
     * @throws ConnectException
     */
    @Test
    public void MachineTrackerAccessRemote_getInventory_returnsInventoryAllOk() throws ConnectException {

        // Arrange
        int id = 1;
        // Stubbing the API response to return 200 OK
        stubFor(get(urlEqualTo("/vendmachtrack/" + id))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"Cola\":10,\"Øl\":20}")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act
        HashMap<String, Integer> result = accessRemote.getInventory(id);

        // Assert
        assertEquals(2, result.size());
        assertEquals(10, result.get("Cola").intValue());
        assertEquals(20, result.get("Øl").intValue());
    }

    /**
     * Tests the {@link MachineTrackerAccessRemote#getInventory(int)} method when
     * the server returns a 400 Bad Request status code.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>The remote server responds with a 400 Bad Request status.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return a 400 Bad Request status code
     * with a JSON body containing an error message.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI, then invoke the getInventory() method with the ID.</li>
     * <li>Assert: Expect a {@link RuntimeException} to be thrown and verify that
     * the exception message contains "Bad Request".</li>
     * </ol>
     */
    @Test
    public void MachineTrackerAccessRemote_getInventory_ServerReturnsBadRequest() {

        // Arrange
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

    /**
     * Tests the {@link MachineTrackerAccessRemote#addItem(int, String, int)} method
     * when adding an item is successful.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>Adding an item to the vending machine is successful, and the remote
     * server responds with a 200 OK status code.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return 200 OK with a JSON body
     * indicating the added item and its quantity.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI and invoke the addItem() method with the specified
     * parameters.</li>
     * <li>Assert: Verify that the result contains the expected item and
     * quantity.</li>
     * </ol>
     *
     * @throws ConnectException
     */
    @Test
    public void MachineTrackerAccessRemote_addItem_addsItemAllOk() throws ConnectException {

        // Arrange
        int id = 1;
        String item = "Cola";
        int quantity = 5;
        // Stubbing the API response to return 200 OK
        stubFor(put(urlEqualTo("/vendmachtrack/" + id + "/add?item=" + URLEncoder.encode(item, StandardCharsets.UTF_8)
                + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"Cola\":5}")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act
        HashMap<String, Integer> result = accessRemote.addItem(id, item, quantity);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.containsKey("Cola"));
        assertEquals(5, result.get("Cola").intValue());
    }

    /**
     * Tests the {@link MachineTrackerAccessRemote#addItem(int, String, int)} method
     * when the remote server returns a Bad Request (400) response.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>Adding an item to the vending machine results in a Bad Request response
     * from the remote server (status code 400).</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return a 400 Bad Request status code
     * with a JSON body containing an error message.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI and invoke the addItem() method with the specified
     * parameters.</li>
     * <li>Assert: Expect a {@link RuntimeException} to be thrown and verify that
     * the exception message contains "Bad Request".</li>
     * </ol>
     */
    @Test
    public void MachineTrackerAccessRemote_addItem_ServerReturnsBadRequest() {

        // Arrange
        int id = 1;
        String item = "Cola";
        int quantity = 5;
        // Stubbing the API response to return 400 Bad Request
        stubFor(put(urlEqualTo("/vendmachtrack/" + id + "/add?item=" + URLEncoder.encode(item, StandardCharsets.UTF_8)
                + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody("{\"error\":\"Bad Request\"}")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accessRemote.addItem(id, item, quantity);
        });
        assertTrue(exception.getMessage().contains("Bad Request"));
    }

    /**
     * Tests the {@link MachineTrackerAccessRemote#removeItem(int, String, int)}
     * method when successfully removing items from the vending machine.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>Removing items from the vending machine results in a successful
     * operation, and the server responds with a 200 OK status code.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return a 200 OK status with a JSON body
     * indicating the updated quantity of the removed item.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI and invoke the removeItem() method with the specified
     * parameters.</li>
     * <li>Assert: Verify that the returned HashMap contains the expected item and
     * its updated quantity, as well as the size of the HashMap after removal.</li>
     * </ol>
     *
     * @throws ConnectException
     */
    @Test
    public void MachineTrackerAccessRemote_removeItem_removesItemAllOk() throws ConnectException {

        // Arrange
        int id = 1;
        String item = "Cola";
        int quantity = 3;
        // Stubbing the API response to return 200 OK
        stubFor(put(urlEqualTo("/vendmachtrack/" + id + "/remove?item="
                + URLEncoder.encode(item, StandardCharsets.UTF_8) + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"Cola\":2}"))); // Assuming that there were originally 5"Colas and 3 were removed
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act
        HashMap<String, Integer> result = accessRemote.removeItem(id, item, quantity);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.containsKey("Cola"));
        assertEquals(2, result.get("Cola").intValue()); // Expecting 2 remaining after removal
    }

    /**
     * Tests the {@link MachineTrackerAccessRemote#removeItem(int, String, int)}
     * method when the server responds with a bad request (400 status code).
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>Removing items from the vending machine results in a bad request (400
     * status code) response from the server.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return a 400 Bad Request status code
     * with a JSON body containing an error message.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI and invoke the removeItem() method with the specified
     * parameters.</li>
     * <li>Assert: Expect a {@link RuntimeException} to be thrown and verify that
     * the exception message contains "Bad Request".</li>
     * </ol>
     */
    @Test
    public void MachineTrackerAccessRemote_removeItem_ServerReturnsBadRequest() {

        // Arrange
        int id = 1;
        String item = "Cola";
        int quantity = 3;

        // Stubbing the API response to return 400 Bad Request
        stubFor(put(urlEqualTo("/vendmachtrack/" + id + "/remove?item="
                + URLEncoder.encode(item, StandardCharsets.UTF_8) + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody("{\"error\":\"Bad Request\"}")));

        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accessRemote.removeItem(id, item, quantity);
        });
        assertTrue(exception.getMessage().contains("Bad Request"));
    }

    /**
     * Tests the {@link MachineTrackerAccessRemote#addVendMach(int, String)} method
     * when adding a vending machine returns a successful response (200 status
     * code).
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>Adding a vending machine to the remote server results in a successful
     * response (200 status code) with the updated vending machine details.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return a 200 OK status with a JSON body
     * containing the updated vending machine details.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI and invoke the addVendMach() method with the specified
     * parameters.</li>
     * <li>Assert: Verify that the result contains the expected vending machine
     * details, including the ID and location.</li>
     * </ol>
     *
     * @throws ConnectException
     */
    @Test
    public void MachineTrackerAccessRemote_addVendMach_RetrunsVendMachAllOk() throws ConnectException {

        // Arrange
        int id = 1;
        String location = "Oslo";

        // Stubbing the API response to return 200 OK
        stubFor(post(urlEqualTo(
                "/vendmachtrack/add?id=" + id + "&location=" + URLEncoder.encode(location, StandardCharsets.UTF_8)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"1\":\"Oslo\"}")));
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act
        HashMap<Integer, String> result = accessRemote.addVendMach(id, location);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.containsKey(1));
        assertEquals("Oslo", result.get(1));
    }

    /**
     * Tests the {@link MachineTrackerAccessRemote#addVendMach(int, String)} method
     * when adding a vending machine returns a BadRequest response (400 status
     * code).
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>Adding a vending machine to the remote server results in a BadRequest
     * response (400 status code) with an error message.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return a 400 Bad Request status with a
     * JSON body containing an error message.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI and invoke the addVendMach() method with the specified
     * parameters.</li>
     * <li>Assert: Expect a {@link RuntimeException} to be thrown and verify that
     * the exception message contains "Bad Request".</li>
     * </ol>
     */
    @Test
    public void MachineTrackerAccessRemote_addVendMach_ServerReturnsBadRequest() {

        // Arrange
        int id = 1;
        String location = "Oslo";
        // Stubbing the API response to return 400 Bad Request
        stubFor(post(urlEqualTo(
                "/vendmachtrack/add?id=" + id + "&location=" + URLEncoder.encode(location, StandardCharsets.UTF_8)))
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

    /**
     * Tests the {@link MachineTrackerAccessRemote#removeVendMach(int)} method when
     * removing a vending machine returns a successful response (200 status code).
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>Removing a vending machine from the remote server results in a successful
     * response (200 status code) with the updated vending machine list.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return a 200 OK status with a JSON body
     * containing the updated vending machine list.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI and invoke the removeVendMach() method with the specified vending
     * machine ID.</li>
     * <li>Assert: Verify that the result contains the expected updated vending
     * machine list with its ID and location.</li>
     * </ol>
     *
     * @throws ConnectException
     */
    @Test
    public void MachineTrackerAccessRemote_removeVendMach_returnsRemovedVendMachAllOk() throws ConnectException {

        // Arrange
        int id = 1;
        // Stubbing the API response to return 200 OK
        stubFor(delete(urlEqualTo("/vendmachtrack/" + id))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"2\":\"Stockholm\"}"))); // Assuming that after removing the machine with ID 1, we
        // have one with ID 2 in Stockholm
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act
        HashMap<Integer, String> result = accessRemote.removeVendMach(id);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.containsKey(2));
        assertEquals("Stockholm", result.get(2));
    }

    /**
     * Tests the {@link MachineTrackerAccessRemote#removeVendMach(int)} method when
     * removing a vending machine results in a bad request response (400 status
     * code).
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>Removing a vending machine from the remote server results in a bad
     * request response (400 status code) with an error message.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return a 400 Bad Request status code
     * with a JSON body containing an error message.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI and invoke the removeVendMach() method with the specified vending
     * machine ID.</li>
     * <li>Assert: Expect a {@link RuntimeException} to be thrown and verify that
     * the exception message contains "Bad Request".</li>
     * </ol>
     */
    @Test
    public void MachineTrackerAccessRemote_removeVendMach_ServerReturnsBadRequest() {

        // Arrange
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

    /**
     * Tests the {@link MachineTrackerAccessRemote#changeLocation(int, String)}
     * method when changing the location of a vending machine is successful (200
     * status code).
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>Changing the location of a vending machine on the remote server is
     * successful, resulting in a 200 OK status code with the updated location.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return a 200 OK status with a JSON body
     * containing the updated location.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI and invoke the changeLocation() method with the specified vending
     * machine ID and new location.</li>
     * <li>Assert: Verify that the returned result is a map containing the updated
     * location, and it matches the expected values.</li>
     * </ol>
     *
     * @throws ConnectException
     */
    @Test
    public void MachineTrackerAccessRemote_changeLocation_returnsUpdatedLocationAllOk() throws ConnectException {

        // Arrange
        int id = 1;
        String newLocation = "Bergen";
        // Stubbing the API response to return 200 OK
        stubFor(put(urlEqualTo(
                "/vendmachtrack/" + id + "?location=" + URLEncoder.encode(newLocation, StandardCharsets.UTF_8)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"1\":\"Bergen\"}"))); // Assuming that after updating the location of the machine
        // with ID 1, its location is Bergen
        MachineTrackerAccessRemote accessRemote = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

        // Act
        HashMap<Integer, String> result = accessRemote.changeLocation(id, newLocation);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.containsKey(1));
        assertEquals("Bergen", result.get(1));
    }

    /**
     * Tests the {@link MachineTrackerAccessRemote#changeLocation(int, String)}
     * method when changing the location of a vending machine results in a server
     * error (400 Bad Request).
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     * <li>Changing the location of a vending machine on the remote server results
     * in a server error with a 400 Bad Request status code.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     * <li>Arrange: Stub the API response to return a 400 Bad Request status code
     * with a JSON body containing an error message.</li>
     * <li>Act: Initialize a new MachineTrackerAccessRemote object with a mocked
     * server URI and invoke the changeLocation() method with the specified vending
     * machine ID and new location.</li>
     * <li>Assert: Expect a {@link RuntimeException} to be thrown and verify that
     * the exception message contains "Bad Request".</li>
     * </ol>
     */
    @Test
    public void MachineTrackerAccessRemote_changeLocation_ServerReturnsBadRequest() {

        // Arrange
        int id = 1;
        String newLocation = "Bergen";
        // Stubbing the API response to return 400 Bad Request
        stubFor(put(urlEqualTo(
                "/vendmachtrack/" + id + "?location=" + URLEncoder.encode(newLocation, StandardCharsets.UTF_8)))
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

    /**
     * After each test case, this method is responsible for stopping the WireMock
     * server.
     *
     * <p>
     * This method ensures that the WireMock server, which was started before each
     * test case in the {@link BeforeEach} setup, is properly stoppe
     * </p>
     */
    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }
}
