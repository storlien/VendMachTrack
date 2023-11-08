package gr2338.vendmachtrack.core.model;

import gr2338.vendmachtrack.core.model.VendingMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class contains test methods for testing the functionality of the {@link VendingMachine} class.
 * <p>
 * The tests in this class focus on various aspects of the VendingMachine class, including its initialization, adding and removing items, and more.
 */
public class VendingMachineTest {

    private VendingMachine machine;
    private HashMap<String, Integer> inventory;

    /**
     * Sets up the test fixture. This method is called before each test case method is executed.
     * It initializes the inventory with some items and their quantities, creates a new VendingMachine object
     * with the given inventory, and sets the location of the machine to "Lobby".
     */
    @BeforeEach
    public void setUp() {
        inventory = new HashMap<>();
        inventory.put("Soda", 5);
        inventory.put("Chips", 10);
        machine = new VendingMachine(1, inventory, "Lobby");
    }


    /**
     * Tests the initialization of a {@link VendingMachine} object.
     * <p>
     * This test case focuses on verifying that a VendingMachine object is initialized with the correct attributes, including its ID, location, and initial status of products.
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Assert: Verify that the VendingMachine object has an ID of 1, a location of "Lobby," and contains initial product status for "Soda" and "Chips."</li>
     * </ol>
     */
    @Test
    public void VendingMachine_testInitialization() {

        // Assert
        assertEquals(1, machine.getId());
        assertEquals("Lobby", machine.getLocation());
        assertTrue(machine.getStatus().containsKey("Soda"));
        assertTrue(machine.getStatus().containsKey("Chips"));
    }


    /**
     * Tests the {@link VendingMachine#addItem(String, int)} method to ensure it adds a new item to the machine's status.
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Act: Call the addItem("Water", 7) method on the VendingMachine object to add a new item "Water" with a quantity of 7.</li>
     *   <li>Assert: Verify that the machine's status contains the new item "Water" and that its quantity is 7.</li>
     * </ol>
     */
    @Test
    public void VendingMachine_testAddNewItem() {

        // Act
        machine.addItem("Water", 7);

        // Assert
        assertTrue(machine.getStatus().containsKey("Water"));
        assertEquals(7, machine.getStatus().get("Water"));
    }


    /**
     * Tests the {@link VendingMachine#addItem(String, int)} method to ensure it updates the quantity of an existing item in the machine's status.
     *
     * <p>
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Act: Call the addItem("Soda", 2) method on the VendingMachine object to update the quantity of the existing item "Soda" to 2.</li>
     *   <li>Assert: Verify that the quantity of the "Soda" item in the machine's status is updated to 7 (original quantity + added quantity).</li>
     * </ol>
     */
    @Test
    public void VendingMachine_testAddExistingItem() {

        // Act
        machine.addItem("Soda", 2);

        // Assert
        assertEquals(7, machine.getStatus().get("Soda"));
    }

    /**
     * Tests the {@link VendingMachine#removeItem(String, int)} method to ensure it removes items from the machine's status correctly.
     *
     * <p>
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Act: Call the removeItem("Soda", 2) method on the VendingMachine object to remove 2 units of the "Soda" item from its status.</li>
     *   <li>Assert: Verify that the quantity of the "Soda" item in the machine's status is reduced by 2 units.</li>
     * </ol>
     */
    @Test
    public void VendingMachine_testRemoveItem() {

        // Act
        machine.removeItem("Soda", 2);

        // Assert
        assertEquals(3, machine.getStatus().get("Soda"));
    }

    /**
     * Tests the {@link VendingMachine#removeItem(String, int)} method to ensure it removes all units of an item from the machine's status correctly.
     *
     * <p>
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Act: Call the removeItem("Soda", 5) method on the VendingMachine object to remove all units of the "Soda" item from its status.</li>
     *   <li>Assert: Verify that the "Soda" item is no longer present in the machine's status after removal.</li>
     * </ol>
     */
    @Test
    public void VendingMachine_testRemoveAllOfItem() {

        // Act
        machine.removeItem("Soda", 5);

        // Assert
        assertFalse(machine.getStatus().containsKey("Soda"));
    }

    /**
     * Tests the {@link VendingMachine#removeItem(String, int)} method to ensure it returns false when attempting to remove an item that does not exist in the machine's status.
     * <p>
     * This test case focuses on verifying that the removeItem() method of a {@link VendingMachine} object returns false when attempting to remove an item that is not present in its status.
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Act & Assert: Attempt to remove 10 units of the "Sodanotexist" item from the VendingMachine object's status and assert that it returns false, indicating that the item does not exist.</li>
     * </ol>
     */
    @Test
    public void VendingMachine_testRemoveItemNotExists() {

        // Act & Assert
        assertFalse(machine.removeItem("Sodanotexist", 10), "Should return false if item does not exist");
    }

    /**
     * Tests the {@link VendingMachine#removeItem(String, int)} method to ensure it returns false when attempting to remove more units of an item than are available in the machine's status.
     *
     * <p>
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Act &amp; Assert: Attempt to remove 6 units of the "Soda" item from the VendingMachine object's status, which only has 5 units, and assert that it returns false, indicating that there are not enough items to remove.</li>
     * </ol>
     */
    @Test
    public void VendingMachine_testRemoveItemNotEnough() {

        // Act & Assert
        assertFalse(machine.removeItem("Soda", 6), "Should return false if not enough items");
    }

    /**
     * Tests the {@link VendingMachine#setStatus(Map)} method to ensure it sets the machine's status correctly with a new inventory.
     *
     * <p>
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create a new inventory {@link HashMap} (newInventory) containing a different item ("Candy") and quantity (15).</li>
     *   <li>Act: Call the setStatus(newInventory) method on the VendingMachine object to set its status with the new inventory.</li>
     *   <li>Assert: Verify that the machine's status now contains the new item ("Candy") and that the original item ("Soda") is no longer present.</li>
     * </ol>
     */
    @Test
    public void VendingMachine_testSetStatus() {

        // Arrange
        HashMap<String, Integer> newInventory = new HashMap<>();
        newInventory.put("Candy", 15);

        // Act
        machine.setStatus(newInventory);

        // Assert
        assertTrue(machine.getStatus().containsKey("Candy"));
        assertFalse(machine.getStatus().containsKey("Soda"));
    }

    /**
     * Tests the {@link VendingMachine#setId(int)} method to ensure it sets the machine's ID correctly.
     *
     * <p>
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Act: Call the setId(2) method on the VendingMachine object to set its ID to 2.</li>
     *   <li>Assert: Verify that the machine's ID is updated to 2.</li>
     * </ol>
     */
    @Test
    public void VendingMachine_testSetId() {

        // Act
        machine.setId(2);

        // Assert
        assertEquals(2, machine.getId());
    }

    /**
     * Tests the {@link VendingMachine#setLocation(String)} method to ensure it sets the machine's location correctly.
     *
     * <p>
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Act: Call the setLocation("Office") method on the VendingMachine object to set its location to "Office".</li>
     *   <li>Assert: Verify that the machine's location is updated to "Office".</li>
     * </ol>
     */
    @Test
    public void VendingMachine_testSetLocation() {

        // Act
        machine.setLocation("Office");

        // Assert
        assertEquals("Office", machine.getLocation());
    }
}