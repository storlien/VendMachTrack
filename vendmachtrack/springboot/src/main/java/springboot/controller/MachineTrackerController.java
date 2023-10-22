package springboot.controller;

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
        return ResponseEntity.ok(machtrackService.getVendMachLocation(id));
    }

    @GetMapping("/machtrack/{id}")
    public ResponseEntity<HashMap<String, Integer>> getInventory(@PathVariable("id") int id) {
        return ResponseEntity.ok(machtrackService.getInventory(id));
    }

    @PutMapping("/machtrack/{id}/add")
    public ResponseEntity<HashMap<String, Integer>> addItem(@PathVariable("id") int id, @RequestParam String item, @RequestParam int amount) {
        return ResponseEntity.ok(machtrackService.addItem(id, item, amount));
    }

    @PutMapping("/machtrack/{id}/remove")
    public ResponseEntity<HashMap<String, Integer>> removeItem(@PathVariable("id") int id, @RequestParam String item, @RequestParam int amount) {
        return ResponseEntity.ok(machtrackService.removeItem(id, item, amount));
    }

    @PostMapping("/machtrack/add")
    public ResponseEntity<HashMap<Integer, String>> addVendMach(@RequestParam int id, @RequestParam String location) {
        return ResponseEntity.ok(machtrackService.addVendMach(id, location));
    }

    @DeleteMapping("/machtrack/{id}")
    public ResponseEntity<HashMap<Integer, String>> removeVendMach(@PathVariable("id") int id) {
        return ResponseEntity.ok(machtrackService.removeVendMach(id));
    }

    @PutMapping("/machtrack/{id}")
    public ResponseEntity<HashMap<Integer, String>> changeLocation(@PathVariable("id") int id, @RequestParam String location) {
        return ResponseEntity.ok(machtrackService.changeLocation(id, location));
    }
}