package gr2338.vendmachtrack.springboot.repository;

import gr2338.vendmachtrack.springboot.repository.MachineTrackerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import gr2338.vendmachtrack.core.model.MachineTracker;
import gr2338.vendmachtrack.core.model.VendingMachine;
import gr2338.vendmachtrack.jsonio.VendmachtrackPersistence;

import java.util.*;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This class contains unit tests for the {@link MachineTrackerRepository} class. It focuses on testing various methods of the repository that interact with vending machines and the {@link MachineTracker} object.
 * <p>
 * The tests are implemented using the JUnit 5 framework and Mockito. The {@link MachineTrackerRepository} class is tested in isolation from the rest of the application.
 */
public class MachineTrackerRepositoryTest {

    private final VendmachtrackPersistence persistence = Mockito.mock(VendmachtrackPersistence.class);
    private final MachineTrackerRepository machineTrackerRepository = new MachineTrackerRepository("testFileName");
    private final ArgumentCaptor<MachineTracker> machineTrackerCaptor = ArgumentCaptor.forClass(MachineTracker.class);

    private final VendingMachine vendingmachine = new VendingMachine();
    private final List<VendingMachine> machines = new ArrayList<>();
    private final MachineTracker machineTracker = new MachineTracker();

    /**
     * Sets up the test fixture before each test method is run.
     * Initializes the Mockito annotations and sets up the vending machine object
     * with an ID of 1 and a location of "Oslo".
     * Adds the vending machine object to the list of machines.
     */
    @BeforeEach
    public void setUp() {

        ReflectionTestUtils.setField(machineTrackerRepository, "persistence", persistence);
        vendingmachine.setId(1);
        vendingmachine.setLocation("Oslo");
        machines.add(vendingmachine);

    }

