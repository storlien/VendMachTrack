package springboot.service;

import core.MachineTracker;
import core.VendingMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.exception.ResourceNotFoundException;
import springboot.repository.MachineTrackerRepository;

import java.util.HashMap;
import java.util.Optional;

@Service
public class MachineTrackerService {


    private final MachineTrackerRepository repository;

    @Autowired
    public MachineTrackerService(MachineTrackerRepository repository) {
        this.repository = repository;
    }

    public HashMap<Integer, String> getVendMachList() {
        Optional<MachineTracker> machtrack = Optional.ofNullable(repository.getVendmachtrack());

        if (machtrack.isPresent()) {
            HashMap<Integer, String> vendMachList = new HashMap<>();

            for (VendingMachine vendMach : machtrack.get().getMachines()) {
                vendMachList.put(vendMach.getId(), vendMach.getLocation());
            }

            return vendMachList;
        } else {
            throw new ResourceNotFoundException("Vending Machine Tracker not found");
        }
    }

    public VendingMachine getVendingMaching(int id) {
        // TODO
        return null;
    }

    public VendingMachine updateVendingMachine(VendingMachine vendmach, int id) {
        // TODO
        return null;
    }
}
