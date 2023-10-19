package springboot.service;

import core.MachineTracker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MachineTrackerService {

    public ResponseEntity<MachineTracker> getMachineTracker() {
//        TODO
        return new ResponseEntity<MachineTracker>((MachineTracker) null, HttpStatus.OK);
    }

    public ResponseEntity<MachineTracker> postMachineTracker(MachineTracker machtrack) {
        // TODO
        return new ResponseEntity<MachineTracker>((MachineTracker) null, HttpStatus.CREATED);
    }
}
