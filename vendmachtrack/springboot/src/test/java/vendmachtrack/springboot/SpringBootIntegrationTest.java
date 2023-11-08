package vendmachtrack.springboot;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Integration tests for a the springboot module.
 * 
 * This class contains integration tests that validate the behavior of our Spring Boot application with REST API endpoints. The tests cover various aspects of the application, including HTTP requests and responses.
 * 
 * <p>
 * The class is annotated with Spring Boot annotations to configure the test environment:
 * </p>
 * <ul>
 *   <li>{@link ExtendWith(SpringExtension.class)}: Extends JUnit 5 with Spring support.</li>
 *   <li>{@link SpringBootTest}: Configures the test environment with a random port and starts the Spring application context.</li>
 *   <li>{@link TestMethodOrder(MethodOrderer.OrderAnnotation.class)}: Orders test methods based on annotated order.</li>
 * </ul>
 * 
 * <p>
 * The class includes instance variables for the server port and a TestRestTemplate for making HTTP requests to the application.
 * </p>
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpringBootIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    /**
     * Tests adding a new vending machine via a REST API endpoint.
     * 
     * <p>
     * This test verifies that a new vending machine can be successfully added by making a POST request
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the base URL, ID, and location for the new vending machine.</li>
     *   <li>Act: Make a POST request to add the new vending machine.</li>
     *   <li>Assert: Check that the response status code is HttpStatus.OK (200 OK) and the response body contains the added vending machine with the expected ID and location.</li>
     * </ol>
     */
    @Test
    @Order(1)
    public void testAddVendMach() {
        
        // Arrange
        String baseUrl = "http://localhost:" + port;
        String newMachineId = "100";  
        String newLocation = "Test Location"; 

        // Act
        ResponseEntity<HashMap<Integer, String>> response = restTemplate.exchange(
        baseUrl + "/vendmachtrack/add?id=" + newMachineId + "&location=" + newLocation,
        HttpMethod.POST,
        null,
        new ParameterizedTypeReference<HashMap<Integer, String>>() {}
    );

        // Assert
        assertThat(response.getStatusCode()).as("Response body: " + response.getBody())
                                            .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get(Integer.parseInt(newMachineId))).isEqualTo(newLocation);
    }

    /**
     * Tests if we can get the location of an existing vending machine.
     * 
     * <p>
     * 
     * This test checks if we can successfully find out where a vending machine is by making a GET request
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: prepare the necessary information; the service's URL and the ID of an existing vending machine.</li>
     *   <li>Act: Make a GET request to get the vending machine's location.</li>
     *   <li>Assert: Check that we receive a positive response (HTTP 200 OK) and that the location we receive matches the expected location ("Test Location").</li>
     * </ol>
     */
    @Test
    @Order(2)
    public void testGetVendMachLocation() {
        
        // Arrange
        String baseUrl = "http://localhost:" + port;
        int existingMachineId = 100;
        // Act
        ResponseEntity<String> response = restTemplate.getForEntity(
            baseUrl + "/vendmachtrack/" + existingMachineId + "/name",
            String.class
        );

        // Assert
        assertThat(response.getStatusCode()).as("Response body: " + response.getBody())
                                            .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        String location = response.getBody();
        assertThat(location).isEqualTo("Test Location");
    }

    /**
     * Tests adding an item to an existing vending machine.
     * 
     * <p>
     * 
     * This test checks if we can successfully put an item into a vending machine and verify that it has the correct quantity using a PUT request
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Prepare the necessary information; service's URL, vending machine ID, item name ("Cola") and quantity (5).</li>
     *   <li>Act: Make a PUT request to add the item to the vending machine.</li>
     *   <li>Assert: Check that we receive a positive response (HTTP 200 OK), and that the vending machine now contains the added item with the expected quantity.</li>
     * </ol>
     */
    @Test
    @Order(3)
    public void testAddItem() {
    
        // Arrange
        String baseUrl = "http://localhost:" + port;
        int machineId = 100; 
        String item = "Cola"; 
        int quantity = 5; 

        // Act
        ResponseEntity<HashMap<String, Integer>> response = restTemplate.exchange(
            baseUrl + "/vendmachtrack/" + machineId + "/add?item=" + item + "&quantity=" + quantity,
            HttpMethod.PUT,
            null, 
            new ParameterizedTypeReference<HashMap<String, Integer>>() {}
        );


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey(item);
        assertThat(response.getBody().get(item)).isEqualTo(quantity);
    }

    /**
     * Tests getting the quantity of a specific item from an existing vending machine.
     * 
     * <p>
     * 
     * This test checks if we can successfully get the quantity of a specific item ("Cola")
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Prepare the necessary information; service's URL, vending machine ID, item name ("Cola") and quantity (5).</li>
     *   <li>Act: Make a GET request to the vending machine's inventory.</li>
     *   <li>Assert: Check that the response contains the expected item name and quantity ("Cola" with a quantity of 5).</li>
     * </ol>
     */
    @Test
    @Order(4)
    public void testGetItem(){

        String baseUrl = "http://localhost:" + port;
        int machineId = 100; 
        String item = "Cola"; 
        int quantity = 5; 

        ResponseEntity<HashMap<String, Integer>> inventoryResponse = restTemplate.exchange(
            baseUrl + "/vendmachtrack/" + machineId,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<HashMap<String, Integer>>() {}
        );
        assertThat(inventoryResponse.getBody()).containsEntry(item, quantity);
    }

    /**
     * Tests removing a specific quantity of an item from a vending machine.
     * 
     * <p>
     * 
     * This test checks if we can successfully take a specific quantity of an item (e.g., "Cola") from a vending machine and verify the remaining quantity.
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Prepare the necessary information; service's URL, vending machine ID, item name ("Cola") and quantity (3).</li>
     *   <li>Act: Make a PUT request to remove the quantity of the item from the vending machine.</li>
     *   <li>Assert: Check that the response contains the expected item name and quantity</li>
     * </ol>
     */
    @Test
    @Order(5)
    public void testRemoveItem() {
        
        // Arrange
        String baseUrl = "http://localhost:" + port;
        int machineId = 100; 
        String item = "Cola"; 
        int quantityToRemove = 3; 

        // Act 
          ResponseEntity<HashMap<String, Integer>> response = restTemplate.exchange(
        baseUrl + "/vendmachtrack/" + machineId + "/remove?item=" + item + "&quantity=" + quantityToRemove,
        HttpMethod.PUT,
        null,
        new ParameterizedTypeReference<HashMap<String, Integer>>() {}
    );

        // Assert 
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey(item);
        int remainingQuantity = 2;
        assertThat(remainingQuantity).isEqualTo(5 - quantityToRemove);
    }

    /**
     * Tests verifying the removal of a specific quantity of an item from an existing vending machine.
     * 
     * <p>
     * 
     * This test checks if we can successfully confirm that a specific quantity of an item (e.g., "Cola") has been removed from a vending machine and that the remaining quantity matches the expected value.
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Prepare the necessary information; service's URL, vending machine ID, item name ("Cola") and quantity (3).</li>
     *   <li>Act: Make a GET request the vending machine's inventory after the removal.</li>
     *   <li>Assert: Check that the response contains the expected item name and the remaining quantity matches the expected value</li>
     * </ol>
     */
    @Test
    @Order(6)
    public void testremovedItem(){

         // Arrange
        String baseUrl = "http://localhost:" + port;
        int machineId = 100; 
        String item = "Cola"; 
        int quantityToRemove = 3; 

        //Act
        ResponseEntity<HashMap<String, Integer>> inventoryResponse = restTemplate.exchange(
        baseUrl + "/vendmachtrack/" + machineId,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<HashMap<String, Integer>>() {}
        );

        //Assert
        assertThat(inventoryResponse.getBody()).containsEntry(item, 5 - quantityToRemove);
    }


    /**
     * Tests removing a vending machine
     * 
     * <p>
     * 
     * This test checks if we can successfully remove a vending machine by making a DELETE request.
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Prepare the necessary information; the service's URL and the ID of the vending machine to remove.</li>
     *   <li>Act: Make a DELETE request to remove the vending machine.</li>
     *   <li>Assert: Check that the response indicates success (HTTP 200 OK), the response body is not null, and the removed vending machine's ID is no longer present in the response.</li>
     * </ol>
     */
    @Test
    @Order(7)
    public void testRemoveVendMach() {
        // Arrange
        String baseUrl = "http://localhost:" + port;
        String machineIdToRemove = "100"; 

        // Act 
        ResponseEntity<HashMap<String, String>> removeResponse = restTemplate.exchange(
            baseUrl + "/vendmachtrack/" + machineIdToRemove,
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<HashMap<String, String>>() {}
        );

        // Assert 
        assertThat(removeResponse.getStatusCode()).as("Response body: " + removeResponse.getBody())
                                                .isEqualTo(HttpStatus.OK);
        assertThat(removeResponse.getBody()).isNotNull();
        assertThat(removeResponse.getBody()).doesNotContainKey(machineIdToRemove);
    }

    /**
     * Tests verifying the removal of a vending machine.
     * 
     * <p>
     * 
     * This test checks if we can successfully confirm that a vending machine has been removed by making a GET request to the specified endpoint and verifying that it's no longer found (HTTP 404 NOT FOUND).
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Prepare the necessary information; service's URL and the ID of the vending machine that was previously removed.</li>
     *   <li>Act: Make a GET request to check if the removed vending machine is no longer present.</li>
     *   <li>Assert: Check that the response status code indicates that the vending machine is not found (HTTP 404 NOT FOUND).</li>
     * </ol>
     */
    @Test
    @Order(8)
    public void testVendMachRemoved(){
       
        // Arrange
        String baseUrl = "http://localhost:" + port;
        String machineIdToRemove = "100"; 

         // Act
        ResponseEntity<String> getResponse = restTemplate.getForEntity(
        baseUrl + "/vendmachtrack/" + machineIdToRemove,
        String.class
        );

        // Assert
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}






