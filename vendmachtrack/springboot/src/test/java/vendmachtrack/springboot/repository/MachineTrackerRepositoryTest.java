package vendmachtrack.springboot.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import vendmachtrack.core.model.MachineTracker;
import vendmachtrack.core.model.VendingMachine;
import vendmachtrack.jsonio.VendmachtrackPersistence;

import java.util.*;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
     * Test case for MachineTrackerRepository's getVendmachtrack method.
     * It Verifies that the method returns the expected MachineTracker object.
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
     * Tests the getVendMach method of the MachineTrackerRepository class.
     * It verifies that the method returns the expected VendingMachine object.
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
     * Tests the getVendMach method of the MachineTrackerRepository class when the
     * machine is not found.
     * Given an id, the method should return null if the machine is not found in the
     * database.
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
     * Tests the saveVendmachtrack method of the MachineTrackerRepository class.
     * It verifies that the method saves and returns a MachineTracker object
     * correctly.
     */
    @Test
    public void MachineTrackerRepository_SaveVendmachtrack_savesAndRetursMAchinetracker() {

        // Arrange
        machineTracker.setMachines(machines);

        // Act
        MachineTracker returnedMachineTracker = machineTrackerRepository.saveVendmachtrack(machineTracker);

        // Assert
        verify(persistence, times(1)).saveVendmachtrack(machineTracker); // check that saveVendmachtrack was called once
        // with the correct parameter
        assertSame(machineTracker, returnedMachineTracker); // check that the returned object is the same as the one
        // passed in
    }

    /**
     * Tests the addVendMach method of the MachineTrackerRepository class.
     * It verifies that the method adds a vending machine to the list of machines in
     * the MachineTracker object.
     * It also verifies that the added vending machine has the correct id and
     * location.
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
     * Tests the addItem method of the MachineTrackerRepository class.
     * It verifies that the method adds an item to an existing vending machine and
     * updates the MachineTracker object accordingly.
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
     * Tests the addItem method of the MachineTrackerRepository class when the
     * vending machine does not exist.
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
     * Tests the changeLocation method of MachineTrackerRepository class.
     * It verifies that the method updates the location of a vending machine with
     * the given id.
     * It also verifies that the updated vending machine is saved in the
     * MachineTracker object.
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
     * Test case for the changeLocation method of MachineTrackerRepository class.
     * It tests the scenario where the vending machine with the given id does not
     * exist in the system.
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
     * Tests the removeVendMach method of the MachineTrackerRepository class.
     * It verifies that the method removes a vending machine from the list of
     * machines in the MachineTracker object,
     * saves the updated MachineTracker object to the persistence layer, and returns
     * the updated MachineTracker object.
     * It also verifies that the returned and saved MachineTracker objects are the
     * same and that the list of machines
     * in the updated MachineTracker object is empty.
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
        assertEquals(machineTracker, capturedMachineTracker); // ensure the returned and saved MachineTrackers are the
        // same
    }

    /**
     * Test case for the removeVendMach method of the MachineTrackerRepository
     * class.
     * Tests the scenario where the vendmach to be removed does not exist in the
     * MachineTracker.
     * <p>
     * The test sets up the necessary mocks and data, calls the removeVendMach
     * method with a non-existent VendindgMachineTracker id,
     * and verifies that the returned VendingMachineTracker is the same as the one
     * saved, and that the saved VendingMachineTracker
     * has the same machines as the original one.
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
        assertNotNull(result.getMachines());
        verify(persistence).saveVendmachtrack(machineTrackerCaptor.capture());
        MachineTracker capturedMachineTracker = machineTrackerCaptor.getValue();
        assertEquals(machineTracker, capturedMachineTracker); // ensure the returned and saved MachineTrackers are the
        // same
    }

    /**
     * Tests the removeItem method of the MachineTrackerRepository class.
     * It verifies that the method removes the specified item from the vending
     * machine and updates the status accordingly.
     * It also verifies that the updated vending machine is saved correctly in the
     * persistence layer.
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
        assertEquals(1, result.getStatus().get(item)); // 1 item should remain
        verify(persistence).saveVendmachtrack(machineTrackerCaptor.capture());
        MachineTracker capturedMachineTracker = machineTrackerCaptor.getValue();
        assertEquals(machineTracker, capturedMachineTracker); // ensure the returned and saved MachineTrackers are the
        // same

    }

    /**
     * Tests the removeItem method of the MachineTrackerRepository class when the
     * vending machine does not exist.
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
