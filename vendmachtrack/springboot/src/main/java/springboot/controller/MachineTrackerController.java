package springboot.controller;

import core.VendingMachine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.service.MachineTrackerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

@RestController
@RequestMapping(MachineTrackerController.VENDMACHTRACK_SERVICE_PATH)
public class MachineTrackerController {

    public static final String VENDMACHTRACK_SERVICE_PATH = "vendmachtrack";

    private final MachineTrackerService machtrackService;

    @Autowired
    public MachineTrackerController(MachineTrackerService machtrackService) {
        this.machtrackService = machtrackService;
    }

    @GetMapping("/machtrack")
    public ResponseEntity<HashMap<Integer, String>> getVendMachList() {
        return ResponseEntity.ok(machtrackService.getVendMachList());
    }

    @GetMapping("/machtrack/vendmach")
    public ResponseEntity<VendingMachine> getVendingMachine(@RequestParam int id) {
//        TODO
        return null;
    }

    @PostMapping("/machtrack")
    public ResponseEntity<VendingMachine> updateVendingMachine(@RequestBody VendingMachine vendmach, @RequestParam int id) {
//        TODO
        return null;
    }
}