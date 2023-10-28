package vendmachtrack.springboot.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import vendmachtrack.core.MachineTracker;
import vendmachtrack.core.VendingMachine;
import vendmachtrack.springboot.exception.IllegalInputException;
import vendmachtrack.springboot.exception.ResourceNotFoundException;

import java.util.Collections;
import java.util.HashMap;

public class MachineTrackerServiceTest {

    @Mock
    private vendmachtrack.springboot.repository.MachineTrackerRepository repository;

    @InjectMocks
    private MachineTrackerService service;

    private MachineTracker machineTracker = new MachineTracker();
    private VendingMachine machine = new VendingMachine();

    /**
     * Sets up the test environment before each test case.
     * Initializes the Mockito annotations and sets the machine ID to 1, location to "Oslo",
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
     * Test case to verify the getVendMachList method of the MachineTrackerService class.
     * Verifies that the method returns a list of machines when the machine tracker is present.
     */
    @Test
    public void getVendMachList_whenMachineTrackerIsPresent_returnsListOfMachines() {
        // Arrange
        when(repository.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        HashMap<Integer,String> result = service.getVendMachList();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(1));
        assertEquals("Oslo", result.get(1));
    }

    /**
     * Test case for the getVendMachList method of the MachineTrackerService class when the machine tracker is not present.
     * Expects a ResourceNotFoundException to be thrown.
     */
    @Test
    public void getVendMachList_whenMachineTrackerIsNotPresent_thowsException(){

        // Arrange
        when(repository.getVendmachtrack()).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.getVendMachList());

    }


    /**
     * Test case for the getVendMachLocation method of the MachineTrackerService class.
     * Verifies that the method returns the correct location for a valid machine ID.
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
     * Test case for the getVendMachLocation method of the MachineTrackerService class when an invalid id is provided.
     * Expects a ResourceNotFoundException to be thrown.
     */
    @Test
    public void getVendMachLocation_invalidId_throwsResourceNotFoundException() {
        // Arrange
        when(repository.getVendmachtrack()).thenReturn(machineTracker);
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.getVendMachLocation(99));
    }

    /**
     * Test case for the getInventory method of the MachineTrackerService class.
     * Verifies that the method returns the correct inventory for a valid machine ID.
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
     * Test case for the getInventory method of the MachineTrackerService class when an invalid id is provided.
     * Expects a ResourceNotFoundException to be thrown.
     */
    @Test
    public void getInventory_invalidId_throwsResourceNotFoundException() {
        // Arrange
        when(repository.getVendMach(99)).thenReturn(null);
        
        //Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.getInventory(99));
    }

    /**
     * Tests the addItem method of the MachineTrackerService class with valid input.
     * Verifies that the method adds an item to the machine successfully when a valid id, item, and amount are provided.
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
     * Tests that an IllegalInputException is thrown when an invalid item is added to the machine.
     */
    @Test 
    public void addItem_invalidItem_throwsIllegalInputException() {
        // Act and Assert
        assertThrows(IllegalInputException.class, () -> service.addItem(1, " ", 5));
    }
    
    /**
     * Tests the addItem method of the MachineTrackerService class when an invalid amount is provided.
     * Expects an IllegalInputException to be thrown.
     * 
     */
    @Test
    public void addItem_invalidAmount_throwsIllegalInputException() {
        // Act and Assert
        assertThrows(IllegalInputException.class, () -> service.addItem(1, "Cola", 0));
    }
    
    /**
     * Tests the addItem method of the MachineTrackerService class when an invalid is is provided.
     * Expects a ResourceNotFoundException to be thrown.
     */
    @Test
    public void addItem_invalidId_throwsResourceNotFoundException() {
        // Arrange
        when(repository.getVendMach(99)).thenReturn(null);
        
        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> service.addItem(99, "Cola", 5));
    }
    
    /**
     * Tests the addItem method of the MachineTrackerService class when a negative amount is provided.
     * Expects an IllegalInputException to be thrown.
     */
    @Test
    public void addItem_negativeAmount_throwsIllegalInputException() {
        
        // Act and Assert
        assertThrows(IllegalInputException.class, () -> service.addItem(1, "Cola", -5));
    }
    
    /**
     * Tests the addVendMach method of the MachineTrackerService class when an existing id is provided.
     * Expects an IllegalInputException to be thrown.
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
     * It verifies that the method adds a new vending machine successfully when a valid id and location are provided.
     */
    @Test
    public void addVendMach_validInput_addsMachineSuccessfully() {
       
        // Arrange
        VendingMachine newMachine = new VendingMachine();
        newMachine.setId(2);
        newMachine.setLocation("Bergen");
        machineTracker.addVendingMachine(newMachine);
        when(repository.getVendMach(2)).thenReturn(null);  // No existing machine with ID 2
        when(repository.getVendmachtrack()).thenReturn(machineTracker);
        
        
        // Act
        HashMap<Integer, String> updatedMachineList = service.addVendMach(2, "Bergen");
        
        // Assert
        assertTrue(updatedMachineList.containsKey(2));
        assertEquals("Bergen", updatedMachineList.get(2));
    }
    
    
    /**
     * Tests the addVendMach method of the MachineTrackerService class when an invalid location is provided.
     * Expects an IllegalInputException to be thrown.
     */
    @Test
    public void addVendMach_invalidLocation_throwsIllegalInputException() {
        // Arrange 
        when(repository.getVendMach(2)).thenReturn(null);
        
        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.addVendMach(2, "123InvalidLocation"));
    }

    /**
     * Tests the addVendMach method of the MachineTrackerService class when an invalid id is provided.
     * Expects an IllegalInputException to be thrown.
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
     * Tests the removeVendMach method of the MachineTrackerService class.
     * It verifies that the method removes a vending machine successfully when a valid id is provided.
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
     * Tests the removeVendMach method of the MachineTrackerService class when an invalid ID is provided.
     * Expects a ResourceNotFoundException to be thrown.
     * 
     */
    @Test
    public void removeVendMach_invalidId_throwsResourceNotFoundException() {
        // Arrange 
        when(repository.getVendMach(99)).thenReturn(null);
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.removeVendMach(99));
    }

    /**
     * Tests the changeLocation method of the MachineTrackerService class.
     * It verifies that the method changes the location of a vending machine successfully.
     * 
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
     * Tests the changeLocation method of the MachineTrackerService class when an invalid location is provided.
     * Expects an IllegalInputException to be thrown.
     */
    @Test
    public void changeLocation_invalidLocation_throwsIllegalInputException() {
        // Arrange
        when(repository.getVendMach(1)).thenReturn(machine);
        
        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.changeLocation(1, "1"));
    }
    
    /**
     * Tests the changeLocation method of the MachineTrackerService class when an invalid id is passed as input. 
     * Expects a ResourceNotFoundException to be thrown.
     * 
     */
    @Test
    public void changeLocation_invalidId_throwsResourceNotFoundException() {
         // Arrange
        when(repository.getVendMach(99)).thenReturn(null);
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.changeLocation(99, "Bergen"));
    }
    
    /**
     * Tests the removeItem method of the MachineTrackerService class when an invalid item is passed as input.
     * Expects an IllegalInputException to be thrown.
     * 
     */
    @Test
    public void removeItem_invalidItem_throwsIllegalInputException() {
        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.removeItem(1, " ", 5));
    }
    
    /**
     * Tests the removeItem method of the MachineTrackerService class when an invalid amount is provided.
     * Expects an IllegalInputException to be thrown.
     */
    @Test
    public void removeItem_invalidAmount_throwsIllegalInputException() {
        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.removeItem(1, "Cola", 0));
    }
    
    /**
     * Tests the removeItem method of the MachineTrackerService class when an invalid id is provided.
     * Expects a ResourceNotFoundException to be thrown.
     * 
     */
    @Test
    public void removeItem_invalidId_throwsResourceNotFoundException() {
        // Arrange  
        when(repository.getVendMach(99)).thenReturn(null);
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.removeItem(99, "Cola", 5));
    }
    
    /**
     * Tests the removeItem method of the MachineTrackerService class when the item is not in the inventory.
     * Expects an IllegalInputException to be thrown.
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
     * Tests the removeItem method of the MachineTrackerService class when the removal amount is greater than the inventory.
     * Expects an IllegalInputException to be thrown.
     */
    @Test
    public void removeItem_removalAmountGreaterThanInventory_throwsIllegalInputException() {
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
