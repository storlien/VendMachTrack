package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {

    private VendingMachine machine;
    private HashMap<String, Integer> inventory;

    @BeforeEach
    public void setUp() {
        inventory = new HashMap<>();
        inventory.put("Soda", 5);
        inventory.put("Chips", 10);
        machine = new VendingMachine(1, inventory, "Lobby");
    }

    @Test
    public void testInitialization() {
        assertEquals(1, machine.getId());
        assertEquals("Lobby", machine.getLocation());
        assertTrue(machine.getStatus().containsKey("Soda"));
        assertTrue(machine.getStatus().containsKey("Chips"));
    }

    @Test
    public void testAddNewItem() {
        machine.addItem("Water", 7);
        assertTrue(machine.getStatus().containsKey("Water"));
        assertEquals(7, machine.getStatus().get("Water"));
    }

    @Test
    public void testAddExistingItem() {
        machine.addItem("Soda", 2);
        assertEquals(7, machine.getStatus().get("Soda"));
    }

    @Test
    public void testRemoveItem() {
        machine.removeItem("Soda", 2);
        assertEquals(3, machine.getStatus().get("Soda"));
    }

    //@Test
    //public void testRemoveAllOfItem() {
      //  machine.removeItem("Soda", 5);
        //assertFalse(machine.getStatus().containsKey("Soda"));
    //}

    //@Test
    //public void testRemoveItemNotExists() {
      //  assertThrows(IllegalArgumentException.class, () -> machine.removeItem("Water", 2));
    //}

    @Test
    public void testSetStatus() {
        HashMap<String, Integer> newInventory = new HashMap<>();
        newInventory.put("Candy", 15);
        machine.setStatus(newInventory);
        assertTrue(machine.getStatus().containsKey("Candy"));
        assertFalse(machine.getStatus().containsKey("Soda"));
    }

    @Test
    public void testSetId() {
        machine.setId(2);
        assertEquals(2, machine.getId());
    }

    @Test
    public void testSetLocation() {
        machine.setLocation("Office");
        assertEquals("Office", machine.getLocation());
    }

    @Test
    public void testToString() {
        assertEquals("Lobby, ID: 1", machine.toString());
    }
}


