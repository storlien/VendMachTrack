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

public class MachineTrackerServiceTest {

    @Mock
    private MachineTrackerRepository repository;

    @InjectMocks
    private MachineTrackerService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getVendMachList_whenMachineTrackerIsPresent_returnsListOfMachines() {
        // Arrange
        VendingMachine machine = new VendingMachine();
        machine.setId(1);
        machine.setLocation("Location1");
        MachineTracker machineTracker = new MachineTracker();
        machineTracker.setMachines(Collections.singletonList(machine));
        when(repository.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        var result = service.getVendMachList();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(1));
        assertEquals("Location1", result.get(1));
    }

  
}
