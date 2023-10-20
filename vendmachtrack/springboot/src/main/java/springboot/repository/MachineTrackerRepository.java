package springboot.repository;

import core.MachineTracker;
import jsonio.VendmachtrackPersistence;
import org.springframework.stereotype.Repository;

@Repository
public class MachineTrackerRepository {

    private final VendmachtrackPersistence persistence;

    public MachineTrackerRepository(String fileName) {
        persistence = new VendmachtrackPersistence(fileName);
    }

    public MachineTracker getVendmachtrack() {
        return persistence.getVendmachtrack();
    }

    public MachineTracker saveVendmachtrack(MachineTracker vendmachtrack) {
        persistence.saveVendmachtrack(vendmachtrack);
        return vendmachtrack;
    }

}