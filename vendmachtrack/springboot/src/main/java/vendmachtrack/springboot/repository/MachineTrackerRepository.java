package vendmachtrack.springboot.repository;

import org.springframework.stereotype.Repository;
import vendmachtrack.core.MachineTracker;
import vendmachtrack.core.VendingMachine;
import vendmachtrack.jsonio.VendmachtrackPersistence;

/**
 * Repository class responsible for handling operations related to the
 * {@code MachineTracker}.
 * This class integrates with the {@code VendmachtrackPersistence} class to
 * manage Vending Machine data.
 * <p>
 * This class is stateless, ensuring that the data retrieved and persisted is
 * always up-to-date with the
 * data storage.
 */
@Repository
public class MachineTrackerRepository {

    /**
     * Instance of the persistence class used for data operations.
     */
    private final VendmachtrackPersistence persistence;

    /**
     * Constructs a new {@code MachineTrackerRepository} instance.
     *
     * @param fileName The name of the file where the data is stored.
     */
    public MachineTrackerRepository(final String fileName) {
        persistence = new VendmachtrackPersistence(fileName);
    }

    /**
     * Retrieves the current state of the {@code MachineTracker}.
     *
     * @return The current {@code MachineTracker} instance.
     */
    public MachineTracker getVendmachtrack() {
        return persistence.getVendmachtrack();
    }

    /**
     * Persists the given {@code MachineTracker} instance.
     *
     * @param vendmachtrack The {@code MachineTracker} instance to save.
     * @return The saved {@code MachineTracker} instance.
     */
    public MachineTracker saveVendmachtrack(final MachineTracker vendmachtrack) {
        persistence.saveVendmachtrack(vendmachtrack);
        return vendmachtrack;
    }

    /**
     * Retrieves a {@code VendingMachine} by its ID.
     *
     * @param id The ID of the {@code VendingMachine}.
     * @return The {@code VendingMachine} with the specified ID, or null if not
     *         found.
     */
    public VendingMachine getVendMach(final int id) {
        for (VendingMachine vendMach : getVendmachtrack().getMachines()) {
            if (vendMach.getId() == id) {
                return vendMach;
            }
        }
        return null;
    }

    /**
     * Updates the location of a specific {@code VendingMachine} based on its ID.
     *
     * @param id       The ID of the {@code VendingMachine}.
     * @param location The new location.
     * @return The updated {@code VendingMachine}, or null if not found.
     */
    public VendingMachine changeLocation(final int id, final String location) {
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

    /**
     * Adds an item to a specific {@code VendingMachine} based on its ID.
     *
     * @param id       The ID of the {@code VendingMachine}.
     * @param item     The item to add.
     * @param quantity The quantity of the item to add.
     * @return The updated {@code VendingMachine}, or null if not found.
     */
    public VendingMachine addItem(final int id, final String item, final int quantity) {
        MachineTracker machTrack = getVendmachtrack();

        for (VendingMachine vendMach : machTrack.getMachines()) {
            if (vendMach.getId() == id) {
                vendMach.addItem(item, quantity);
                saveVendmachtrack(machTrack);
                return vendMach;
            }
        }
        return null;
    }

    /**
     * Removes an item from a specific {@code VendingMachine} based on its ID.
     *
     * @param id       The ID of the {@code VendingMachine}.
     * @param item     The item to remove.
     * @param quantity The quantity of the item to remove.
     * @return The updated {@code VendingMachine}, or null if not found.
     */
    public VendingMachine removeItem(final int id, final String item, final int quantity) {
        MachineTracker machTrack = getVendmachtrack();

        for (VendingMachine vendMach : machTrack.getMachines()) {
            if (vendMach.getId() == id) {
                vendMach.removeItem(item, quantity);
                saveVendmachtrack(machTrack);
                return vendMach;
            }
        }
        return null;
    }

    /**
     * Adds a new {@code VendingMachine} with a specified ID and location to the
     * {@code MachineTracker}.
     *
     * @param id       The ID of the new {@code VendingMachine}.
     * @param location The location of the new {@code VendingMachine}.
     * @return The updated {@code MachineTracker} instance.
     */
    public MachineTracker addVendMach(final int id, final String location) {
        MachineTracker machTrack = getVendmachtrack();
        VendingMachine vendMach = new VendingMachine();

        vendMach.setId(id);
        vendMach.setLocation(location);
        machTrack.addVendingMachine(vendMach);

        return saveVendmachtrack(machTrack);
    }

    /**
     * Removes a {@code VendingMachine} based on its ID from the
     * {@code MachineTracker}.
     *
     * @param id The ID of the {@code VendingMachine} to remove.
     * @return The updated {@code MachineTracker} instance.
     */
    public MachineTracker removeVendMach(final int id) {
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
