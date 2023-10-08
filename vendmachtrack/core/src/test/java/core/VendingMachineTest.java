package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

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
     * Tests the initialization of the VendingMachine object.
     * 
     * This method tests if the VendingMachine object is initialized correctly by checking the following:
     * - The ID of the machine is 1
     * - The location of the machine is "Lobby"
     * - The machine contains a status for "Soda" and "Chips"
     */
    @Test
    public void testInitialization() {
        assertEquals(1, machine.getId());
        assertEquals("Lobby", machine.getLocation());
        assertTrue(machine.getStatus().containsKey("Soda"));
        assertTrue(machine.getStatus().containsKey("Chips"));
    }

    /**
     * Tests the addItem method of the VendingMachine class by adding a new item and verifying that it was added successfully.
     * 
     * <p>
     * This test adds a new item "Water" with a quantity of 7 to the vending machine and then checks that the vending machine's status map contains the item "Water" with a quantity of 7.
     * </p>
     */
    @Test
    public void testAddNewItem() {
        machine.addItem("Water", 7);
        assertTrue(machine.getStatus().containsKey("Water"));
        assertEquals(7, machine.getStatus().get("Water"));
    }

    /**
     * Tests the behavior of adding an existing item to the vending machine.
     * 
     * <p>
     * This test case adds a "Soda" item with a quantity of 2 to the vending machine, and then
     * verifies that the quantity of "Soda" in the vending machine is updated to 7, which is the
     * expected quantity after adding 2 more "Soda" items to the existing quantity of 5.
     * </p>
     */
    @Test
    public void testAddExistingItem() {
        machine.addItem("Soda", 2);
        assertEquals(7, machine.getStatus().get("Soda"));
    }

    /**
     * Tests the removeItem() method of the VendingMachine class.
     * It removes 2 items of the "Soda" product and checks if the status of "Soda" has decreased by 2.
     */
    @Test
    public void testRemoveItem() {
        machine.removeItem("Soda", 2);
        assertEquals(3, machine.getStatus().get("Soda"));
    }

    /**
     * Tests the removeItem() method of the VendingMachine class by removing all instances of a specific item and verifying that it is no longer present in the machine's status map.
     */
    //@Test
    //public void testRemoveAllOfItem() {
        //machine.removeItem("Soda", 5);
      //  assertFalse(machine.getStatus().containsKey("Soda"));
    //}

    /**
     * Tests the removeItem method of the VendingMachine class when the item to be removed does not exist.
     * Expects an IllegalArgumentException to be thrown.
     * 
     * @throws IllegalArgumentException if the item to be removed does not exist in the vending machine.
     * @see VendingMachine#removeItem(String, int)
     * 
     * @test_category vending_machine
     * @test_subcategory functionality
     * @since 1.0
     */
    //@Test
    //public void testRemoveItemNotExists() {
      //  assertThrows(IllegalArgumentException.class, () -> machine.removeItem("Water", 2));
    //}

    /**
     * Tests the setStatus method of the VendingMachine class.
     * 
     * Creates a new inventory HashMap with a single item, "Candy", and a quantity of 15.
     * The method then sets the VendingMachine's status to the new inventory.
     * 
     * Asserts that the VendingMachine's status contains the "Candy" key and does not contain the "Soda" key.
     */
    @Test
    public void testSetStatus() {
        HashMap<String, Integer> newInventory = new HashMap<>();
        newInventory.put("Candy", 15);
        machine.setStatus(newInventory);
        assertTrue(machine.getStatus().containsKey("Candy"));
        assertFalse(machine.getStatus().containsKey("Soda"));
    }

    /**
     * Tests the setId() method of the VendingMachine class.
     * 
     * This test sets the ID of the VendingMachine object to 2 using the setId() method,
     * and then checks if the ID has been set correctly by calling the getId() method.
     * The test passes if the returned ID matches the expected value of 2.
     */
    @Test
    public void testSetId() {
        machine.setId(2);
        assertEquals(2, machine.getId());
    }

    /**
     * Tests the setLocation() method of the VendingMachine class.
     * It sets the location of the vending machine to "Office" and then checks if the location has been set correctly.
     */
    @Test
    public void testSetLocation() {
        machine.setLocation("Office");
        assertEquals("Office", machine.getLocation());
    }

    /**
     * Tests the toString() method of the VendingMachine class.
     * 
     * This method tests if the toString() method of the VendingMachine class returns the expected string representation of the object.
     * The expected string representation is "Lobby, ID: 1".
     * 
     * @see VendingMachine#toString()
     */
    @Test
    public void testToString() {
        assertEquals("Lobby, ID: 1", machine.toString());
    }
}


