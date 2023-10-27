package vendmachtrack.ui.access;

import vendmachtrack.core.MachineTracker;
import vendmachtrack.core.VendingMachine;
import vendmachtrack.jsonio.VendmachtrackPersistence;

import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Class for accessing Vending Machine Tracker through REST API.
 * Accesses the Spring Boot REST API server by following the documentation on possible requests to perform against the API.
 */
public class MachineTrackerAccessLocal implements MachineTrackerAccessible {

    private final VendmachtrackPersistence persistence;

    /**
     * Constructor. Requires an object of VendmachtrackPersistence for reading/writing to file.
     *
     * @param persistence VendmachtrackPersistence object
     */
    public MachineTrackerAccessLocal(VendmachtrackPersistence persistence) {
        this.persistence = persistence;
    }

    /**
     * Access method for the list of vending machines
     *
     * @return HashMap of vending machine list with vending machine ID as key and location as value
     */
    @Override
    public HashMap<Integer, String> getVendMachList() {
        HashMap<Integer, String> vendMachList = new HashMap<>();

        for (VendingMachine vendMach : getMachtrack().getMachines()) {
            vendMachList.put(vendMach.getId(), vendMach.getLocation());
        }

        return vendMachList;
    }

    /**
     * Access method for the location of a vending machine
     *
     * @param id The ID of the vending machine
     * @return Location of vending machine
     */
    @Override
    public String getVendMachLocation(int id) {
        HashMap<Integer, String> vendMachList = getVendMachList();

        if (vendMachList.containsKey(id)) {
            return vendMachList.get(id);
        } else {
            throw new RuntimeException("No such Vending Machine with ID: " + id);
        }
    }

    /**
     * Access method for inventory
     *
     * @param id The ID of the vending machine
     * @return HashMap of inventory with item as key and amount as value
     */
    @Override
    public HashMap<String, Integer> getInventory(int id) {
        return getVendMach(id).getStatus();
    }

    /**
     * Access method for adding item to vending machine's inventory
     *
     * @param id     The ID of the vending machine
     * @param item   The item name to be added
     * @param amount The amount of the item to be added
     * @return HashMap of inventory with item as key and amount as value
     */
    @Override
    public HashMap<String, Integer> addItem(int id, String item, int amount) {
        validateItem(item);
        validateAmount(amount);

        MachineTracker machtrack = getMachtrack();

        for (VendingMachine vendMach : machtrack.getMachines()) {
            if (vendMach.getId() == id) {
                vendMach.addItem(item, amount);
                persistence.saveVendmachtrack(machtrack);
                return vendMach.getStatus();
            }
        }

        throw new RuntimeException("No such Vending Machine with ID: " + id);
    }

    /**
     * Access method for removing an amount of an item
     *
     * @param id     The ID of the vending machine
     * @param item   The item to be removed
     * @param amount The amount to be removed from the item
     * @return HashMap of inventory with item as key and amount as value
     */
    @Override
    public HashMap<String, Integer> removeItem(int id, String item, int amount) {
        validateItem(item);
        validateAmount(amount);

        VendingMachine vendMach = getVendMach(id);

        if (!vendMach.getStatus().containsKey(item)) {
            throw new IllegalArgumentException("The vending machine's inventory does not contain this item");
        } else if (amount > vendMach.getStatus().get(item)) {
            throw new IllegalArgumentException("The vending machine's inventory contains less than the given amount to remove of item: " + item);
        } else {
            MachineTracker machTrack = getMachtrack();

            for (VendingMachine v : machTrack.getMachines()) {
                if (v.getId() == id) {
                    v.removeItem(item, amount);
                    persistence.saveVendmachtrack(machTrack);
                    return v.getStatus();
                }
            }

            return null;
        }
    }

    /**
     * Access method for adding a new vending machine
     *
     * @param id       The ID of the new vending machine
     * @param location The location of the new vending machine
     * @return HashMap of vending machine list with vending machine ID as key and location as value
     */
    @Override
    public HashMap<Integer, String> addVendMach(int id, String location) {
        validateNewVendMachId(id);
        validateLocation(location);

        MachineTracker machTrack = getMachtrack();
        VendingMachine vendMach = new VendingMachine();

        vendMach.setId(id);
        vendMach.setLocation(location);
        machTrack.addVendingMachine(vendMach);
        persistence.saveVendmachtrack(machTrack);

        return getVendMachList();
    }

