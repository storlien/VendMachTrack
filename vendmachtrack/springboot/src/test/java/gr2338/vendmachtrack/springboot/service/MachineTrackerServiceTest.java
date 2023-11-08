package gr2338.vendmachtrack.springboot.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import gr2338.vendmachtrack.springboot.service.MachineTrackerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import gr2338.vendmachtrack.core.model.MachineTracker;
import gr2338.vendmachtrack.core.model.VendingMachine;
import gr2338.vendmachtrack.springboot.exception.IllegalInputException;
import gr2338.vendmachtrack.springboot.exception.ResourceNotFoundException;
import gr2338.vendmachtrack.springboot.repository.MachineTrackerRepository;

import java.util.Collections;
import java.util.HashMap;

/**
 * Unit tests for the {@link MachineTrackerService} class.
 * This class contains test cases to verify the behavior of the {@link MachineTrackerService} methods.
 * Each test case focuses on specific functionality and expected outcomes of the service.
 *
 * <p>
 * Test cases cover various scenarios including valid inputs, invalid inputs, exceptions, and edge cases to ensure the service behaves as expected.
 * </p>
 */
public class MachineTrackerServiceTest {

    @Mock
    private MachineTrackerRepository repository;

    @InjectMocks
    private MachineTrackerService service;

    private final MachineTracker machineTracker = new MachineTracker();
    private final VendingMachine machine = new VendingMachine();

    /**
     * Sets up the test environment before each test case.
     * Initializes the Mockito annotations and sets the machine ID to 1, location to
     * "Oslo",
     * and adds the machine to the machine tracker.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        machine.setId(1);
        machine.setLocation("Oslo");
        machineTracker.setMachines(Collections.singletonList(machine));
    }

    /**
     * Tests the {@link MachineTrackerService#getVendMachList()} method of the {@link MachineTrackerService} class.
     *
     * <p>
     * This test case verifies that the {@link MachineTrackerService#getVendMachList()} method returns a list of machines when the machine tracker is present.
     * </p>
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including a {@link MachineTracker} object with machines and configure mock behavior for the repository.</li>
     *   <li>Act: Call the {@link MachineTrackerService#getVendMachList()} method to retrieve the actual list of machines.</li>
     *   <li>Assert: Verify that the actual list of machines is not null, contains the expected machine(s), and has the correct mapping of machine IDs to locations.
     *          Also, ensure that the {@link MachineTrackerRepository#getVendmachtrack()} method is called once.</li>
     * </ol>
     */
    @Test
    public void getVendMachList_whenMachineTrackerIsPresent_returnsListOfMachines() {

        // Arrange
        when(repository.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        HashMap<Integer, String> result = service.getVendMachList();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(1));
        assertEquals("Oslo", result.get(1));
    }

