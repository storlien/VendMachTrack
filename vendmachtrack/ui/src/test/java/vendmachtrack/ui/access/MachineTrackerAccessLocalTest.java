package vendmachtrack.ui.access;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import vendmachtrack.core.MachineTracker;
import vendmachtrack.core.VendingMachine;
import vendmachtrack.jsonio.VendmachtrackPersistence;

/**
 * This class tests the {@link vendmachtrack.ui.access.MachineTrackerAccessLocal} class, which provides methods for interacting with
 * a local vending machine tracker. The tests cover all the public methods in the {@link vendmachtrack.ui.access.MachineTrackerAccessLocal} class.
 * 
 * <p>
 * 
 * Each test method in this class corresponds to a method in {@link vendmachtrack.ui.access.MachineTrackerAccessLocal} and tests its functionality
 * with a variety of inputs and scenarios. This includes both normal operation and edge cases, such as invalid inputs.
 * 
 * <p>
 * 
 * The tests use Mockito to mock the {@link vendmachtrack.jsonio.VendmachtrackPersistence} class, which is a dependency of {@link vendmachtrack.ui.access.MachineTrackerAccessLocal}.
 * This allows the tests to control the behavior of {@link vendmachtrack.jsonio.VendmachtrackPersistence} and to verify that {@link vendmachtrack.ui.access.MachineTrackerAccessLocal}
 * interacts with it correctly.
 * 
 * <p>
 * 
 * The {@link #setUp()} method is run before each test and initializes the mocks and the {@link vendmachtrack.ui.access.MachineTrackerAccessLocal} instance to be tested.
 */
public class MachineTrackerAccessLocalTest {

    /**
     * Mock of the {@link vendmachtrack.jsonio.VendmachtrackPersistence} class. This is used to control the behavior of the persistence layer
     * during testing, and to verify that {@link vendmachtrack.ui.access.MachineTrackerAccessLocal} interacts with it correctly.
     */
    @Mock
    private VendmachtrackPersistence mockPersistence;

    /**
     * The {@link vendmachtrack.ui.access.MachineTrackerAccessLocal} instance that is being tested. This is initialized in the {@link #setUp()} method before each test.
     */
    @InjectMocks
    private MachineTrackerAccessLocal accessLocal;

    /**
     * A {@link vendmachtrack.core.MachineTracker} instance used in the tests. This is initialized in the {@link #setUp()} method before each test.
     */
    private MachineTracker machineTracker;

    /**
     * A {@link vendmachtrack.core.VendingMachine} instance used in the tests. This is initialized in the {@link #setUp()} method before each test.
     */
    private VendingMachine machine = new VendingMachine();

    /**
     * This method is run before each test. It initializes the mocks and the {@link vendmachtrack.ui.access.MachineTrackerAccessLocal} instance to be tested.
     * <p>
     *  It also sets up a {@link vendmachtrack.core.MachineTracker} instance with a single {@link vendmachtrack.core.VendingMachine} for use in the tests.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        List<VendingMachine> machines = new ArrayList<>();
        machine.setId(1);
        machine.setLocation("Oslo");
        machines.add(machine);
        machineTracker = new MachineTracker();
        machineTracker.setMachines(machines);
    }

    /**
     * Tests the {@link MachineTrackerAccessLocal#getVendMachList()} method to ensure it correctly fetches the list of vending machines.
     * 
     * This test case verifies:
     * <ul>
     *   <li>That the method interacts with the persistence layer (mocked in this case) to retrieve the list of vending machines.</li>
     *   <li>That the returned list from the method matches the expected list, containing the correct vending machine details.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code getVendMachList} method to retrieve the vending machine list.</li>
     *   <li>Assert: Verify that the returned list matches the expected list containing the vending machine located in "Oslo" with an ID of 1.</li>
     * </ol>
     * 
     */
    @Test
    public void MachineTrackerAccessLocal_getVendMachList_returnsCorrectList() {
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
    
        // Act
        HashMap<Integer, String> result = accessLocal.getVendMachList();
    
        // Assert
        assertEquals(1, result.size());
        assertTrue(result.containsKey(1));
        assertEquals("Oslo", result.get(1));
    }


