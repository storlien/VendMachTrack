package vendmachtrack.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MachineTrackerTest {

    private MachineTracker tracker;
    private VendingMachine machine1;
    private VendingMachine machine2;

    /**
     * Sets up the test fixture before each test method runs.
     * <p>
     * Initializes the MachineTracker object and two VendingMachine objects.
     */
    @BeforeEach
    public void setUp() {
        tracker = new MachineTracker();
        machine1 = new VendingMachine();
        machine2 = new VendingMachine();
    }

    /**
     * Tests the addVendingMachine() method of the MachineTracker class.
     * Adds a vending machine to the tracker and checks if it is contained in the list of machines.
     */
    @Test
    public void testAddVendingMachine() {
        tracker.addVendingMachine(machine1);
        assertTrue(tracker.getMachines().contains(machine1));
    }

    /**
     * Tests the behavior of the addVendingMachine() method when a vending machine with the same ID already exists in the tracker.
     */
    @Test
    public void testAddVendingMachineAlreadyExists() {
        // Adds a vending machine to the tracker
        tracker.addVendingMachine(machine1);
        // Asserts that adding the same vending machine again throws an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> tracker.addVendingMachine(machine1));
    }

    /**
     * Tests the functionality of removing a vending machine from the tracker.
     * Adds a vending machine to the tracker, removes it, and checks that it is no longer in the tracker.
     */
    @Test
    public void testRemoveVendingMachine() {
        tracker.addVendingMachine(machine1);
        tracker.removeVendingMachine(machine1);
        assertFalse(tracker.getMachines().contains(machine1));
    }

    /**
     * Tests the removeVendingMachine method of the MachineTracker class when the vending machine does not exist.
     * Expects an IllegalArgumentException to be thrown.
     */
    @Test
    public void testRemoveVendingMachineNotExists() {
        assertThrows(IllegalArgumentException.class, () -> tracker.removeVendingMachine(machine2));
    }

    /**
     * Tests the {@link MachineTracker#getMachines()} method.
     */
    @Test
    public void testGetMachines() {
        tracker.addVendingMachine(machine1);
        List<VendingMachine> machines = tracker.getMachines();
        assertTrue(machines.contains(machine1));
        assertEquals(1, machines.size());
    }

    /**
     * Tests the setMachines() method of the MachineTracker class.
     *
     * <p>Sets two vending machines using the setMachines() method and then checks if the machines
     * are contained in the list of machines returned by the getMachines() method. The test passes
     * if both machines are contained in the list and the size of the list is 2.</p>
     */
    @Test
    public void testSetMachines() {
        tracker.setMachines(Arrays.asList(machine1, machine2));
        List<VendingMachine> machines = tracker.getMachines();
        assertTrue(machines.contains(machine1));
        assertTrue(machines.contains(machine2));
        assertEquals(2, machines.size());
    }
}

