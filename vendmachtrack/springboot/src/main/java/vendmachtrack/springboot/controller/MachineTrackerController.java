package vendmachtrack.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vendmachtrack.springboot.service.MachineTrackerService;
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

    @GetMapping()
    public ResponseEntity<HashMap<Integer, String>> getVendMachList() {
        return ResponseEntity.ok(machtrackService.getVendMachList());
    }

    @GetMapping("/{id}/name")
    public ResponseEntity<String> getVendMachLocation(@PathVariable("id") int id) {
        return ResponseEntity.ok(machtrackService.getVendMachLocation(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Integer>> getInventory(@PathVariable("id") int id) {
        return ResponseEntity.ok(machtrackService.getInventory(id));
    }

    @PutMapping("/{id}/add")
    public ResponseEntity<HashMap<String, Integer>> addItem(@PathVariable("id") int id, @RequestParam String item,
            @RequestParam int quantity) {
        return ResponseEntity.ok(machtrackService.addItem(id, item, quantity));
    }

    @PutMapping("/{id}/remove")
    public ResponseEntity<HashMap<String, Integer>> removeItem(@PathVariable("id") int id, @RequestParam String item,
            @RequestParam int quantity) {
        return ResponseEntity.ok(machtrackService.removeItem(id, item, quantity));
    }

    @PostMapping("/add")
    public ResponseEntity<HashMap<Integer, String>> addVendMach(@RequestParam int id, @RequestParam String location) {
        return ResponseEntity.ok(machtrackService.addVendMach(id, location));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<Integer, String>> removeVendMach(@PathVariable("id") int id) {
        return ResponseEntity.ok(machtrackService.removeVendMach(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<Integer, String>> changeLocation(@PathVariable("id") int id,
            @RequestParam String location) {
        return ResponseEntity.ok(machtrackService.changeLocation(id, location));
    }
}