    /**
     * Tests the {@link MachineTrackerRepository#getVendmachtrack()} method of the {@link MachineTrackerRepository} class.
     *
     * <p>
     * This test case verifies that the {@link MachineTrackerRepository#getVendmachtrack()} method returns the expected {@link MachineTracker} object.
     * </p>
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including a {@link MachineTracker} object with machines.</li>
     *   <li>Act: Call the {@link MachineTrackerRepository#getVendmachtrack()} method to retrieve the actual {@link MachineTracker} object.</li>
     *   <li>Assert: Verify that the actual {@link MachineTracker} object matches the expected one and that the {@link MachinePersistence} is called once with the expected method.</li>
     * </ol>
     */
    @Test
    public void MachineTrackerRepository_getVendmachtrack_ReturnMachineTracker() {

        // Arrange
        machineTracker.setMachines(machines);
        when(persistence.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        MachineTracker actualMachineTracker = machineTrackerRepository.getVendmachtrack();

        // Assert
        assertEquals(machineTracker, actualMachineTracker);
        verify(persistence, times(1)).getVendmachtrack();
    }

    /**
     * Tests the {@link MachineTrackerRepository#getVendMach(int)} method of the {@link MachineTrackerRepository} class.
     *
     * <p>
     * This test case verifies that the {@link MachineTrackerRepository#getVendMach(int)} method returns the expected {@link VendingMachine} object when given a valid machine ID.
     * </p>
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including a {@link MachineTracker} object with machines and a valid machine ID.</li>
     *   <li>Act: Call the {@link MachineTrackerRepository#getVendMach(int)} method to retrieve the actual {@link VendingMachine} object.</li>
     *   <li>Assert: Verify that the actual {@link VendingMachine} object matches the expected one.</li>
     * </ol>
     */
    @Test
    public void MachineTrackerRepository_getVendMach_ReturnVendingMachine() {

        // Arrange
        int id = 1;
        machineTracker.setMachines(machines);
        when(persistence.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        VendingMachine actualVendingMachine = machineTrackerRepository.getVendMach(id);

        // Assert
        assertEquals(vendingmachine, actualVendingMachine);
    }

    /**
     * Tests the {@link MachineTrackerRepository#getVendMach(int)} method of the {@link MachineTrackerRepository} class when the machine is not found.
     *
     * <p>
     * Given an ID, this test case verifies that the {@link MachineTrackerRepository#getVendMach(int)} method returns {@code null} if the vending machine is not found in the database.
     * </p>
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including a {@link MachineTracker} object with an empty list of machines and an ID for a non-existent machine.</li>
     *   <li>Act: Call the {@link MachineTrackerRepository#getVendMach(int)} method to retrieve the actual {@link VendingMachine} object.</li>
     *   <li>Assert: Verify that the actual {@link VendingMachine} object is {@code null} as expected.</li>
     * </ol>
     */
    @Test
    public void MachineTrackerRepository_getVendMach_retrunsnull() {
        // Arrange
        int id = 1;
        machineTracker.setMachines(List.of());
        when(persistence.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        VendingMachine actualVendingMachine = machineTrackerRepository.getVendMach(id);

        // Assert
        assertNull(actualVendingMachine);
    }

    /**
     * Tests the {@link MachineTrackerRepository#saveVendmachtrack(MachineTracker)} method of the {@link MachineTrackerRepository} class.
     *
     * <p>
     * This test case verifies that the {@link MachineTrackerRepository#saveVendmachtrack(MachineTracker)} method correctly saves and returns a {@link MachineTracker} object.
     * </p>
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including a {@link MachineTracker} object with a list of machines.</li>
     *   <li>Act: Call the {@link MachineTrackerRepository#saveVendmachtrack(MachineTracker)} method to save the {@link MachineTracker} object.</li>
     *   <li>Assert: Verify that the method was called once with the correct parameter and that the returned {@link MachineTracker} object is the same as the one passed in.</li>
     * </ol>
     */
    @Test
    public void MachineTrackerRepository_SaveVendmachtrack_savesAndRetursMAchinetracker() {

        // Arrange
        machineTracker.setMachines(machines);

        // Act
        MachineTracker returnedMachineTracker = machineTrackerRepository.saveVendmachtrack(machineTracker);

        // Assert
        verify(persistence, times(1)).saveVendmachtrack(machineTracker);
        assertSame(machineTracker, returnedMachineTracker);
    }

    /**
     * Tests the {@link MachineTrackerRepository#addVendMach(int, String)} method of the {@link MachineTrackerRepository} class.
     *
     * <p>
     * This test case verifies that the {@link MachineTrackerRepository#addVendMach(int, String)} method correctly adds a vending machine to the list of machines in the {@link MachineTracker} object.
     * It also ensures that the added vending machine has the correct ID and location.
     * </p>
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the initial {@link MachineTracker} object with an empty list of machines and configure mock behavior for persistence methods.</li>
     *   <li>Act: Call the {@link MachineTrackerRepository#addVendMach(int, String)} method to add a vending machine.</li>
     *   <li>Assert: Verify that the {@link MachineTracker#saveVendmachtrack(MachineTracker)} method was called with the correct parameter, and ensure that the added vending machine has the expected ID and location.</li>
     * </ol>
     */
    @Test
    public void MachineTrackerRepository_addVendMach_addsVendingMachine() {

        // Arrange
        int id = 2;
        String location = "Trondheim";
        MachineTracker initialMachineTracker = new MachineTracker();
        initialMachineTracker.setMachines(new ArrayList<>());
        when(persistence.getVendmachtrack()).thenReturn(initialMachineTracker);
        when(persistence.saveVendmachtrack(any(MachineTracker.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        MachineTracker updatedMachineTracker = machineTrackerRepository.addVendMach(id, location);

        // Assert
        verify(persistence).saveVendmachtrack(machineTrackerCaptor.capture());
        MachineTracker capturedMachineTracker = machineTrackerCaptor.getValue();
        assertEquals(updatedMachineTracker, capturedMachineTracker);
        List<VendingMachine> vendingMachines = capturedMachineTracker.getMachines();
        assertEquals(1, vendingMachines.size());
        VendingMachine addedVendingMachine = vendingMachines.get(0);
        assertEquals(id, addedVendingMachine.getId());
        assertEquals(location, addedVendingMachine.getLocation());
    }


    /**
     * Tests the {@link MachineTrackerRepository#addItem(int, String, int)} method of the {@link MachineTrackerRepository} class.
     *
     * <p>
     * This test case verifies that the {@link MachineTrackerRepository#addItem(int, String, int)} method correctly adds an item to an existing vending machine and updates the {@link MachineTracker} object accordingly.
     * </p>
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up initial data, including the vending machine, item, and quantity, and configure mock behavior for persistence methods.</li>
     *   <li>Act: Call the {@link MachineTrackerRepository#addItem(int, String, int)} method to add an item to the vending machine.</li>
     *   <li>Assert: Verify that the method call returns a non-null result with the expected ID and quantity, and ensure that the updated {@link MachineTracker} object contains the modified vending machine.</li>
     * </ol>
     */
    @Test
    public void MachineTrackerRepository_addItem_addsItemToExistingVendingmachine() {

        // Arrange
        int id = 1;
        String item = "Cola";
        int quantity = 10;
        machineTracker.setMachines(machines);
        when(persistence.getVendmachtrack()).thenReturn(machineTracker);
        when(persistence.saveVendmachtrack(any(MachineTracker.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        VendingMachine result = machineTrackerRepository.addItem(id, item, quantity);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(quantity, result.getStatus().get(item).intValue());
        verify(persistence).saveVendmachtrack(machineTrackerCaptor.capture());
        MachineTracker capturedMachineTracker = machineTrackerCaptor.getValue();
        // verify that the captured MachineTracker contains the updated VendingMachine
        assertEquals(vendingmachine, capturedMachineTracker.getMachines().get(0));
    }

    /**
     * Tests the {@link MachineTrackerRepository#addItem(int, String, int)} method of the {@link MachineTrackerRepository} class when the vending machine does not exist.
     *
     * <p>
     * This test case verifies that the {@link MachineTrackerRepository#addItem(int, String, int)} method returns {@code null} when attempting to add an item to a non-existent vending machine.
     * </p>
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the test by configuring mock behavior to simulate an empty list of vending machines for the {@link MachineTracker} object.</li>
     *   <li>Act: Call the {@link MachineTrackerRepository#addItem(int, String, int)} method to add an item to the vending machine.</li>
     *   <li>Assert: Verify that the method returns {@code null} since the vending machine does not exist.</li>
     * </ol>
     */
    @Test
    public void MachineTrackerRepository_addItem_returnNullVendingMachinenotexist() {

        // Arrange
        int id = 1;
        String item = "Cola";
        int quantity = 10;
        machineTracker.setMachines(Collections.emptyList());
        when(persistence.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        VendingMachine result = machineTrackerRepository.addItem(id, item, quantity);

        // Assert
        assertNull(result);
    }

    /**
     * Tests the {@link MachineTrackerRepository#changeLocation(int, String)} method of the {@link MachineTrackerRepository} class.
     *
     * <p>
     * This test case verifies that the {@link MachineTrackerRepository#changeLocation(int, String)} method correctly updates the location of a vending machine with the given ID
     * and ensures that the updated vending machine is saved in the {@link MachineTracker} object.
     * </p>
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the test by configuring mock behavior to simulate an existing vending machine with the specified ID, as well as setting the new location and configuring save behavior for the {@link MachineTracker} object.</li>
     *   <li>Act: Call the {@link MachineTrackerRepository#changeLocation(int, String)} method to change the location of the vending machine.</li>
     *   <li>Assert: Verify that the method returns the updated vending machine with the correct ID and location. Also, ensure that the updated vending machine is saved in the {@link MachineTracker} object.</li>
     * </ol>
     */
    @Test
    public void MachineTrackerRepository_changeLocation_changeLocationOnVendingMachine() {

        // Arrange
        int id = 1;
        String newLocation = "Trondheim";
        machineTracker.setMachines(machines);
        when(persistence.getVendmachtrack()).thenReturn(machineTracker);
        when(persistence.saveVendmachtrack(any(MachineTracker.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        VendingMachine result = machineTrackerRepository.changeLocation(id, newLocation);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(newLocation, result.getLocation());
        verify(persistence).saveVendmachtrack(machineTrackerCaptor.capture());
        MachineTracker capturedMachineTracker = machineTrackerCaptor.getValue();
        // verify that the captured MachineTracker contains the updated VendingMachine
        assertEquals(vendingmachine, capturedMachineTracker.getMachines().get(0));
    }

    /**
     * Test case for the {@link MachineTrackerRepository#changeLocation(int, String)} method of the {@link MachineTrackerRepository} class.
     *
     * <p>
     * This test case covers the scenario where the vending machine with the given ID does not exist in the system.
     * It verifies that the method correctly returns null when attempting to change the location of a non-existent vending machine.
     * </p>
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the test by configuring mock behavior to simulate an existing list of vending machines and specify a non-existent vending machine ID and a new location.</li>
     *   <li>Act: Call the {@link MachineTrackerRepository#changeLocation(int, String)} method to attempt to change the location of the non-existent vending machine.</li>
     *   <li>Assert: Verify that the method returns null, indicating that no changes were made since the vending machine does not exist in the system.</li>
     * </ol>
     */
    @Test
    public void MachineTrackerRepository_changeLocation_returnNullVendingMachineNotExist() {

        // Arrange
        int id = 10;
        String newLocation = "Wrong Location";
        machineTracker.setMachines(machines);
        when(persistence.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        VendingMachine result = machineTrackerRepository.changeLocation(id, newLocation);

        // Assert
        assertNull(result);
    }

    /**
     * Tests the {@link MachineTrackerRepository#removeVendMach(int)} method of the {@link MachineTrackerRepository} class.
     *
     * <p>
     * This test verifies that the method removes a vending machine from the list of machines in the {@link MachineTracker} object..
     * </p>
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the test by configuring mock behavior to simulate an existing list of vending machines, specifying a vending machine ID to remove, and configuring mock behavior for saving the updated {@link MachineTracker} object.</li>
     *   <li>Act: Call the {@link MachineTrackerRepository#removeVendMach(int)} method to remove the vending machine.</li>
     *   <li>Assert: Verify that the vending machine is reoved form the machinetracker. </li>
     * </ol>
     */
    @Test
    public void MachineTrackerRepository_removeVendMach_removesVendingmachine() {

        // Arrange
        int id = 1;
        machineTracker.setMachines(machines);
        when(persistence.getVendmachtrack()).thenReturn(machineTracker);
        when(persistence.saveVendmachtrack(any(MachineTracker.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        MachineTracker result = machineTrackerRepository.removeVendMach(id);

        // Assert
        assertEquals(machineTracker, result);
        assertTrue(result.getMachines().isEmpty());
        verify(persistence).saveVendmachtrack(machineTrackerCaptor.capture());
        MachineTracker capturedMachineTracker = machineTrackerCaptor.getValue();
        assertEquals(machineTracker, capturedMachineTracker);
    }

    /**
     * Test case for the {@link MachineTrackerRepository#removeVendMach(int)} method of the {@link MachineTrackerRepository} class.
     *
     * <p>
     * Tests the scenario where the vending machine to be removed does not exist in the {@link MachineTracker}.
     * </p>
     *
     * <p>
     * The test sets up the necessary mocks and data, calls the {@link MachineTrackerRepository#removeVendMach(int)} method with a non-existent vending machine ID,
     * and verifies that the returned {@link MachineTracker} is the same as the one saved.
     * </p>
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the test by configuring mock behavior to simulate an existing list of vending machines, specifying a vending machine ID that does not exist in the list, and configuring mock behavior for saving the updated {@link MachineTracker} object.</li>
     *   <li>Act: Call the {@link MachineTrackerRepository#removeVendMach(int)} method to attempt to remove a non-existent vending machine.</li>
     *   <li>Assert: Verify that the method returns the same {@link MachineTracker} object as the one saved.</li>
     * </ol>
     */
    @Test
    public void MachineTrackerRepository_removeVendMach_removeVendmachNotExist() {

        // Arrange
        int id = 3;
        machineTracker.setMachines(machines);
        when(persistence.getVendmachtrack()).thenReturn(machineTracker);
        when(persistence.saveVendmachtrack(any(MachineTracker.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        MachineTracker result = machineTrackerRepository.removeVendMach(id);

        // Assert
        assertEquals(machineTracker, result);
        verify(persistence).saveVendmachtrack(machineTrackerCaptor.capture());
        MachineTracker capturedMachineTracker = machineTrackerCaptor.getValue();
        assertEquals(machineTracker, capturedMachineTracker);
    }


    /**
     * Test case for the {@link MachineTrackerRepository#removeItem(int, String, int)} method of the {@link MachineTrackerRepository} class.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the test by configuring mock behavior to simulate an existing vending machine with a specified item and quantity, configuring mock behavior for saving the updated {@link MachineTracker} object, and providing an existing vending machine ID.</li>
     *   <li>Act: Call the {@link MachineTrackerRepository#removeItem(int, String, int)} method to attempt to remove the specified quantity of the item from the vending machine.</li>
     *   <li>Assert: Verify that the method returns a non-null {@link VendingMachine} object with the updated status, the status of the removed item is as expected, and the {@link MachineTracker} object is saved correctly in the persistence layer.</li>
     * </ol>
     */
    @Test
    public void MachineTrackerRepository_removeitem_removesItemFromVendigMachine() {

        // Arrange
        int id = 1;
        String item = "Cola";
        int quantity = 5;
        HashMap<String, Integer> status = new HashMap<>();
        status.put(item, 6);

        vendingmachine.setStatus(status);
        MachineTracker machineTracker = new MachineTracker();
        machineTracker.setMachines(Collections.singletonList(vendingmachine));
        when(persistence.getVendmachtrack()).thenReturn(machineTracker);
        when(persistence.saveVendmachtrack(any(MachineTracker.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        VendingMachine result = machineTrackerRepository.removeItem(id, item, quantity);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(1, result.getStatus().get(item));
        verify(persistence).saveVendmachtrack(machineTrackerCaptor.capture());
        MachineTracker capturedMachineTracker = machineTrackerCaptor.getValue();
        assertEquals(machineTracker, capturedMachineTracker);

    }

    /**
     * Test case for the {@link MachineTrackerRepository#removeItem(int, String, int)} method of the {@link MachineTrackerRepository} class when the vending machine does not exist.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the test by configuring mock behavior to simulate an empty list of vending machines, specifying a vending machine ID that does not exist, and configuring mock behavior for retrieving the {@link MachineTracker} object.</li>
     *   <li>Act: Call the {@link MachineTrackerRepository#removeItem(int, String, int)} method to attempt to remove an item from a non-existent vending machine.</li>
     *   <li>Assert: Verify that the method returns a null {@link VendingMachine} object, indicating that the vending machine does not exist.</li>
     * </ol>
     */
    @Test
    public void MachineTrackerRepository_removeItem_removesItemVendingMachineNotExist() {

        // Arrange
        int id = 1;
        String item = "Cola";
        int quantity = 5;
        MachineTracker machineTracker = new MachineTracker();
        machineTracker.setMachines(List.of()); // no vending machines
        when(persistence.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        VendingMachine result = machineTrackerRepository.removeItem(id, item, quantity);

        // Assert
        assertNull(result);
    }

}
