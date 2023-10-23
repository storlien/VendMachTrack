package springboot.repository;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import core.MachineTracker;
import core.VendingMachine;
import jsonio.VendmachtrackPersistence;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;



public class MachineTrackerRepositoryTest {

    @Mock
    private VendmachtrackPersistence persistence;

    @Captor
    private ArgumentCaptor<MachineTracker> machineTrackerCaptor;

    @InjectMocks
    private MachineTrackerRepository machineTrackerRepository;

    private VendingMachine vendingmachine = new VendingMachine();
    private List<VendingMachine> machines = new ArrayList<>();
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        vendingmachine.setId(1);
        vendingmachine.setLocation("Oslo");
        machines.add(vendingmachine);
        
    }


    @Test
    public void MachineTrackerRepository_getVendmachtrack_ReturnMachineTracker(){

      // Arrange
        MachineTracker expectedMachineTracker = new MachineTracker();
        expectedMachineTracker.setMachines(machines);
        when(persistence.getVendmachtrack()).thenReturn(expectedMachineTracker);

        // Act
        MachineTracker actualMachineTracker = machineTrackerRepository.getVendmachtrack();

        // Assert
        assertEquals(expectedMachineTracker, actualMachineTracker);
        verify(persistence, times(1)).getVendmachtrack();
    }

    @Test
    public void MachineTrackerRepository_getVendMach_ReturnVendingMachine(){

        // Arrange
        int id = 123;
        VendingMachine expectedVendingMachine = new VendingMachine();
        expectedVendingMachine.setId(id);
        MachineTracker machineTracker = new MachineTracker();
        machineTracker.setMachines(Arrays.asList(expectedVendingMachine));
        when(persistence.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        VendingMachine actualVendingMachine = machineTrackerRepository.getVendMach(id);

        // Assert
        assertEquals(expectedVendingMachine, actualVendingMachine);

    }


    @Test
    public void MachineTrackerRepository_getVendMach_retrunsnull(){
        // Arrange
        int id = 123;
        MachineTracker machineTracker = new MachineTracker();
        machineTracker.setMachines(Arrays.asList());
        when(persistence.getVendmachtrack()).thenReturn(machineTracker);

        // Act
        VendingMachine actualVendingMachine = machineTrackerRepository.getVendMach(id);

        // Assert
        assertNull(actualVendingMachine);
    }

    
    @Test
    public void MachineTrackerRepository_SaveVendmachtrack_savesAndRetursMAchinetracker(){

        // Arrange
        MachineTracker machineTracker = new MachineTracker();
        machineTracker.setMachines(machines);
       

        // Act
        MachineTracker returnedMachineTracker = machineTrackerRepository.saveVendmachtrack(machineTracker);

        // Assert
        verify(persistence, times(1)).saveVendmachtrack(machineTracker); // check that saveVendmachtrack was called once with the correct parameter
        assertSame(machineTracker, returnedMachineTracker); // check that the returned object is the same as the one passed in
       
    }


    @Test
    public void MachineTrackerRepository_addVendMach_addsVendingMachine(){

          // Arrange
        int id = 2;
        String location = "Trondheim";
        MachineTracker initialMachineTracker = new MachineTracker();
        initialMachineTracker.setMachines(new ArrayList<>());
        when(persistence.getVendmachtrack()).thenReturn(initialMachineTracker);
        when(persistence.saveVendmachtrack(any(MachineTracker.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

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

    @Test
    public void MachineTrackerRepository_addItem_addsItemToExistingVendingmachine(){
           // Arrange
        int id = 1;
        String item = "Cola";
        int amount = 10;
        MachineTracker machineTracker = new MachineTracker();
        machineTracker.setMachines(machines); 
        when(persistence.getVendmachtrack()).thenReturn(machineTracker);
        when(persistence.saveVendmachtrack(any(MachineTracker.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        VendingMachine result = machineTrackerRepository.addItem(id, item, amount);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(amount, result.getStatus().get(item).intValue()); 
        verify(persistence).saveVendmachtrack(machineTrackerCaptor.capture());
        MachineTracker capturedMachineTracker = machineTrackerCaptor.getValue();
        // verify that the captured MachineTracker contains the updated VendingMachine
        assertEquals(vendingmachine, capturedMachineTracker.getMachines().get(0)); 
    }
    
    @Test
    public void MachineTrackerRepository_addItem_returnNullVendingMachinenotexist(){
                
          // Arrange
          int id = 1;
          String item = "Cola";
          int amount = 10;
          MachineTracker machineTracker = new MachineTracker();
          machineTracker.setMachines(Collections.emptyList());
          when(persistence.getVendmachtrack()).thenReturn(machineTracker);
  
          // Act
          VendingMachine result = machineTrackerRepository.addItem(id, item, amount);
  
          // Assert
          assertNull(result);


    }

    


    @Test
    public void MachineTrackerRepository_saveALL_ReturnSavedMachineTrackers(){

        //Arrange 
        

        //Act


        //Assert

    }

}
