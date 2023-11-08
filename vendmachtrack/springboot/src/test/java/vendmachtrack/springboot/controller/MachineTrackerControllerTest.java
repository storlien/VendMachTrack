package vendmachtrack.springboot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vendmachtrack.core.model.VendingMachine;
import vendmachtrack.springboot.exception.IllegalInputException;
import vendmachtrack.springboot.exception.ResourceNotFoundException;
import vendmachtrack.springboot.service.MachineTrackerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Test class for the {@link MachineTrackerController} using Spring's WebMvcTest.
 * 
 * <p>
 * This test class contains a suite of test cases that verify the behavior of the {@link MachineTrackerController} by mocking HTTP requests and responses. The tests cover various methods and scenarios, including adding and removing vending machines, changing machine locations, and handling exceptions like {@link ResourceNotFoundException} and {@link IllegalInputException}.
 * </p>
 * 
 * <p>
 * To conduct these tests, Spring's {@link WebMvcTest} annotation is used to focus on testing the controller layer in isolation. Various mock configurations are applied to test different scenarios.
 * </p>
 */
@WebMvcTest(controllers = MachineTrackerController.class)
public class MachineTrackerControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private MachineTrackerService machineTrackerService;

    private final VendingMachine vendingmachine = new VendingMachine();
    private final List<VendingMachine> machines = new ArrayList<>();

    /**
     * Sets up the test fixture before each test method runs.
     * Initializes the vending machine with an ID of 1 and a location of "Oslo",
     * and adds it to the list of machines.
     */
    @BeforeEach
    public void setUp() {
        vendingmachine.setId(1);
        vendingmachine.setLocation("Oslo");
        machines.add(vendingmachine);
    }

    /**
     * Tests the behavior of the {@link MachineTrackerController#getVendMachList()} method in the {@link MachineTrackerController} class.
     * 
     * This test verifies that the {@link MachineTrackerController#getVendMachList()} method returns a JSON object containing the list of vending machines and ensures that the HTTP response status is 200 (OK).
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up a mock response containing a HashMap of vending machines and their locations.</li>
     *   <li>Act: Make a GET request to the "/vendmachtrack" endpoint using MockMvc.</li>
     *   <li>Assert: Verify that the response status is 200 (OK) and that the returned JSON matches the expected format.</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_getVendMachList_returnVendmachList() throws Exception {
        // Arrange
        HashMap<Integer, String> vendMachList = new HashMap<>();
        vendMachList.put(1, "Oslo");
        when(machineTrackerService.getVendMachList()).thenReturn(vendMachList);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .get("/vendmachtrack")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("{\"1\":\"Oslo\"}"));
    }

    
    /**
     * Tests the scenario where the {@link MachineTrackerController} throws a {@link ResourceNotFoundException} when trying to get the list of vending machines.
     * 
     * This test expects the HTTP response status to be 404 (Not Found) and the exception to be of type {@link ResourceNotFoundException} with the message "Should throw".
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to throw a {@link ResourceNotFoundException} with the message "Should throw" when called.</li>
     *   <li>Act: Make a GET request to the "/vendmachtrack" endpoint using MockMvc.</li>
     *   <li>Assert: Verify that the response status is 404 (Not Found), the resolved exception is of type {@link ResourceNotFoundException}, and the exception message matches "Should throw".</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_getVendMachList_throwsResourceNotFoundException() throws Exception {

        when(machineTrackerService.getVendMachList()).thenThrow(new ResourceNotFoundException("Should throw"));

        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .get("/vendmachtrack")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }

    
    /**
     * Tests the behavior of the {@link MachineTrackerController#getVendMachLocation(int)} method in the {@link MachineTrackerController} class.
     * 
     * <p>
     * This test verifies that the {@link MachineTrackerController#getVendMachLocation(int)} method returns the expected location ("Oslo") for a vending machine with a given ID and ensures a 200 (OK) response status.
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to return the location "Oslo" when the {@link MachineTrackerService#getVendMachLocation(int)} method is called with any integer argument.</li>
     *   <li>Act: Make a GET request to the "/vendmachtrack/1/name" endpoint using MockMvc to retrieve the location of vending machine ID 1.</li>
     *   <li>Assert: Verify that the response status is 200 (OK) and that the returned content matches the expected location "Oslo".</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_getVendMachLocation_returnLocation() throws Exception {
        // Arrange
        String location = "Oslo";
        when(machineTrackerService.getVendMachLocation(anyInt())).thenReturn(location);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .get("/vendmachtrack/1/name")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("Oslo"));
    }

    /**
     * Tests the behavior of the {@link MachineTrackerController#getVendMachLocation(int)} method in the {@link MachineTrackerController} class when it throws a {@link ResourceNotFoundException}.
     * 
     * <p>
     * This test verifies that the {@link MachineTrackerController#getVendMachLocation(int)} method throws a {@link ResourceNotFoundException} with the message "Should throw" and ensures that the HTTP response status is 404 (Not Found).
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to throw a {@link ResourceNotFoundException} with the message "Should throw" when the {@link MachineTrackerService#getVendMachLocation(int)} method is called with any integer argument.</li>
     *   <li>Act: Make a GET request to the "/vendmachtrack/1/name" endpoint using MockMvc.</li>
     *   <li>Assert: Verify that the response status is 404 (Not Found), the resolved exception is of type {@link ResourceNotFoundException}, and the exception message matches "Should throw".</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_getVendMachLocation_throwsResourceNotFoundException() throws Exception {

        // Arrange
        when(machineTrackerService.getVendMachLocation(anyInt())).thenThrow(new ResourceNotFoundException("Should throw"));

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .get("/vendmachtrack/1/name")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }

    /**
     * Tests the behavior of the {@link MachineTrackerController#getInventory(int)} method in the {@link MachineTrackerController} class.
     * 
     * <p>
     * This test verifies that the {@link MachineTrackerController#getInventory(int)} method returns a JSON object containing the inventory of a vending machine ("Cola": 10) and ensures that the HTTP response status is 200 (OK).
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to return an inventory HashMap with the item "Cola" and quantity 10 when the {@link MachineTrackerService#getInventory(int)} method is called with any integer argument.</li>
     *   <li>Act: Make a GET request to the "/vendmachtrack/1" endpoint using MockMvc to get the inventory of vending machine ID 1.</li>
     *   <li>Assert: Verify that the response status is 200 (OK) and that the returned content matches the expected inventory JSON.</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_getInventory_returnInventory() throws Exception {
        // Arrange
        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Cola", 10);
        when(machineTrackerService.getInventory(anyInt())).thenReturn(inventory);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .get("/vendmachtrack/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("{\"Cola\":10}"));
    }

    /**
     * Tests the behavior of the {@link MachineTrackerController#getInventory(int)} method in the {@link MachineTrackerController} class when it throws an {@link IllegalInputException}.
     * 
     * <p>
     * This test verifies that the {@link MachineTrackerController#getInventory(int)} method throws an {@link IllegalInputException} with the message "Should throw" and ensures that the HTTP response status is 400 (Bad Request).
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to throw an {@link IllegalInputException} with the message "Should throw" when the {@link MachineTrackerService#getInventory(int)} method is called with any integer argument.</li>
     *   <li>Act: Make a GET request to the "/vendmachtrack/1" endpoint using MockMvc to retrieve the inventory of vending machine ID 1.</li>
     *   <li>Assert: Verify that the response status is 400 (Bad Request), the resolved exception is of type {@link IllegalInputException}, and the exception message matches "Should throw".</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_getInventory_throwsIllegalInputException() throws Exception {
        // Arrange
        when(machineTrackerService.getInventory(anyInt())).thenThrow(new IllegalInputException("Should throw"));

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .get("/vendmachtrack/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalInputException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }

    /**
     * Tests the behavior of the {@link MachineTrackerController#addItem(int, String, int)} method in the {@link MachineTrackerController} class.
     * 
     * <p>
     * This test verifies that the {@link MachineTrackerController#addItem(int, String, int)} method returns a JSON object containing the updated inventory of the vending machine ("Coke": 10) and ensures that the HTTP response status is 200 (OK).
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to return an updated inventory HashMap with the item "Coke" and quantity 10 when the {@link MachineTrackerService#addItem(int, String, int)} method is called.</li>
     *   <li>Act: Make a PUT request to the "/vendmachtrack/1/add" endpoint using MockMvc with the parameters "item" set to "Coke" and "quantity" set to 10.</li>
     *   <li>Assert: Verify that the response status is 200 (OK).</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_addItem_returnUpdatedInventory() throws Exception {
        // Arrange
        HashMap<String, Integer> updatedInventory = new HashMap<>();
        updatedInventory.put("Coke", 10);
        when(machineTrackerService.addItem(anyInt(), anyString(), anyInt())).thenReturn(updatedInventory);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .put("/vendmachtrack/1/add")
                .param("item", "Coke")
                .param("quantity", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("{\"Coke\":10}"));
    }
    
    /**
     * Tests the behavior of the {@link MachineTrackerController#addItem(int, String, int)} method in the {@link MachineTrackerController} class when it throws an {@link IllegalInputException}.
     * 
     * <p>
     * This test verifies that the {@link MachineTrackerController#addItem(int, String, int)} method throws an {@link IllegalInputException} with the message "Should throw" and ensures that the HTTP response status is 400 (Bad Request).
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to throw an {@link IllegalInputException} with the message "Should throw" when the {@link MachineTrackerService#addItem(int, String, int)} method is called.</li>
     *   <li>Act: Make a PUT request to the "/vendmachtrack/1/add" endpoint using MockMvc with the parameters "item" set to "Coke" and "quantity" set to 10.</li>
     *   <li>Assert: Verify that the response status is 400 (Bad Request), the resolved exception is of type {@link IllegalInputException}, and the exception message matches "Should throw".</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_addItem_throwsIllegalInputException() throws Exception {
        // Arrange
        when(machineTrackerService.addItem(anyInt(), anyString(), anyInt())).thenThrow(new IllegalInputException("Should throw"));

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .put("/vendmachtrack/1/add")
                .param("item", "Coke")
                .param("quantity", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalInputException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }

    /**
     * Tests the behavior of the {@link MachineTrackerController#removeItem(int, String, int)} method in the {@link MachineTrackerController} class.
     * 
     * <p>
     * This test verifies that the {@link MachineTrackerController#removeItem(int, String, int)} method returns a JSON object containing the updated inventory of the vending machine (e.g., "Coke": 5) and ensures that the HTTP response status is 200 (OK).
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to return an updated inventory HashMap with the item "Coke" and quantity 5 when the {@link MachineTrackerService#removeItem(int, String, int)} method is called.</li>
     *   <li>Act: Make a PUT request to the "/vendmachtrack/1/remove" endpoint using MockMvc with the parameters "item" set to "Coke" and "quantity" set to 5.</li>
     *   <li>Assert: Verify that the response status is 200 (OK).</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_removeItem_returnUpdatedInventory() throws Exception {
        // Arrange
        HashMap<String, Integer> updatedInventory = new HashMap<>();
        updatedInventory.put("Coke", 5);
        when(machineTrackerService.removeItem(anyInt(), anyString(), anyInt())).thenReturn(updatedInventory);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .put("/vendmachtrack/1/remove")
                .param("item", "Coke")
                .param("quantity", "5")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("{\"Coke\":5}"));
    }

   
    /**
     * Tests the behavior of the {@link MachineTrackerController#removeItem(int, String, int)} method in the {@link MachineTrackerController} class when it throws an {@link IllegalInputException}.
     * 
     * <p>
     * This test verifies that the {@link MachineTrackerController#removeItem(int, String, int)} method throws an {@link IllegalInputException} with the message "Should throw" and ensures that the HTTP response status is 400 (Bad Request).
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to throw an {@link IllegalInputException} with the message "Should throw" when the {@link MachineTrackerService#removeItem(int, String, int)} method is called.</li>
     *   <li>Act: Make a PUT request to the "/vendmachtrack/1/remove" endpoint using MockMvc with the parameters "item" set to "Coke" and "quantity" set to 5.</li>
     *   <li>Assert: Verify that the response status is 400 (Bad Request), the resolved exception is of type {@link IllegalInputException}, and the exception message matches "Should throw".</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_removeItem_throwsIllegalInputException() throws Exception {

        // Arrange
        when(machineTrackerService.removeItem(anyInt(), anyString(), anyInt())).thenThrow(new IllegalInputException("Should throw"));

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .put("/vendmachtrack/1/remove")
                .param("item", "Coke")
                .param("quantity", "5")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalInputException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));

    }

    
    /**
     * Tests the behavior of the {@link MachineTrackerController#addVendMach(int, String)} method in the {@link MachineTrackerController} class.
     * 
     * <p>
     * This test verifies that the {@link MachineTrackerController#addVendMach(int, String)} method returns a {@link HashMap} with the added vending machine's ID and location ("1": "Oslo") and ensures that the HTTP response status is 200 (OK).
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to return a {@link HashMap} with the added vending machine's ID and location (e.g., "1": "Oslo") when the {@link MachineTrackerService#addVendMach(int, String)} method is called.</li>
     *   <li>Act: Make a POST request to the "/vendmachtrack/add" endpoint using MockMvc with the parameters "id" set to 1 and "location" set to "Oslo".</li>
     *   <li>Assert: Verify that the response status is 200 (OK) and the response content matches the expected JSON format (e.g., "{\"1\":\"Oslo\"}").</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_addVendMach_returnAdded() throws Exception {

        // Arrange
        HashMap<Integer, String> status = new HashMap<>();
        status.put(1, "Oslo");
        given(machineTrackerService.addVendMach(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString()))
                .willReturn(status);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .post("/vendmachtrack/add")
                .param("id", "1")
                .param("location", "Oslo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("{\"1\":\"Oslo\"}"));
    }

    
    /**
     * Tests the behavior of the {@link MachineTrackerController#addVendMach(int, String)} method in the {@link MachineTrackerController} class when an {@link IllegalInputException} is thrown.
     * 
     * <p>
     * This test verifies that the {@link MachineTrackerController#addVendMach(int, String)} method throws an {@link IllegalInputException} and ensures that the HTTP response status is 400 Bad Request.
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to throw an {@link IllegalInputException} with the message "Should throw" when the {@link MachineTrackerService#addVendMach(int, String)} method is called with any integer and string arguments.</li>
     *   <li>Act: Make a POST request to the "/vendmachtrack/add" endpoint using MockMvc with the parameters "id" set to 1 and "location" set to "Oslo".</li>
     *   <li>Assert: Verify that the response status is 400 Bad Request and the exception thrown is of type {@link IllegalInputException} with the message "Should throw".</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_addVendMach_throwsIllegalInputException() throws Exception {
        //Arrange
        when(machineTrackerService.addVendMach(anyInt(), anyString())).thenThrow(new IllegalInputException("Should throw"));

        //Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .post("/vendmachtrack/add")
                .param("id", "1")
                .param("location", "Oslo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalInputException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }

    
    /**
     * Tests the behavior of the {@link MachineTrackerController#removeVendMach(int)} method in the {@link MachineTrackerController} class.
     * 
     * <p>
     * This test verifies that the {@link MachineTrackerController#removeVendMach(int)} method returns an updated list of vending machines as a {@link HashMap} and ensures that the HTTP response status is 200 (OK).
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to return an updated list of vending machines as a {@link HashMap} when the {@link MachineTrackerService#removeVendMach(int)} method is called.</li>
     *   <li>Act: Make a DELETE request to the "/vendmachtrack/1" endpoint using MockMvc.</li>
     *   <li>Assert: Verify that the response status is 200 OK and the returned JSON matches the expected updated list of vending machines.</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_removeVendMach_returnUpdatedList() throws Exception {
        // Arrange
        HashMap<Integer, String> updatedList = new HashMap<>();
        updatedList.put(2, "Trondheim");
        when(machineTrackerService.removeVendMach(anyInt())).thenReturn(updatedList);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .delete("/vendmachtrack/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("{\"2\":\"Trondheim\"}"));
    }

    /**
     * Tests the behavior of the {@link MachineTrackerController#removeVendMach(int)} method in the {@link MachineTrackerController} class when an {@link IllegalInputException} is thrown.
     * 
     * <p>
     * This test verifies that the {@link MachineTrackerController#removeVendMach(int)} method handles the scenario where an {@link IllegalInputException} is thrown. It expects the HTTP response status to be 400 Bad Request, and the thrown exception to be of type {@link IllegalInputException} with the message "Should throw."
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to throw an {@link IllegalInputException} with the message "Should throw" when the {@link MachineTrackerService#removeVendMach(int)} method is called.</li>
     *   <li>Act: Make a DELETE request to the "/vendmachtrack/1" endpoint using MockMvc.</li>
     *   <li>Assert: Verify that the response status is 400 Bad Request and that the thrown exception is of type {@link IllegalInputException} with the expected message.</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_removeVendMach_throwsIllegalInputException() throws Exception {

        // Arrange
        when(machineTrackerService.removeVendMach(anyInt())).thenThrow(new IllegalInputException("Should throw"));

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .delete("/vendmachtrack/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalInputException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }

  
    /**
     * Tests the behavior of the {@link MachineTrackerController#changeLocation(int, String)} method in the {@link MachineTrackerController} class.
     * 
     * <p>
     * This test verifies that the {@link MachineTrackerController#changeLocation(int, String)} method correctly updates the location of a vending machine and returns a HashMap with the updated location. It expects the HTTP response status to be 200 (OK).
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to return a HashMap with the updated location when the {@link MachineTrackerService#changeLocation(int, String)} method is called.</li>
     *   <li>Act: Make a PUT request to the "/vendmachtrack/1" endpoint with the updated location using MockMvc.</li>
     *   <li>Assert: Verify that the response status is 200 (OK) and that the response body contains the expected updated location.</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_changeLocation_returnUpdatedLocation() throws Exception {
        // Arrange
        HashMap<Integer, String> updatedLocation = new HashMap<>();
        updatedLocation.put(1, "Trondheim");
        when(machineTrackerService.changeLocation(anyInt(), anyString())).thenReturn(updatedLocation);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .put("/vendmachtrack/1")
                .param("location", "Trondheim")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("{\"1\":\"Trondheim\"}"));
    }

 
    /**
     * Tests the behavior of the {@link MachineTrackerController#changeLocation(int, String)} method in the {@link MachineTrackerController} class when it throws an {@link IllegalInputException}.
     * 
     * <p>
     * This test verifies how the {@link MachineTrackerController#changeLocation(int, String)} method handles an {@link IllegalInputException} when trying to update the location of a vending machine. It expects the HTTP response status to be 400 Bad Request and the exception to be of type {@link IllegalInputException}.
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to throw an {@link IllegalInputException} with the message "Should throw" when the {@link MachineTrackerService#changeLocation(int, String)} method is called.</li>
     *   <li>Act: Make a PUT request to the "/vendmachtrack/1" endpoint with the updated location using MockMvc.</li>
     *   <li>Assert: Verify that the response status is 400 Bad Request and that the exception is of type {@link IllegalInputException} with the expected message.</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_changeLocation_throwsIllegalInputException() throws Exception {

        // Arrange
        when(machineTrackerService.changeLocation(anyInt(), anyString())).thenThrow(new IllegalInputException("Should throw"));

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .put("/vendmachtrack/1")
                .param("location", "Trondheim")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalInputException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }

   
    /**
     * Tests the behavior of the {@link MachineTrackerController#changeLocation(int, String)} method in the {@link MachineTrackerController} class when it throws a {@link ResourceNotFoundException}.
     * 
     * <p>
     * This test verifies how the {@link MachineTrackerController#changeLocation(int, String)} method handles a {@link ResourceNotFoundException} when trying to update the location of a vending machine. It expects the HTTP response status to be 404 Not Found and the exception to be of type {@link ResourceNotFoundException}.
     * </p>
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Configure the {@link MachineTrackerService} to throw a {@link ResourceNotFoundException} with the message "Should throw" when the {@link MachineTrackerService#changeLocation(int, String)} method is called.</li>
     *   <li>Act: Make a PUT request to the "/vendmachtrack/1" endpoint with the updated location using MockMvc.</li>
     *   <li>Assert: Verify that the response status is 404 Not Found and that the exception is of type {@link ResourceNotFoundException} with the expected message.</li>
     * </ol>
     * 
     * @throws Exception if an exception occurs during test execution.
     */
    @Test
    public void MachineTrackerController_changeLocation_throwsResourceNotFoundException() throws Exception {

        // Arrange
        when(machineTrackerService.changeLocation(anyInt(), anyString())).thenThrow(new ResourceNotFoundException("Should throw"));

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
                .put("/vendmachtrack/1")
                .param("location", "Trondheim")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }

}
