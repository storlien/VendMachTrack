package springboot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import core.MachineTracker;
import core.VendingMachine;
import springboot.exception.ResourceNotFoundException;
import springboot.repository.MachineTrackerRepository;

import java.util.Collections;
import java.util.HashMap;

public class MachineTrackerServiceTest {

    @Mock
    private MachineTrackerRepository repository;

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
        
        // Assert
        assertThrows(ResourceNotFoundException.class, () -> service.getInventory(99));
    }
  
}
