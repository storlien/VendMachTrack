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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        machine.setId(1);
        machine.setLocation("Oslo");
         machineTracker.setMachines(Collections.singletonList(machine));
    }

    @Test
    public void getVendMachList_whenMachineTrackerIsPresent_returnsListOfMachines() {
        // Arrange
        when(repository.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        var result = service.getVendMachList();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(1));
        assertEquals("Oslo", result.get(1));
    }

    @Test
    public void getVendMachList_whenMachineTrackerIsNotPresent_thowsException(){

        // Arrange
        when(repository.getVendmachtrack()).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.getVendMachList());

    }


    @Test
    public void getVendMachLocation_validId_returnsLocation() {
        // Arrange
        when(repository.getVendmachtrack()).thenReturn(machineTracker);
        
        // Act
        String location = service.getVendMachLocation(1);
        
        // Assert
        assertEquals("Oslo", location);
    }

    @Test
    public void getVendMachLocation_invalidId_throwsResourceNotFoundException() {
        // Arrange
        when(repository.getVendmachtrack()).thenReturn(machineTracker);
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.getVendMachLocation(99));
    }

    @Test
    public void getInventory_validId_returnsInventory() {
        // Act
        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Cola", 10);
        inventory.put("Pepsi", 5);
        machine.setStatus(inventory);
        
        // Assert
        when(repository.getVendMach(1)).thenReturn(machine);
        HashMap<String, Integer> fetchedInventory = service.getInventory(1);
        
        // Then the fetched inventory should match the expected inventory
        assertEquals(inventory, fetchedInventory);
    }

    @Test
    public void getInventory_invalidId_throwsResourceNotFoundException() {
        // Act
        when(repository.getVendMach(99)).thenReturn(null);
        
        //Arrange & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.getInventory(99));
    }

        @Test
    public void addItem_validInput_addsItemSuccessfully() {
        // Act
        HashMap<String, Integer> inventory = new HashMap<>();
        VendingMachine updatedMachine = new VendingMachine();
        updatedMachine.setId(1);
        updatedMachine.setStatus(inventory);

        when(repository.getVendMach(1)).thenReturn(machine);
        when(repository.addItem(1, "Cola", 5)).thenReturn(updatedMachine);
        
        // Arrange
        HashMap<String, Integer> updatedInventory = service.addItem(1, "Cola", 5);
        
        // Assert
        assertEquals(inventory, updatedInventory);
    }
    
    @Test 
    public void addItem_invalidItem_throwsIllegalInputException() {
        // Arrange and Assert
        assertThrows(IllegalInputException.class, () -> service.addItem(1, " ", 5));
    }
    
    @Test
    public void addItem_invalidAmount_throwsIllegalInputException() {
        // Arrange and Assert
        assertThrows(IllegalInputException.class, () -> service.addItem(1, "Cola", 0));
    }
    
    @Test
    public void addItem_invalidId_throwsResourceNotFoundException() {
        // Act
        when(repository.getVendMach(99)).thenReturn(null);
        
        // Arrange and Assert
        assertThrows(ResourceNotFoundException.class, () -> service.addItem(99, "Cola", 5));
    }
    
    @Test
    public void addItem_negativeAmount_throwsIllegalInputException() {
        
        // Arrange and Assert
        assertThrows(IllegalInputException.class, () -> service.addItem(1, "Cola", -5));
    }
    

    
    @Test
    public void addVendMach_existingId_throwsIllegalInputException() {
        // Arrange
        when(repository.getVendMach(1)).thenReturn(machine);
        
        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.addVendMach(1, "Trondheim"));
    }


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
    
    
    @Test
    public void addVendMach_invalidLocation_throwsIllegalInputException() {
        // Arrange 
        when(repository.getVendMach(2)).thenReturn(null);
        
        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.addVendMach(2, "123InvalidLocation"));
    }

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
    
    @Test
    public void removeVendMach_invalidId_throwsResourceNotFoundException() {
        // Arrange  that there is no machine with ID 99
        when(repository.getVendMach(99)).thenReturn(null);
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.removeVendMach(99));
    }

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
    
    @Test
    public void changeLocation_invalidLocation_throwsIllegalInputException() {
        // Arrange
        when(repository.getVendMach(1)).thenReturn(machine);
        
        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.changeLocation(1, "123InvalidLocation"));
    }
    
    @Test
    public void changeLocation_invalidId_throwsResourceNotFoundException() {
         // Arrange
        when(repository.getVendMach(99)).thenReturn(null);
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.changeLocation(99, "Bergen"));
    }
    
    @Test
    public void removeItem_invalidItem_throwsIllegalInputException() {
        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.removeItem(1, " ", 5));
    }
    
    @Test
    public void removeItem_invalidAmount_throwsIllegalInputException() {
        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.removeItem(1, "Cola", 0));
    }
    
    @Test
    public void removeItem_invalidId_throwsResourceNotFoundException() {
        // Arrange  that the repository does not have a machine with ID 99
        when(repository.getVendMach(99)).thenReturn(null);
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.removeItem(99, "Cola", 5));
    }
    
    @Test
    public void removeItem_itemNotInInventory_throwsIllegalInputException() {
        // Arrange 
        HashMap<String, Integer> inventory = new HashMap<>();
        machine.setStatus(inventory);
        
        when(repository.getVendMach(1)).thenReturn(machine);
        
        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.removeItem(1, "Cola", 5));
    }
    
    @Test
    public void removeItem_removalAmountGreaterThanInventory_throwsIllegalInputException() {
        // Arrange 
        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Cola", 2);
        machine.setStatus(inventory);
        
        when(repository.getVendMach(1)).thenReturn(machine);
        
        // Act & Assert
        assertThrows(IllegalInputException.class, () -> service.removeItem(1, "Cola", 5));
    }
    
}
