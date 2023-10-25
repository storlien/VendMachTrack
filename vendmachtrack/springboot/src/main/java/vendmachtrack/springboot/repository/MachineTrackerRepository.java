package vendmachtrack.springboot.repository;

import vendmachtrack.core.MachineTracker;
import vendmachtrack.core.VendingMachine;
import vendmachtrack.jsonio.VendmachtrackPersistence;
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

    public VendingMachine getVendMach(int id) {
        for (VendingMachine vendMach : getVendmachtrack().getMachines()) {
            if (vendMach.getId() == id) {
                return vendMach;
            }
        }

        return null;
    }

    public VendingMachine changeLocation(int id, String location) {
        MachineTracker machTrack = getVendmachtrack();

        for (VendingMachine vendMach : machTrack.getMachines()) {
            if (vendMach.getId() == id) {
                vendMach.setLocation(location);
                saveVendmachtrack(machTrack);
                return vendMach;
            }
        }

        return null;

    }

    public VendingMachine addItem(int id, String item, int amount) {
        MachineTracker machTrack = getVendmachtrack();

        for (VendingMachine vendMach : machTrack.getMachines()) {
            if (vendMach.getId() == id) {
                vendMach.addItem(item, amount);
                saveVendmachtrack(machTrack);
                return vendMach;
            }
        }

        return null;
    }

    public VendingMachine removeItem(int id, String item, int amount) {
        MachineTracker machTrack = getVendmachtrack();

        for (VendingMachine vendMach : machTrack.getMachines()) {
            if (vendMach.getId() == id) {
                vendMach.removeItem(item, amount);
                saveVendmachtrack(machTrack);
                return vendMach;
            }
        }

        return null;
    }

    public MachineTracker addVendMach(int id, String location) {
        MachineTracker machTrack = getVendmachtrack();
        VendingMachine vendMach = new VendingMachine();

        vendMach.setId(id);
        vendMach.setLocation(location);
        machTrack.addVendingMachine(vendMach);

        return saveVendmachtrack(machTrack);
    }

    public MachineTracker removeVendMach(int id) {
        MachineTracker machTrack = getVendmachtrack();

        for (VendingMachine vendMach : machTrack.getMachines()) {
            if (vendMach.getId() == id) {
                machTrack.removeVendingMachine(vendMach);
            }
        }

        saveVendmachtrack(machTrack);

        return machTrack;
    }
}