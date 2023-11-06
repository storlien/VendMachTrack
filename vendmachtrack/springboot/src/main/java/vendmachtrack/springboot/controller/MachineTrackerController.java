package vendmachtrack.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import vendmachtrack.springboot.service.MachineTrackerService;

import java.util.HashMap;

@RestController
@RequestMapping(MachineTrackerController.VENDMACHTRACK_SERVICE_PATH)
public class MachineTrackerController {

    static final String VENDMACHTRACK_SERVICE_PATH = "vendmachtrack";

    private final MachineTrackerService machineTrackerService;

    @Autowired
    public MachineTrackerController(final MachineTrackerService machineTrackerService) {
        this.machineTrackerService = machineTrackerService;
    }

    @GetMapping()
    public ResponseEntity<HashMap<Integer, String>> getVendMachList() {
        return ResponseEntity.ok(machineTrackerService.getVendMachList());
    }

    @GetMapping("/{id}/name")
    public ResponseEntity<String> getVendMachLocation(@PathVariable("id") final int id) {
        return ResponseEntity.ok(machineTrackerService.getVendMachLocation(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Integer>> getInventory(@PathVariable("id") final int id) {
        return ResponseEntity.ok(machineTrackerService.getInventory(id));
    }

    @PutMapping("/{id}/add")
    public ResponseEntity<HashMap<String, Integer>> addItem(@PathVariable("id") final int id,
                                                            @RequestParam final String item,
                                                            @RequestParam final int quantity) {
        return ResponseEntity.ok(machineTrackerService.addItem(id, item, quantity));
    }

    @PutMapping("/{id}/remove")
    public ResponseEntity<HashMap<String, Integer>> removeItem(@PathVariable("id") final int id,
                                                               @RequestParam final String item,
                                                               @RequestParam final int quantity) {
        return ResponseEntity.ok(machineTrackerService.removeItem(id, item, quantity));
    }

    @PostMapping("/add")
    public ResponseEntity<HashMap<Integer, String>> addVendMach(@RequestParam final int id,
                                                                @RequestParam final String location) {
        return ResponseEntity.ok(machineTrackerService.addVendMach(id, location));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<Integer, String>> removeVendMach(@PathVariable("id") final int id) {
        return ResponseEntity.ok(machineTrackerService.removeVendMach(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<Integer, String>> changeLocation(@PathVariable("id") final int id,
                                                                   @RequestParam final String location) {
        return ResponseEntity.ok(machineTrackerService.changeLocation(id, location));
    }
}