    /**
     * Access method for removing a vending machine
     *
     * @param id The ID of the vending machine
     * @return HashMap of vending machine list with vending machine ID as key and location as value
     */
    @Override
    public HashMap<Integer, String> removeVendMach(int id) {
        validateVendMachId(id);

        MachineTracker machTrack = getMachtrack();

        for (VendingMachine vendMach : machTrack.getMachines()) {
            if (vendMach.getId() == id) {
                machTrack.removeVendingMachine(vendMach);
                persistence.saveVendmachtrack(machTrack);
            }
        }

        return getVendMachList();
    }

    /**
     * Access method for changing the location of a vending machine
     *
     * @param id       The ID of the vending machine
     * @param location The new location of the vending machine
     * @return HashMap of vending machine list with vending machine ID as key and location as value
     */
    @Override
    public HashMap<Integer, String> changeLocation(int id, String location) {
        validateLocation(location);
        validateVendMachId(id);

        MachineTracker machTrack = getMachtrack();

        for (VendingMachine vendMach : machTrack.getMachines()) {
            if (vendMach.getId() == id) {
                vendMach.setLocation(location);
                persistence.saveVendmachtrack(machTrack);
            }
        }

        return getVendMachList();
    }

    /**
     * Internal method for validating location name.
     * Throws IllegalArgumentException if name is not valid.
     *
     * @param location Location name to be validated
     */
    private void validateLocation(String location) {
        if (!Pattern.compile("[A-ZÆØÅ][a-zæøå]*( [A-ZÆØÅ][a-zæøå]*)*").matcher(location).matches()) {
            throw new IllegalArgumentException("Location name not valid");
        }
    }

    /**
     * Internal method for validating amount.
     * Throws IllegalArgumentException if amount is not valid.
     *
     * @param amount Amount to be validated
     */
    private void validateAmount(int amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Amount has to be higher than zero");
        }
    }

    /**
     * Internal method for validating item name.
     * Throws IllegalArgumentException if item name is not valid.
     *
     * @param item Item name to be validated
     */
    private void validateItem(String item) {
        if (!Pattern.compile("\\S(.*\\S)?").matcher(item).matches()) {
            throw new IllegalArgumentException("Item name not valid");
        }
    }

    /**
     * Internal method for validating if the vending machine with given ID exists.
     * Throws IllegalArgumentException if no vending machine with given ID exists.
     *
     * @param id The ID of the vending machine to be validated
     */
    private void validateVendMachId(int id) {
        for (VendingMachine vendMach : getMachtrack().getMachines()) {
            if (vendMach.getId() == id) {
                return;
            }
        }

        throw new IllegalArgumentException("No such Vending Machine with ID: " + id);
    }

    /**
     * Internal method for validating new ID for vending machine.
     * Throws IllegalArgumentException if new ID is not valid or already existing.
     *
     * @param id The ID to be validated
     */
    private void validateNewVendMachId(int id) {
        if (!Pattern.compile("\\d+").matcher(String.valueOf(id)).matches()) {
            throw new IllegalArgumentException("Id not valid");
        }

        for (VendingMachine vendMach : getMachtrack().getMachines()) {
            if (vendMach.getId() == id) {
                throw new IllegalArgumentException("A vending machine with id " + id + " already exists");
            }
        }
    }

    /**
     * Internal method for retrieving a vending machine based on ID.
     * Throws IllegalArgumentException if no vending machine with given ID exists.
     *
     * @param id The ID of the vending machine
     * @return Vending Machine with given ID
     */
    private VendingMachine getVendMach(int id) {
        for (VendingMachine vendMach : getMachtrack().getMachines()) {
            if (vendMach.getId() == id) {
                return vendMach;
            }
        }

        throw new IllegalArgumentException("No such Vending Machine with ID: " + id);
    }

    /**
     * Internal method for retrieving vending machine tracker.
     * Throws RunTimeException if no vending machine tracker could be found with the persistence object given to constructor.
     *
     * @return Vending machine tracker object
     */
    private MachineTracker getMachtrack() {
        Optional<MachineTracker> machtrack = Optional.ofNullable(persistence.getVendmachtrack());

        if (machtrack.isEmpty()) {
            throw new RuntimeException("Vending Machine Tracker not found");
        }

        return machtrack.get();
    }
}
