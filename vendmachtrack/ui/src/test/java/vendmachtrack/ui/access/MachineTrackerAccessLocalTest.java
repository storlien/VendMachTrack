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

public class MachineTrackerAccessLocalTest {

    @Mock
    private VendmachtrackPersistence mockPersistence;

    @InjectMocks
    private MachineTrackerAccessLocal accessLocal;

    private MachineTracker machineTracker;
    private VendingMachine machine = new VendingMachine();

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

    @Test
    public void MachineTrackerAccessLocal_getVendMachList_throwsExceptionWhenNoTrackerFound() {
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(null);  // This would cause getMachtrack to throw an exception

        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            accessLocal.getVendMachList();
        }, "Vending Machine Tracker not found");
    }

    @Test
    public void MachineTrackerAccessLocal_getVendMachLocation_returnsLocationForValidId() {
        // Arrange
        when(mockPersistence.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        String location = accessLocal.getVendMachLocation(1);

        // Assert
        assertEquals("Oslo", location);
    }

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
