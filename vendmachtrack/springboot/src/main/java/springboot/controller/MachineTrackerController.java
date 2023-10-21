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

    @GetMapping("/machtrack/{id}/name")
    public ResponseEntity<String> getVendMachLocation(@PathVariable("id") int id) {
        //        TODO
        return null;
    }

    @GetMapping("/machtrack/{id}")
    public ResponseEntity<HashMap<String, Integer>> getInventory(@PathVariable("id") int id) {
        //        TODO
        return null;
    }

    @PutMapping("/machtrack/{id}/add")
    public ResponseEntity<HashMap<String, Integer>> addItem(@PathVariable("id") int id, @RequestParam String item, @RequestParam int amount) {
        //        TODO
        return null;
    }

    @PutMapping("/machtrack/{id}/remove")
    public ResponseEntity<HashMap<String, Integer>> removeItem(@PathVariable("id") int id, @RequestParam String item, @RequestParam int amount) {
        //        TODO
        return null;
    }

    @PostMapping("/machtrack/add")
    public ResponseEntity<HashMap<Integer, String>> addVendMach(@RequestParam int id, @RequestParam String location) {
        //        TODO
        return null;
    }

    @DeleteMapping("/machtrack/{id}")
    public ResponseEntity<HashMap<Integer, String>> removeVendMach(@PathVariable("id") int id) {
        //        TODO
        return null;
    }

    @PutMapping("/machtrack/{id}")
    public ResponseEntity<HashMap<Integer, String>> changeLocation(@PathVariable("id") int id, @RequestParam String location) {
        //        TODO
        return null;
    }
}