    /**
     * Tests the {@link MachineTrackerAccessLocal#getVendMachList()} method to ensure that an exception is thrown when there's no available vending machine tracker.
     * 
     * This test case focuses on the scenario when:
     * <ul>
     *   <li>The persistence layer (mocked in this case) does not return any vending machine tracker (returns null).</li>
     *   <li>The method is expected to throw a {@link RuntimeException} with a message indicating that the vending machine tracker was not found.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return null, simulating the absence of a vending machine tracker.</li>
     *   <li>Act: Call the {@code getVendMachList} method, which should result in a {@link RuntimeException} being thrown.</li>
     *   <li>Assert: Verify that a runtime exception is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws RuntimeException When no vending machine tracker is found in the persistence layer.
     */
    @Test
    public void MachineTrackerAccessLocal_getVendMachList_throwsExceptionWhenNoTrackerFound() {
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(null);  // This would cause getMachtrack to throw an exception

        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            accessLocal.getVendMachList();
        }, "Vending Machine Tracker not found");
    }

        
    /**
     * Tests the {@link MachineTrackerAccessLocal#getVendMachLocation(int)} method to ensure it correctly fetches the location of a specified vending machine based on its ID.
     * 
     * This test case verifies:
     * <ul>
     *   <li>That the method interacts with the persistence layer (mocked in this case) to retrieve the location of the vending machine with a specific ID.</li>
     *   <li>That the returned location from the method matches the expected location for the given vending machine ID.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code getVendMachLocation} method with a valid vending machine ID.</li>
     *   <li>Assert: Verify that the returned location matches the expected location ("Oslo" in this case) for the given vending machine ID.</li>
     * </ol>
     * 
     */
    @Test
    public void MachineTrackerAccessLocal_getVendMachLocation_returnsLocationForValidId() {
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        String location = accessLocal.getVendMachLocation(1);

        // Assert
        assertEquals("Oslo", location);
    }

  
    /**
     * Tests the {@link MachineTrackerAccessLocal#getVendMachLocation(int)} method to ensure that an exception is thrown when an invalid vending machine ID is provided.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>The persistence layer (mocked in this case) has no record of the provided vending machine ID.</li>
     *   <li>The method is expected to throw a {@link RuntimeException} with a message indicating that no such vending machine exists for the given ID.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code getVendMachLocation} method with an invalid vending machine ID.</li>
     *   <li>Assert: Verify that a runtime exception is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws RuntimeException When an invalid vending machine ID is provided.
     */
    @Test
    public void MachineTrackerAccessLocal_getVendMachLocation_throwsExceptionForInvalidId() {
       
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        int invalidId = 99;  
    
        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            accessLocal.getVendMachLocation(invalidId);
        }, "No such Vending Machine with ID: " + invalidId);
    }

   
    /**
     * Tests the {@link MachineTrackerAccessLocal#getInventory(int)} method to ensure it accurately fetches the inventory of a specified vending machine based on its ID.
     * 
     * This test case verifies:
     * <ul>
     *   <li>That the method interacts with the persistence layer (mocked in this case) to retrieve the inventory of the vending machine with a specific ID.</li>
     *   <li>That the returned inventory from the method matches the expected inventory, containing the correct items and their respective quantities.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create a mock inventory containing items "Cola" (10 units) and "Øl" (5 units) and set it as the expected inventory for the vending machine.</li>
     *   <li>Mock the persistence layer to return a predefined machine tracker instance with the vending machine and its inventory.</li>
     *   <li>Act: Call the {@code getInventory} method with a valid vending machine ID.</li>
     *   <li>Assert: Verify that the returned inventory matches the expected inventory.</li>
     * </ol>
     * 
     */
    @Test
    public void MachineTrackerAccessLocal_getInventory_returnsInventoryForValidId() {

        // Arrange
        HashMap<String, Integer> mockInventory = new HashMap<>();
        mockInventory.put("Cola", 10);
        mockInventory.put("Øl", 5);
        machine.setStatus(mockInventory);
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        HashMap<String, Integer> resultInventory = accessLocal.getInventory(1);

        // Assert
        assertEquals(mockInventory, resultInventory);
    }

    /**
     * Tests the {@link MachineTrackerAccessLocal#getInventory(int)} method to ensure that it throws an exception when provided with an invalid vending machine ID.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>The persistence layer (mocked in this case) does not have a record of the provided vending machine ID.</li>
     *   <li>The method is expected to throw an {@link IllegalArgumentException} with a message indicating that no such vending machine exists for the given ID.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code getInventory} method with an invalid vending machine ID.</li>
     *   <li>Assert: Verify that an IllegalArgumentException is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws IllegalArgumentException When an invalid vending machine ID is provided.
     */
    @Test
    public void MachineTrackerAccessLocal_getInventory_throwsExceptionForInvalidId() {
        
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        int invalidId = 99;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accessLocal.getInventory(invalidId);
        }, "No such Vending Machine with ID: " + invalidId);
    }


    /**
     * Tests the {@link MachineTrackerAccessLocal#addItem(int, String, int)} method to ensure it correctly adds an item with a specified amount to a vending machine's inventory.
     * 
     * This test case checks:
     * <ul>
     *   <li>If the method adds the specified item and amount to the vending machine's inventory in the persistence layer.</li>
     *   <li>If the returned inventory contains the newly added item with the correct amount.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create a mock inventory for the vending machine and set it as the initial inventory state. Mock the persistence layer to return a predefined machine tracker instance with this vending machine.</li>
     *   <li>Act: Call the {@code addItem} method with a valid vending machine ID, a valid item name, and a valid amount for the item.</li>
     *   <li>Assert: Verify that the returned inventory contains the newly added item with the correct amount.</li>
     * </ol>
     * 
     */
    @Test
    public void MachineTrackerAccessLocal_addItem_addsItemForValidIdItemAmount() {
        
        // Arrange
        HashMap<String, Integer> mockInventory = new HashMap<>();
        machine.setStatus(mockInventory);
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        String newItem = "Cola";
        int newAmount = 5;

        // Act
        HashMap<String, Integer> resultInventory = accessLocal.addItem(1, newItem, newAmount);

        // Assert
        assertTrue(resultInventory.containsKey(newItem));
        assertEquals(newAmount, resultInventory.get(newItem).intValue());
    }


    /**
     * Tests the {@link MachineTrackerAccessLocal#addItem(int, String, int)} method to ensure that it throws an exception when provided with an invalid vending machine ID.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>The persistence layer (mocked in this case) does not have a record of the provided vending machine ID.</li>
     *   <li>The method is expected to throw a {@link RuntimeException} with a message indicating that no such vending machine exists for the given ID.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code addItem} method with an invalid vending machine ID, a new item, and its quantity.</li>
     *   <li>Assert: Verify that a {@link RuntimeException} is thrown with the expected error message.</li>
     * </ol>
     * 
     * @throws RuntimeException When an invalid vending machine ID is provided.
     */
    @Test
    public void MachineTrackerAccessLocal_addItem_throwsExceptionForInvalidId() {
        
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        int invalidId = 99;  
        String newItem = "Water";
        int newAmount = 5;
    
        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            accessLocal.addItem(invalidId, newItem, newAmount);
        }, "No such Vending Machine with ID: " + invalidId);
    }
    
    /**
     * Tests the {@link MachineTrackerAccessLocal#addItem(int, String, int)} method to ensure it throws an exception when provided with an invalid item name.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>The provided item name is not valid (e.g., an empty or blank string).</li>
     *   <li>The method is expected to throw an {@link IllegalArgumentException} with a message indicating that the item name is not valid.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code addItem} method with a valid vending machine ID, an invalid item name, and a valid amount for the item.</li>
     *   <li>Assert: Verify that an IllegalArgumentException is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws IllegalArgumentException When an invalid item name is provided.
     */
    @Test
    public void MachineTrackerAccessLocal_addItem_throwsExceptionForInvalidItem() {
        
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        String invalidItem = " "; 
        int newAmount = 5;
    
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accessLocal.addItem(1, invalidItem, newAmount);
        }, "Item name not valid");
    }
    
    /**
     * Tests the {@link MachineTrackerAccessLocal#addItem(int, String, int)} method to ensure it throws an exception when provided with an invalid item amount.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>The provided item amount is not valid (e.g., zero or a negative value).</li>
     *   <li>The method is expected to throw an {@link IllegalArgumentException} with a message indicating that the amount has to be higher than zero.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code addItem} method with a valid vending machine ID, a valid item name, and an invalid item amount (zero).</li>
     *   <li>Assert: Verify that an IllegalArgumentException is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws IllegalArgumentException When an invalid item amount is provided.
     */
    @Test
    public void MachineTrackerAccessLocal_addItem_throwsExceptionForInvalidAmount() {
        
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        String newItem = "Water";
        int invalidAmount = 0;
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accessLocal.addItem(1, newItem, invalidAmount);
        }, "Amount has to be higher than zero");
    }
    
    /**
     * Tests the {@link MachineTrackerAccessLocal#removeItem(int, String, int)} method to ensure it correctly removes an item with a specified amount from a vending machine's inventory.
     * 
     * This test case checks:
     * <ul>
     *   <li>If the method removes the specified item and amount from the vending machine's inventory in the persistence layer.</li>
     *   <li>If the returned inventory contains the item with the updated quantity after removal.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create a mock inventory for the vending machine containing an existing item ("Water" with a quantity of 10) and set it as the initial inventory state. Mock the persistence layer to return a predefined machine tracker instance with this vending machine.</li>
     *   <li>Act: Call the {@code removeItem} method with a valid vending machine ID, an existing item name, and a valid amount to remove (5 units).</li>
     *   <li>Assert: Verify that the returned inventory contains the item with the updated quantity after removal (5 units).</li>
     * </ol>
     * 
    */
    @Test
    public void MachineTrackerAccessLocal_removeItem_removesItemForValidIdItemAmount() {
       
        // Arrange
        HashMap<String, Integer> mockInventory = new HashMap<>();
        String existingItem = "Water";
        mockInventory.put(existingItem, 10);
        machine.setStatus(mockInventory);
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        int removeAmount = 5;
    
        // Act
        HashMap<String, Integer> resultInventory = accessLocal.removeItem(1, existingItem, removeAmount);
    
        // Assert
        assertTrue(resultInventory.containsKey(existingItem));
        assertEquals(5, resultInventory.get(existingItem).intValue()); 
    }
    

     /**
     * Tests the {@link MachineTrackerAccessLocal#removeItem(int, String, int)} method to ensure it throws an exception when trying to remove an item that is not in the vending machine's inventory.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>The provided item name is not found in the vending machine's inventory.</li>
     *   <li>The method is expected to throw an {@link IllegalArgumentException} with a message indicating that the vending machine's inventory does not contain the item.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create an empty mock inventory for the vending machine and set it as the initial inventory state. Mock the persistence layer to return a predefined machine tracker instance with this vending machine.</li>
     *   <li>Act: Call the {@code removeItem} method with a valid vending machine ID, a non-existing item name, and a valid amount to remove (2 units).</li>
     *   <li>Assert: Verify that an IllegalArgumentException is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws IllegalArgumentException If an attempt is made to remove an item that is not in the vending machine's inventory.
     */
    @Test
    public void MachineTrackerAccessLocal_removeItem_throwsExceptionForItemNotInInventory() {
       
        // Arrange
        HashMap<String, Integer> mockInventory = new HashMap<>();
        machine.setStatus(mockInventory);
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        String nonExistingItem = "Cola";
        int removeAmount = 2;
    
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accessLocal.removeItem(1, nonExistingItem, removeAmount);
        }, "The vending machine's inventory does not contain this item");
    }

    /**
     * Tests the {@link MachineTrackerAccessLocal#removeItem(int, String, int)} method to ensure it throws an exception when trying to remove more of an item than is available in the vending machine's inventory.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>The provided item amount to remove exceeds the quantity available in the vending machine's inventory for that item.</li>
     *   <li>The method is expected to throw an {@link IllegalArgumentException} with a message indicating that the vending machine's inventory contains less than the given amount to remove of the specified item.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create a mock inventory for the vending machine containing an existing item ("Cola" with a quantity of 2) and set it as the initial inventory state. Mock the persistence layer to return a predefined machine tracker instance with this vending machine.</li>
     *   <li>Act: Call the {@code removeItem} method with a valid vending machine ID, an existing item name, and an amount to remove (5 units) that exceeds the available quantity.</li>
     *   <li>Assert: Verify that an IllegalArgumentException is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws IllegalArgumentException If an attempt is made to remove more of an item than is available in the vending machine's inventory.
     */
    @Test
    public void MachineTrackerAccessLocal_removeItem_throwsExceptionForRemovingMoreThanAvailable() {
    
        // Arrange
        HashMap<String, Integer> mockInventory = new HashMap<>();
        String existingItem = "Cola";
        mockInventory.put(existingItem, 2);  
        machine.setStatus(mockInventory);
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        int removeAmount = 5; 
    
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accessLocal.removeItem(1, existingItem, removeAmount);
        }, "The vending machine's inventory contains less than the given amount to remove of item: " + existingItem);
    }
    
    /**
     * Tests the {@link MachineTrackerAccessLocal#removeItem(int, String, int)} method to ensure it throws an exception when provided with an invalid item name.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>The provided item name is not valid (e.g., an empty or blank string).</li>
     *   <li>The method is expected to throw an {@link IllegalArgumentException} with a message indicating that the item name is not valid.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code removeItem} method with a valid vending machine ID, an invalid item name, and a valid amount to remove (2 units).</li>
     *   <li>Assert: Verify that an IllegalArgumentException is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws IllegalArgumentException If an attempt is made to remove an item with an invalid item name.
     */
    @Test
    public void MachineTrackerAccessLocal_removeItem_throwsExceptionForInvalidItem() {
       
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        String invalidItem = " "; 
        int removeAmount = 2;
    
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accessLocal.removeItem(1, invalidItem, removeAmount);
        }, "Item name not valid");
    }
    
    /**
     * Tests the {@link MachineTrackerAccessLocal#removeItem(int, String, int)} method to ensure it throws an exception when provided with an invalid item amount.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>The provided item amount is not valid (e.g., zero or a negative value).</li>
     *   <li>The method is expected to throw an {@link IllegalArgumentException} with a message indicating that the amount has to be higher than zero.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code removeItem} method with a valid vending machine ID, an existing item name, and an invalid item amount (zero).</li>
     *   <li>Assert: Verify that an IllegalArgumentException is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws IllegalArgumentException If an attempt is made to remove an item with an invalid item amount (zero or negative).
     */
    @Test
    public void MachineTrackerAccessLocal_removeItem_throwsExceptionForInvalidAmount() {
        
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        String existingItem = "Cola";
        int invalidAmount = 0;  // Assuming 0 is an invalid amount
    
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accessLocal.removeItem(1, existingItem, invalidAmount);
        }, "Amount has to be higher than zero");
    }

    /**
     * Tests the {@link MachineTrackerAccessLocal#addVendMach(int, String)} method to ensure it correctly adds a vending machine with a specified ID and location to the vending machine list.
     * 
     * This test case checks:
     * <ul>
     *   <li>If the method successfully adds the specified vending machine (ID and location) to the vending machine list.</li>
     *   <li>If the returned vending machine list contains the newly added vending machine with the correct ID and location.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code addVendMach} method with a valid vending machine ID (newId) and a valid location (newLocation).</li>
     *   <li>Assert: Verify that the returned vending machine list contains the newly added vending machine with the expected ID and location.</li>
     * </ol>
     * 
     */
    @Test
    public void MachineTrackerAccessLocal_addVendMach_addsVendMachForValidIdLocation() {
        
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        int newId = 2;
        String newLocation = "Trondheim";

        // Act
        HashMap<Integer, String> resultMachList = accessLocal.addVendMach(newId, newLocation);

        // Assert
        assertTrue(resultMachList.containsKey(newId));
        assertEquals(newLocation, resultMachList.get(newId));
    }

    /**
     * Tests the {@link MachineTrackerAccessLocal#addVendMach(int, String)} method to ensure it throws an exception when attempting to add a vending machine with an existing ID.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>An attempt is made to add a vending machine with an ID that already exists in the vending machine list.</li>
     *   <li>The method is expected to throw an {@link IllegalArgumentException} with a message indicating that a vending machine with the same ID already exists.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code addVendMach} method with an existing vending machine ID (existingId) and a valid location (location).</li>
     *   <li>Assert: Verify that an IllegalArgumentException is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws IllegalArgumentException If an attempt is made to add a vending machine with an existing ID.
     */
    @Test
    public void MachineTrackerAccessLocal_addVendMach_throwsExceptionForExistingId() {
       
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        int existingId = 1;
        String location = "Trondheim";
    
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accessLocal.addVendMach(existingId, location);
        }, "A vending machine with id " + existingId + " already exists");
    }

    /**
     * Tests the {@link MachineTrackerAccessLocal#addVendMach(int, String)} method to ensure it throws an exception when provided with an invalid vending machine ID.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>An attempt is made to add a vending machine with an invalid ID (e.g., a negative value).</li>
     *   <li>The method is expected to throw an {@link IllegalArgumentException} with a message indicating that the ID is not valid.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code addVendMach} method with an invalid vending machine ID (invalidId) and a valid location (location).</li>
     *   <li>Assert: Verify that an IllegalArgumentException is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws IllegalArgumentException If an attempt is made to add a vending machine with an invalid ID (e.g., negative).
     */
    @Test
    public void MachineTrackerAccessLocal_addVendMach_throwsExceptionForInvalidId() {
        
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        int invalidId = -1; 
        String location = "Trondheim";
    
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accessLocal.addVendMach(invalidId, location);
        }, "Id not valid");
    }

    /**
     * Tests the {@link MachineTrackerAccessLocal#addVendMach(int, String)} method to ensure it throws an exception when provided with an invalid vending machine location.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>An attempt is made to add a vending machine with an invalid location (e.g., a numeric value).</li>
     *   <li>The method is expected to throw an {@link IllegalArgumentException} with a message indicating that the location name is not valid.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code addVendMach} method with a valid vending machine ID (newId) and an invalid location name (invalidLocation).</li>
     *   <li>Assert: Verify that an IllegalArgumentException is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws IllegalArgumentException If an attempt is made to add a vending machine with an invalid location name (e.g., numeric).
     */
    @Test
    public void MachineTrackerAccessLocal_addVendMach_throwsExceptionForInvalidLocation() {
       
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        int newId = 2;
        String invalidLocation = "1";  
    
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accessLocal.addVendMach(newId, invalidLocation);
        }, "Location name not valid");
    }

    /**
     * Tests the {@link MachineTrackerAccessLocal#removeVendMach(int)} method to ensure it correctly removes a vending machine with a specified ID from the vending machine list.
     * 
     * This test case checks:
     * <ul>
     *   <li>If the method successfully removes the vending machine with the specified ID from the vending machine list.</li>
     *   <li>If the returned vending machine list no longer contains the removed vending machine with the correct ID.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code removeVendMach} method with an existing vending machine ID (existingId).</li>
     *   <li>Assert: Verify that the returned vending machine list no longer contains the removed vending machine with the specified ID.</li>
     * </ol>
     * 
     */
    @Test
    public void MachineTrackerAccessLocal_removeVendMach_removesVendMachForValidId() {
        
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        int existingId = 1;

        // Act
        HashMap<Integer, String> resultMachList = accessLocal.removeVendMach(existingId);

        // Assert
        assertFalse(resultMachList.containsKey(existingId));
    }
    
    /**
     * Tests the {@link MachineTrackerAccessLocal#removeVendMach(int)} method to ensure it throws an exception when attempting to remove a vending machine with an invalid ID.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>An attempt is made to remove a vending machine with an ID that does not exist in the vending machine list.</li>
     *   <li>The method is expected to throw an {@link IllegalArgumentException} with a message indicating that no such vending machine exists for the given ID.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code removeVendMach} method with a non-existing vending machine ID (nonExistingId).</li>
     *   <li>Assert: Verify that an IllegalArgumentException is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws IllegalArgumentException If an attempt is made to remove a vending machine with an invalid ID (non-existent).
     */
    @Test
    public void MachineTrackerAccessLocal_removeVendMach_throwsExceptionForInvalidId() {
        
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        int nonExistingId = 9999;
    
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accessLocal.removeVendMach(nonExistingId);
        }, "No such Vending Machine with ID: " + nonExistingId);
    }
    
    /**
     * Tests the {@link MachineTrackerAccessLocal#changeLocation(int, String)} method to ensure it correctly changes the location of a vending machine with a specified ID in the vending machine list.
     * 
     * This test case checks:
     * <ul>
     *   <li>If the method successfully changes the location of the vending machine with the specified ID in the vending machine list.</li>
     *   <li>If the returned vending machine list contains the vending machine with the updated location for the specified ID.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code changeLocation} method with an existing vending machine ID (existingId) and a new valid location (newLocation).</li>
     *   <li>Assert: Verify that the returned vending machine list contains the vending machine with the updated location for the specified ID.</li>
     * </ol>
     *
     */
    @Test
    public void MachineTrackerAccessLocal_changeLocation_changesLocationForValidId() {
       
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        int existingId = 1;
        String newLocation = "Berlin";
    
        // Act
        HashMap<Integer, String> resultMachList = accessLocal.changeLocation(existingId, newLocation);
    
        // Assert
        assertEquals(newLocation, resultMachList.get(existingId));
    }
   
    /**
     * Tests the {@link MachineTrackerAccessLocal#changeLocation(int, String)} method to ensure it throws an exception when attempting to change the location of a vending machine with an invalid ID.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>An attempt is made to change the location of a vending machine with an ID that does not exist in the vending machine list.</li>
     *   <li>The method is expected to throw an {@link IllegalArgumentException} with a message indicating that no such vending machine exists for the given ID.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code changeLocation} method with a non-existing vending machine ID (nonExistingId) and a valid new location (newLocation).</li>
     *   <li>Assert: Verify that an IllegalArgumentException is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws IllegalArgumentException If an attempt is made to change the location of a vending machine with an invalid ID (non-existent).
     */
    @Test
    public void MachineTrackerAccessLocal_changeLocation_throwsExceptionForInvalidId() {
   
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        int nonExistingId = 10;
        String newLocation = "Trondheim";
    
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accessLocal.changeLocation(nonExistingId, newLocation);
        }, "No such Vending Machine with ID: " + nonExistingId);
    }

    /**
     * Tests the {@link MachineTrackerAccessLocal#changeLocation(int, String)} method to ensure it throws an exception when attempting to change the location of a vending machine with an invalid location name.
     * 
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>An attempt is made to change the location of a vending machine with an invalid location name (e.g., a numeric value).</li>
     *   <li>The method is expected to throw an {@link IllegalArgumentException} with a message indicating that the location name is not valid.</li>
     * </ul>
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the persistence layer to return a predefined machine tracker instance.</li>
     *   <li>Act: Call the {@code changeLocation} method with an existing vending machine ID (existingId) and an invalid location name (invalidLocation).</li>
     *   <li>Assert: Verify that an IllegalArgumentException is thrown with the appropriate error message.</li>
     * </ol>
     * 
     * @throws IllegalArgumentException If an attempt is made to change the location of a vending machine with an invalid location name (e.g., numeric).
     */
    @Test
    public void MachineTrackerAccessLocal_changeLocation_throwsExceptionForInvalidLocation() {
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);
        int existingId = 1;
        String invalidLocation = "1234";
    
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accessLocal.changeLocation(existingId, invalidLocation);
        }, "Location name not valid");
    }
}
