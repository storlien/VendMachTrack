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
import vendmachtrack.springboot.service.MachineTrackerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
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
    public void MachineTrackerController_getVendMachList_returnVendmachList(){

    }

    
 

}