    /**
     * Test case for the {@link MachineTrackerService#getVendMachList()} method of the {@link MachineTrackerService} class when the machine tracker is not present.
     * Expects a {@link ResourceNotFoundException} to be thrown.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including a null {@link MachineTracker} object and configure mock behavior for the repository.</li>
     *   <li>Act & Assert: Verify that calling the {@link MachineTrackerService#getVendMachList()} method results in a {@link ResourceNotFoundException} being thrown.</li>
     * </ol>
     */
    @Test
    public void getVendMachList_whenMachineTrackerIsNotPresent_thowsException() {

        // Arrange
        when(repository.getVendmachtrack()).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.getVendMachList());

    }

    /**
     * Test case for the {@link MachineTrackerService#getVendMachLocation(int)} method of the {@link MachineTrackerService} class.
     * Verifies that the method returns the correct location for a valid machine ID.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including a valid machine ID and configure mock behavior for the repository.</li>
     *   <li>Act: Call the {@link MachineTrackerService#getVendMachLocation(int)} method to retrieve the actual location.</li>
     *   <li>Assert: Verify that the actual location matches the expected location.</li>
     * </ol>
     */
    @Test
    public void getVendMachLocation_validId_returnsLocation() {
        // Arrange
        when(repository.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        String location = service.getVendMachLocation(1);

        // Assert
        assertEquals("Oslo", location);
    }

    /**
     * Test case for the {@link MachineTrackerService#getVendMachLocation(int)} method of the {@link MachineTrackerService} class.
     * Verifies that the method throws a {@link ResourceNotFoundException} when an invalid ID is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including an invalid machine ID and configure mock behavior for the repository.</li>
     *   <li>Act & Assert: Verify that calling the {@link MachineTrackerService#getVendMachLocation(int)} method with an invalid ID throws a {@link ResourceNotFoundException}.</li>
     * </ol>
     */
    @Test
    public void getVendMachLocation_invalidId_throwsResourceNotFoundException() {

        // Arrange
        when(repository.getVendmachtrack()).thenReturn(machineTracker);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.getVendMachLocation(99));
    }

    /**
     * Test case for the {@link MachineTrackerService#getInventory(int)} method of the {@link MachineTrackerService} class.
     * Verifies that the method returns the correct inventory for a valid machine ID.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including a valid machine ID, inventory data, and configure mock behavior for the repository.</li>
     *   <li>Act: Call the {@link MachineTrackerService#getInventory(int)} method to retrieve the actual inventory.</li>
     *   <li>Assert: Verify that the actual inventory matches the expected one.</li>
     * </ol>
     */
    @Test
    public void getInventory_validId_returnsInventory() {
        // Arrange
        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Cola", 10);
        inventory.put("Pepsi", 5);
        machine.setStatus(inventory);
        when(repository.getVendMach(1)).thenReturn(machine);

        // Act
        HashMap<String, Integer> fetchedInventory = service.getInventory(1);

        // Assert
        assertEquals(inventory, fetchedInventory);
    }

    /**
     * Test case for the {@link MachineTrackerService#getInventory(int)} method of the {@link MachineTrackerService} class when an invalid ID is provided.
     * Expects a {@link ResourceNotFoundException} to be thrown.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including an invalid machine ID, and configure mock behavior for the repository.</li>
     *   <li>Act & Assert: Call the {@link MachineTrackerService#getInventory(int)} method with an invalid ID and assert that it throws a {@link ResourceNotFoundException}.</li>
     * </ol>
     */
    @Test
    public void getInventory_invalidId_throwsResourceNotFoundException() {
        // Arrange
        when(repository.getVendMach(99)).thenReturn(null);

        //Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.getInventory(99));
    }

    /**
     * Test case for the {@link MachineTrackerService#addItem(int, String, int)} method of the {@link MachineTrackerService} class with valid input.
     * Verifies that the method adds an item to the machine successfully when a valid ID, item, and quantity are provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including a valid machine ID, item, quantity, and configure mock behavior for the repository.</li>
     *   <li>Act: Call the {@link MachineTrackerService#addItem(int, String, int)} method with valid input.</li>
     *   <li>Assert: Verify that the method returns the expected updated inventory.</li>
     * </ol>
     */
    @Test
    public void addItem_validInput_addsItemSuccessfully() {
        // Arrange
        HashMap<String, Integer> inventory = new HashMap<>();
        VendingMachine updatedMachine = new VendingMachine();
        updatedMachine.setId(1);
        updatedMachine.setStatus(inventory);

        when(repository.getVendMach(1)).thenReturn(machine);
        when(repository.addItem(1, "Cola", 5)).thenReturn(updatedMachine);

        // Act
        HashMap<String, Integer> updatedInventory = service.addItem(1, "Cola", 5);

        // Assert
        assertEquals(inventory, updatedInventory);
    }


    /**
     * Test case for the {@link MachineTrackerService#addItem(int, String, int)} method of the {@link MachineTrackerService} class when an invalid item is added to the machine.
     * Verifies that an {@link IllegalInputException} is thrown when an invalid item is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Act & Assert: Verify that an {@link IllegalInputException} is thrown when an invalid item is added to the machine.</li>
     * </ol>
     */
    @Test
    public void addItem_invalidItem_throwsIllegalInputException() {
        // Act and Assert
        assertThrows(IllegalInputException.class, () -> service.addItem(1, " ", 5));
    }

    /**
     * Test case for the {@link MachineTrackerService#addItem(int, String, int)} method of the {@link MachineTrackerService} class when an invalid quantity is provided.
     * Verifies that an {@link IllegalInputException} is thrown when an invalid quantity is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Act & Assert: Verify that an {@link IllegalInputException} is thrown when an invalid quantity is provided.</li>
     * </ol>
     */
    @Test
    public void addItem_invalidQuantity_throwsIllegalInputException() {
        // Act and Assert
        assertThrows(IllegalInputException.class, () -> service.addItem(1, "Cola", 0));
    }

    /**
     * Test case for the {@link MachineTrackerService#addItem(int, String, int)} method of the {@link MachineTrackerService} class when an invalid ID is provided.
     * Verifies that a {@link ResourceNotFoundException} is thrown when an invalid ID is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary mock behavior to simulate a non-existent vending machine with the given ID.</li>
     *   <li>Act & Assert: Verify that a {@link ResourceNotFoundException} is thrown when an invalid ID is provided.</li>
     * </ol>
     */
    @Test
    public void addItem_invalidId_throwsResourceNotFoundException() {
        // Arrange
        when(repository.getVendMach(99)).thenReturn(null);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> service.addItem(99, "Cola", 5));
    }

    /**
     * Test case for the {@link MachineTrackerService#addItem(int, String, int)} method of the {@link MachineTrackerService} class
     * when an invalid quantity is provided.
     * Verifies that an {@link IllegalInputException} is thrown when an invalid quantity is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Act & Assert: Verify that an {@link IllegalInputException} is thrown when an invalid quantity is provided.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void addItem_negativeQuantity_throwsIllegalInputException() {

        // Act and Assert
        assertThrows(IllegalInputException.class, () -> service.addItem(1, "Cola", -5));
    }

    /**
     * Test case for the {@link MachineTrackerService#addVendMach(int, String)} method of the {@link MachineTrackerService} class
     * when an existing ID is provided.
     * Verifies that the method throws an {@link IllegalInputException} when an existing ID is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including an existing machine with the same ID.</li>
     *   <li>Act & Assert: Verify that an {@link IllegalInputException} is thrown when an existing ID is provided.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void addVendMach_existingId_throwsIllegalInputException() {
        // Arrange
        when(repository.getVendMach(1)).thenReturn(machine);

        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.addVendMach(1, "Trondheim"));
    }

    /**
     * Test case for adding a new vending machine to the machine tracker service.
     * Verifies that the method adds a new vending machine successfully when a
     * valid ID and location are provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including a new vending machine with a valid ID and location, and configure mock behavior for the repository.</li>
     *   <li>Act: Call the {@link MachineTrackerService#addVendMach(int, String)} method with valid input.</li>
     *   <li>Assert: Verify that the method returns the expected updated machine list with the new vending machine.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void addVendMach_validInput_addsMachineSuccessfully() {

        // Arrange
        VendingMachine newMachine = new VendingMachine();
        newMachine.setId(2);
        newMachine.setLocation("Bergen");
        machineTracker.addVendingMachine(newMachine);
        when(repository.getVendMach(2)).thenReturn(null); // No existing machine with ID 2
        when(repository.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        HashMap<Integer, String> updatedMachineList = service.addVendMach(2, "Bergen");

        // Assert
        assertTrue(updatedMachineList.containsKey(2));
        assertEquals("Bergen", updatedMachineList.get(2));
    }

    /**
     * Test case for the {@link MachineTrackerService#addVendMach(int, String)} method of the {@link MachineTrackerService} class
     * when an invalid location is provided.
     * Verifies that the method throws an {@link IllegalInputException} when an invalid location is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including no existing machine with the same ID.</li>
     *   <li>Act & Assert: Verify that an {@link IllegalInputException} is thrown when an invalid location is provided.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void addVendMach_invalidLocation_throwsIllegalInputException() {
        // Arrange
        when(repository.getVendMach(2)).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.addVendMach(2, "123InvalidLocation"));
    }

    /**
     * Test case for the {@link MachineTrackerService#addVendMach(int, String)} method of the {@link MachineTrackerService} class
     * when an invalid ID is provided.
     * Verifies that the method throws an {@link IllegalInputException} when an invalid ID is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including a new vending machine with an invalid ID and a location, and configure mock behavior for the repository.</li>
     *   <li>Act & Assert: Verify that an {@link IllegalInputException} is thrown when an invalid ID is provided.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void addVendMach_invalidId_throwsIllegalInputException() {
        // Arrange
        VendingMachine newMachine = new VendingMachine();
        newMachine.setId(-1);
        newMachine.setLocation("Bergen");
        when(repository.getVendMach(-1)).thenReturn(newMachine);

        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.addVendMach(-1, "Trondheim"));
    }

    /**
     * Test case for the {@link MachineTrackerService#removeVendMach(int)} method of the {@link MachineTrackerService} class
     * when a valid ID is provided.
     * Verifies that the method removes a vending machine successfully when a valid ID is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including an existing vending machine with a valid ID, and configure mock behavior for the repository.</li>
     *   <li>Act: Call the {@link MachineTrackerService#removeVendMach(int)} method with a valid ID.</li>
     *   <li>Assert: Verify that the method removes the vending machine from the machine list.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void removeVendMach_validId_removesMachineSuccessfully() {

        // Arrange
        when(repository.getVendMach(1)).thenReturn(machine);
        when(repository.getVendmachtrack()).thenReturn(machineTracker);
        machineTracker.removeVendingMachine(machine);

        // Act
        HashMap<Integer, String> updatedMachineList = service.removeVendMach(1);

        // Assert
        assertFalse(updatedMachineList.containsKey(1));
    }

    /**
     * Test case for the {@link MachineTrackerService#removeVendMach(int)} method of the {@link MachineTrackerService} class
     * when an invalid ID is provided.
     * Verifies that the method throws a {@link ResourceNotFoundException} when an invalid ID is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data.</li>
     *   <li>Act & Assert: Verify that a {@link ResourceNotFoundException} is thrown when an invalid ID is provided.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void removeVendMach_invalidId_throwsResourceNotFoundException() {
        // Arrange
        when(repository.getVendMach(99)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.removeVendMach(99));
    }

    /**
     * Test case for the {@link MachineTrackerService#changeLocation(int, String)} method of the {@link MachineTrackerService} class
     * when valid input is provided.
     * Verifies that the method changes the location of a vending machine successfully.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including an existing vending machine with a valid ID and a new location, and configure mock behavior for the repository.</li>
     *   <li>Act: Call the {@link MachineTrackerService#changeLocation(int, String)} method with valid input.</li>
     *   <li>Assert: Verify that the method returns the expected updated machine list with the new location.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void changeLocation_validInput_changesLocationSuccessfully() {
        // Arrange
        VendingMachine updatedMachine = new VendingMachine();
        updatedMachine.setId(1);
        updatedMachine.setLocation("Trondheim");
        MachineTracker updatedMachineTracker = new MachineTracker();
        updatedMachineTracker.addVendingMachine(updatedMachine);

        when(repository.getVendMach(1)).thenReturn(updatedMachine);
        when(repository.getVendmachtrack()).thenReturn(updatedMachineTracker);

        // Act
        HashMap<Integer, String> updatedMachineList = service.changeLocation(1, "Trondheim");

        // Assert
        assertTrue(updatedMachineList.containsKey(1));
        assertEquals("Trondheim", updatedMachineList.get(1));
    }


    /**
     * Test case for the {@link MachineTrackerService#changeLocation(int, String)} method of the {@link MachineTrackerService} class
     * when an invalid location is provided.
     * Verifies that the method throws an {@link IllegalInputException} when an invalid location is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including an existing vending machine with a valid ID.</li>
     *   <li>Act & Assert: Verify that an {@link IllegalInputException} is thrown when an invalid location is provided.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void changeLocation_invalidLocation_throwsIllegalInputException() {
        // Arrange
        when(repository.getVendMach(1)).thenReturn(machine);

        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.changeLocation(1, "1"));
    }

    /**
     * Test case for the {@link MachineTrackerService#changeLocation(int, String)} method of the {@link MachineTrackerService} class
     * when an invalid ID is passed as input.
     * Verifies that the method throws a {@link ResourceNotFoundException} when an invalid ID is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including no existing vending machine with the provided invalid ID.</li>
     *   <li>Act & Assert: Verify that a {@link ResourceNotFoundException} is thrown when an invalid ID is provided.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void changeLocation_invalidId_throwsResourceNotFoundException() {
        // Arrange
        when(repository.getVendMach(99)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.changeLocation(99, "Bergen"));
    }

    /**
     * Test case for the {@link MachineTrackerService#removeItem(int, String, int)} method of the {@link MachineTrackerService} class
     * when an invalid item is passed as input.
     * Verifies that the method throws an {@link IllegalInputException} when an invalid item is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Act & Assert: Verify that an {@link IllegalInputException} is thrown when an invalid item is provided.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void removeItem_invalidItem_throwsIllegalInputException() {
        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.removeItem(1, " ", 5));
    }

    /**
     * Test case for the {@link MachineTrackerService#removeItem(int, String, int)} method of the {@link MachineTrackerService} class
     * when an invalid quantity is provided.
     * Verifies that the method throws an {@link IllegalInputException} when an invalid quantity is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Act & Assert: Verify that an {@link IllegalInputException} is thrown when an invalid quantity is provided.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void removeItem_invalidQuantity_throwsIllegalInputException() {
        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.removeItem(1, "Cola", 0));
    }

    /**
     * Test case for the {@link MachineTrackerService#removeItem(int, String, int)} method of the {@link MachineTrackerService} class
     * when an invalid ID is provided.
     * Verifies that the method throws a {@link ResourceNotFoundException} when an invalid ID is provided.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including no existing vending machine with the provided invalid ID.</li>
     *   <li>Act & Assert: Verify that a {@link ResourceNotFoundException} is thrown when an invalid ID is provided.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void removeItem_invalidId_throwsResourceNotFoundException() {
        // Arrange
        when(repository.getVendMach(99)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.removeItem(99, "Cola", 5));
    }

    /**
     * Test case for the {@link MachineTrackerService#removeItem(int, String, int)} method of the {@link MachineTrackerService} class
     * when the item is not in the inventory.
     * Verifies that the method throws an {@link IllegalInputException} when the item is not present in the inventory.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including an empty inventory for the vending machine.</li>
     *   <li>Act: Call the {@link MachineTrackerService#removeItem(int, String, int)} method.</li>
     *   <li>Assert: Verify that an {@link IllegalInputException} is thrown when the item is not in the inventory.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void removeItem_itemNotInInventory_throwsIllegalInputException() {
        // Arrange
        HashMap<String, Integer> inventory = new HashMap<>();
        machine.setStatus(inventory);

        // Act
        when(repository.getVendMach(1)).thenReturn(machine);

        // Assert
        assertThrows(IllegalInputException.class, () -> service.removeItem(1, "Cola", 5));
    }

    /**
     * Test case for the {@link MachineTrackerService#removeItem(int, String, int)} method of the {@link MachineTrackerService} class
     * when the removal quantity is greater than the inventory.
     * Verifies that the method throws an {@link IllegalInputException} when the removal quantity exceeds the available quantity in the inventory.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including an inventory with a lower quantity than the removal quantity.</li>
     *   <li>Act: Call the {@link MachineTrackerService#removeItem(int, String, int)} method.</li>
     *   <li>Assert: Verify that an {@link IllegalInputException} is thrown when the removal quantity is greater than the inventory.</li>
     * </ol>
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void removeItem_removalQuantityGreaterThanInventory_throwsIllegalInputException() {
        // Arrange
        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Cola", 2);
        machine.setStatus(inventory);

        // Act
        when(repository.getVendMach(1)).thenReturn(machine);

        // Assert
        assertThrows(IllegalInputException.class, () -> service.removeItem(1, "Cola", 5));
    }

}
