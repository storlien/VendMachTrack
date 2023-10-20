package springboot.controller;

import core.MachineTracker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.service.MachineTrackerService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("vendmachtrack")
public class MachineTrackerController {

    private final MachineTrackerService machtrackService;

    @Autowired
    public MachineTrackerController(MachineTrackerService machtrackService) {
        this.machtrackService = machtrackService;
    }

    @GetMapping("machtrack")
    public ResponseEntity<MachineTracker> getMachineTracker() {
        System.out.println("Get successful");
        return machtrackService.getMachineTracker();
    }

    @PostMapping("update")
    public ResponseEntity<MachineTracker> postMachineTracker(@RequestBody MachineTracker machtrack) {
        System.out.println("Post successful");
        return machtrackService.postMachineTracker(machtrack);
    }
}