package gr2338.vendmachtrack.core.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class contains test methods for testing the functionality of the {@link MachineTracker} class.
 * <p>
 * The tests in this class focus on various aspects of the MachineTracker class, including adding and removing vending machines, retrieving the list of machines, and more.
 */
public class MachineTrackerTest {

    private MachineTracker tracker;
    private VendingMachine machine1;
    private VendingMachine machine2;

    /**
     * Sets up the initial state for each test method in the {@link MachineTrackerTest} class.
     *
     * <p>
     * <p>
     * To prepare for each test, the following steps are performed:
     * </p>
     * <ol>
     *   <li>Initialize a new {@link MachineTracker} object to create a new tracker for each test.</li>
     *   <li>Create two {@link VendingMachine} objects (machine1 and machine2) that can be used in the test methods.</li>
     * </ol>
     */
    @BeforeEach
    public void setUp() {
        tracker = new MachineTracker();
        machine1 = new VendingMachine();
        machine2 = new VendingMachine();
    }


    /**
     * Tests the {@link MachineTracker#addVendingMachine(VendingMachine)} method to ensure it adds a vending machine to the tracker's list.
     * <p>
     * This test case focuses on verifying that the addVendingMachine() method of a {@link MachineTracker} object correctly adds a vending machine to its list of machines.
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Act: Call the addVendingMachine(machine1) method on the MachineTracker object to add machine1 to the list of machines.</li>
     *   <li>Assert: Verify that the list of machines (retrieved using getMachines()) contains machine1 after the addition.</li>
     * </ol>
     */
    @Test
    public void MachineTracker_testAddVendingMachine() {

        // Act
        tracker.addVendingMachine(machine1);

        // Assert
        assertTrue(tracker.getMachines().contains(machine1));
    }

    /**
     * Tests the {@link MachineTracker#addVendingMachine(VendingMachine)} method to ensure it throws an {@link IllegalArgumentException} when attempting to add a vending machine that already exists in the tracker's list.
     *
     * <p>
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create a {@link MachineTracker} object and add a {@link VendingMachine} object (machine1) to its list of machines.</li>
     *   <li>Act & Assert: Attempt to add the same machine1 again using the addVendingMachine() method and assert that it throws an {@link IllegalArgumentException}.</li>
     * </ol>
     */
    @Test
    public void MachineTracker_testAddVendingMachineAlreadyExists() {
        // Arrange
        tracker.addVendingMachine(machine1);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> tracker.addVendingMachine(machine1));
    }

    /**
     * Tests the {@link MachineTracker#removeVendingMachine(VendingMachine)} method to ensure it removes a vending machine from the tracker's list.
     *
     * <p>
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create a {@link MachineTracker} object and add a {@link VendingMachine} object (machine1) to its list of machines.</li>
     *   <li>Act: Call the removeVendingMachine(machine1) method on the MachineTracker object to remove machine1 from the list of machines.</li>
     *   <li>Assert: Verify that the list of machines (retrieved using getMachines()) does not contain machine1 after the removal.</li>
     * </ol>
     */
    @Test
    public void MachineTracker_testRemoveVendingMachine() {

        // Arrange
        tracker.addVendingMachine(machine1);

        // Act
        tracker.removeVendingMachine(machine1);

        // Assert
        assertFalse(tracker.getMachines().contains(machine1));
    }

    /**
     * Tests the {@link MachineTracker#removeVendingMachine(VendingMachine)} method to ensure it throws an {@link IllegalArgumentException} when attempting to remove a vending machine that does not exist in the tracker's list.
     *
     * <p>
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Act & Assert: Attempt to remove a {@link VendingMachine} object (machine2) from an empty list of machines in the MachineTracker object and assert that it throws an {@link IllegalArgumentException}.</li>
     * </ol>
     */
    @Test
    public void MachineTracker_testRemoveVendingMachineNotExists() {

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> tracker.removeVendingMachine(machine2));
    }


    /**
     * Tests the {@link MachineTracker#getMachines()} method to ensure it returns the list of vending machines correctly.
     *
     * <p>
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create a {@link MachineTracker} object and add a {@link VendingMachine} object (machine1) to its list of machines.</li>
     *   <li>Act: Call the getMachines() method on the MachineTracker object to retrieve the list of vending machines.</li>
     *   <li>Assert: Verify that the list of machines contains machine1 and has a size of 1, as expected.</li>
     * </ol>
     */
    @Test
    public void MachineTracker_testGetMachines() {
        // Arrange
        tracker.addVendingMachine(machine1);

        // Act
        List<VendingMachine> machines = tracker.getMachines();

        // Assert
        assertTrue(machines.contains(machine1));
        assertEquals(1, machines.size());
    }


    /**
     * Tests the {@link MachineTracker#setMachines(List)} method to ensure it sets the list of vending machines correctly.
     *
     * <p>
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create a {@link MachineTracker} object and set the list of vending machines to include {@link VendingMachine} objects (machine1 and machine2).</li>
     *   <li>Act: Call the getMachines() method on the MachineTracker object to retrieve the list of vending machines.</li>
     *   <li>Assert: Verify that the list of machines contains machine1, machine2, and has a size of 2, as expected.</li>
     * </ol>
     */
    @Test
    public void MachineTrakcer_testSetMachines() {

        // Arrange
        tracker.setMachines(Arrays.asList(machine1, machine2));

        // Act
        List<VendingMachine> machines = tracker.getMachines();

        // Assert
        assertTrue(machines.contains(machine1));
        assertTrue(machines.contains(machine2));
        assertEquals(2, machines.size());
    }
}

