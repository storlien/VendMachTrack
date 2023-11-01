package vendmachtrack.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vendmachtrack.springboot.service.MachineTrackerService;

import java.util.HashMap;

/**
 * Controller responsible for handling HTTP requests related to the machine
 * tracker operations.
 * Provides endpoints to view, add, modify, and delete vending machines and
 * their inventory.
 */
@RestController
@RequestMapping(MachineTrackerController.VENDMACHTRACK_SERVICE_PATH)
public class MachineTrackerController {

    static final String VENDMACHTRACK_SERVICE_PATH = "vendmachtrack";

    private final MachineTrackerService machtrackService;

    /**
     * Constructor injection of the MachineTrackerService dependency.
     *
     * @param machtrackService The service layer handling the machine tracker logic.
     */
    @Autowired
    public MachineTrackerController(MachineTrackerService machtrackService) {
        this.machtrackService = machtrackService;
    }

    /**
     * Fetches the list of all vending machines.
     *
     * @return List of vending machines with their IDs and locations.
     */
    @GetMapping()
    public ResponseEntity<HashMap<Integer, String>> getVendMachList() {
        return ResponseEntity.ok(machtrackService.getVendMachList());
    }

    /**
     * Retrieves the location of a vending machine by its ID.
     *
     * @param id The ID of the vending machine.
     * @return Location of the vending machine.
     */
    @GetMapping("/{id}/name")
    public ResponseEntity<String> getVendMachLocation(@PathVariable("id") int id) {
        return ResponseEntity.ok(machtrackService.getVendMachLocation(id));
    }

    /**
     * Fetches the inventory of a specific vending machine by its ID.
     *
     * @param id The ID of the vending machine.
     * @return Inventory items and their quantities.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Integer>> getInventory(@PathVariable("id") int id) {
        return ResponseEntity.ok(machtrackService.getInventory(id));
    }

    /**
     * Adds items to the inventory of a vending machine.
     *
     * @param id       The ID of the vending machine.
     * @param item     The name of the item.
     * @param quantity The quantity of the item to add.
     * @return Updated inventory items and their quantities.
     */
    @PutMapping("/{id}/add")
    public ResponseEntity<HashMap<String, Integer>> addItem(@PathVariable("id") int id, @RequestParam String item,
            @RequestParam int quantity) {
        return ResponseEntity.ok(machtrackService.addItem(id, item, quantity));
    }

    /**
     * Removes items from the inventory of a vending machine.
     *
     * @param id       The ID of the vending machine.
     * @param item     The name of the item.
     * @param quantity The quantity of the item to remove.
     * @return Updated inventory items and their quantities.
     */
    @PutMapping("/{id}/remove")
    public ResponseEntity<HashMap<String, Integer>> removeItem(@PathVariable("id") int id, @RequestParam String item,
            @RequestParam int quantity) {
        return ResponseEntity.ok(machtrackService.removeItem(id, item, quantity));
    }

    /**
     * Adds a new vending machine.
     *
     * @param id       The ID of the new vending machine.
     * @param location The location of the new vending machine.
     * @return Updated list of vending machines with their IDs and locations.
     */
    @PostMapping("/add")
    public ResponseEntity<HashMap<Integer, String>> addVendMach(@RequestParam int id, @RequestParam String location) {
        return ResponseEntity.ok(machtrackService.addVendMach(id, location));
    }

    /**
     * Removes a vending machine by its ID.
     *
     * @param id The ID of the vending machine to remove.
     * @return Updated list of vending machines with their IDs and locations.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<Integer, String>> removeVendMach(@PathVariable("id") int id) {
        return ResponseEntity.ok(machtrackService.removeVendMach(id));
    }

    /**
     * Changes the location of a vending machine.
     *
     * @param id       The ID of the vending machine.
     * @param location The new location.
     * @return Updated list of vending machines with their IDs and locations.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HashMap<Integer, String>> changeLocation(@PathVariable("id") int id,
            @RequestParam String location) {
        return ResponseEntity.ok(machtrackService.changeLocation(id, location));
    }
}