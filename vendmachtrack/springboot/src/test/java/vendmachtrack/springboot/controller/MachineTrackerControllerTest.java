package vendmachtrack.springboot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import vendmachtrack.core.MachineTracker;
import vendmachtrack.core.VendingMachine;
import vendmachtrack.springboot.exception.IllegalInputException;
import vendmachtrack.springboot.exception.ResourceNotFoundException;
import vendmachtrack.springboot.service.MachineTrackerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;

@WebMvcTest(controllers = MachineTrackerController.class)
public class MachineTrackerControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private MachineTrackerService machineTrackerService;

    @Autowired
    private ObjectMapper objectMapper;
 
    
    

    private VendingMachine vendingmachine = new VendingMachine();
    private List<VendingMachine> machines = new ArrayList<>();
    private MachineTracker machineTracker = new MachineTracker();


 
    @BeforeEach
    public void setUp() {
        vendingmachine.setId(1);
        vendingmachine.setLocation("Oslo");
        machines.add(vendingmachine);
    }

    @Test
    public void MachineTrackerController_getVendMachList_returnVendmachList() throws Exception {
        // Arrange
        HashMap<Integer, String> vendMachList = new HashMap<>();
        vendMachList.put(1, "Oslo");
        when(machineTrackerService.getVendMachList()).thenReturn(vendMachList);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .get("/vendmachtrack")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("{\"1\":\"Oslo\"}"));
    }

    @Test
    public void MachineTrackerController_getVendMachList_throwsResourceNotFoundException() throws Exception{

        when(machineTrackerService.getVendMachList()).thenThrow(new ResourceNotFoundException("Should throw"));

        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .get("/vendmachtrack")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }

    @Test
    public void MachineTrackerController_getVendMachLocation_returnLocation() throws Exception {
        // Arrange
        String location = "Oslo";
        when(machineTrackerService.getVendMachLocation(anyInt())).thenReturn(location);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .get("/vendmachtrack/1/name")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("Oslo"));
    }


    @Test
    public void MachineTrackerController_getVendMachLocation_throwsResourceNotFoundException() throws Exception{

        // Arrange
        when(machineTrackerService.getVendMachLocation(anyInt())).thenThrow(new ResourceNotFoundException("Should throw"));

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .get("/vendmachtrack/1/name")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }








    @Test
    public void MachineTrackerController_getInventory_returnInventory() throws Exception {
        // Arrange
        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Cola", 10);
        when(machineTrackerService.getInventory(anyInt())).thenReturn(inventory);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .get("/vendmachtrack/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("{\"Cola\":10}"));
    }

    @Test
    public void MachineTrackerController_getInventory_throwsIllegalInputException() throws Exception {
        // Arrange
        when(machineTrackerService.getInventory(anyInt())).thenThrow(new IllegalInputException("Should throw"));

         // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .get("/vendmachtrack/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalInputException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }

    @Test
    public void MachineTrackerController_addItem_returnUpdatedInventory() throws Exception {
        // Arrange
        HashMap<String, Integer> updatedInventory = new HashMap<>();
        updatedInventory.put("Coke", 10);
        when(machineTrackerService.addItem(anyInt(), anyString(), anyInt())).thenReturn(updatedInventory);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .put("/vendmachtrack/1/add")
            .param("item", "Coke")
            .param("amount", "10")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("{\"Coke\":10}"));
    }

    @Test
    public void MachineTrackerController_addItem_throwsIllegalInputException() throws Exception {
        // Arrange
        when(machineTrackerService.addItem(anyInt(), anyString(), anyInt())).thenThrow(new IllegalInputException("Should throw"));

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .put("/vendmachtrack/1/add")
            .param("item", "Coke")
            .param("amount", "10")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalInputException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }

    @Test
    public void MachineTrackerController_removeItem_returnUpdatedInventory() throws Exception {
        // Arrange
        HashMap<String, Integer> updatedInventory = new HashMap<>();
        updatedInventory.put("Coke", 5);
        when(machineTrackerService.removeItem(anyInt(), anyString(), anyInt())).thenReturn(updatedInventory);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .put("/vendmachtrack/1/remove")
            .param("item", "Coke")
            .param("amount", "5")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("{\"Coke\":5}"));
    }


      @Test
    public void MachineTrackerController_removeItem_throwsIllegalInputException() throws Exception {
        
        // Arrange
        when(machineTrackerService.removeItem(anyInt(), anyString(), anyInt())).thenThrow(new IllegalInputException("Should throw"));

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .put("/vendmachtrack/1/remove")
            .param("item", "Coke")
            .param("amount", "5")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalInputException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
        
    }
    
    @Test
    public void MachineTrackerController_addVendMach_returnAdded() throws Exception {
        
        //Arrange
        HashMap<Integer, String> status = new HashMap<>();
        status.put(1, "Oslo");
        given(machineTrackerService.addVendMach(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString())).willReturn(status);

        //Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .post("/vendmachtrack/add")
            .param("id", "1")
            .param("location", "Oslo")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("{\"1\":\"Oslo\"}"));
    }


     @Test
    public void MachineTrackerController_addVendMach_throwsIllegalInputException() throws Exception {
        //Arrange
        when(machineTrackerService.addVendMach(anyInt(), anyString())).thenThrow(new IllegalInputException("Should throw"));

        //Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .post("/vendmachtrack/add")
            .param("id", "1")
            .param("location", "Oslo")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalInputException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }

    @Test
    public void MachineTrackerController_removeVendMach_returnUpdatedList() throws Exception {
        // Arrange
        HashMap<Integer, String> updatedList = new HashMap<>();
        updatedList.put(2, "Trondheim");
        when(machineTrackerService.removeVendMach(anyInt())).thenReturn(updatedList);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .delete("/vendmachtrack/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("{\"2\":\"Trondheim\"}"));
    }

    @Test
    public void MachineTrackerController_removeVendMach_throwsIllegalInputException() throws Exception {
        
        // Arrange
        when(machineTrackerService.removeVendMach(anyInt())).thenThrow(new IllegalInputException("Should throw"));

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .delete("/vendmachtrack/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
         response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalInputException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }

    @Test
    public void MachineTrackerController_changeLocation_returnUpdatedLocation() throws Exception {
        // Arrange
        HashMap<Integer, String> updatedLocation = new HashMap<>();
        updatedLocation.put(1, "Trondheim");
        when(machineTrackerService.changeLocation(anyInt(), anyString())).thenReturn(updatedLocation);

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .put("/vendmachtrack/1")
            .param("location", "Trondheim")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("{\"1\":\"Trondheim\"}"));
    }

      @Test
    public void MachineTrackerController_changeLocation_throwsIllegalInputException() throws Exception {
       
        // Arrange
        when(machineTrackerService.changeLocation(anyInt(), anyString())).thenThrow(new IllegalInputException("Should throw"));

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .put("/vendmachtrack/1")
            .param("location", "Trondheim")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalInputException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }


      @Test
    public void MachineTrackerController_changeLocation_throwsResourceNotFoundException() throws Exception {
        
        // Arrange
        when(machineTrackerService.changeLocation(anyInt(), anyString())).thenThrow(new ResourceNotFoundException("Should throw"));

        // Act
        ResultActions response = mockmvc.perform(MockMvcRequestBuilders
            .put("/vendmachtrack/1")
            .param("location", "Trondheim")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Should throw", result.getResolvedException().getMessage()));
    }

}
