package vendmachtrack.ui.access;

import vendmachtrack.core.MachineTracker;
import vendmachtrack.core.VendingMachine;
import vendmachtrack.jsonio.VendmachtrackPersistence;

import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Class for accessing Vending Machine Tracker through REST API.
 * Accesses the Spring Boot REST API server by following the documentation on
 * possible requests to perform against the API.
 */
public class MachineTrackerAccessLocal implements MachineTrackerAccessible {

    private final VendmachtrackPersistence persistence;

    /**
     * Constructor. Requires an object of VendmachtrackPersistence for
     * reading/writing to file.
     *
     * @param persistence VendmachtrackPersistence object
     */
    public MachineTrackerAccessLocal(final VendmachtrackPersistence persistence) {
        this.persistence = persistence;
    }

    /**
     * Access method for the list of vending machines
     *
     * @return HashMap of vending machine list with vending machine ID as key and
     * location as value
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
    public String getVendMachLocation(final int id) {
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
     * @return HashMap of inventory with item as key and quantity as value
     */
    @Override
    public HashMap<String, Integer> getInventory(final int id) {
        return getVendMach(id).getStatus();
    }

    /**
     * Access method for adding item to vending machine's inventory
     *
     * @param id       The ID of the vending machine
     * @param item     The item name to be added
     * @param quantity The quantity of the item to be added
     * @return HashMap of inventory with item as key and quantity as value
     */
    @Override
    public HashMap<String, Integer> addItem(final int id, final String item, final int quantity) {
        validateItem(item);
        validateQuantity(quantity);

        MachineTracker machtrack = getMachtrack();

        for (VendingMachine vendMach : machtrack.getMachines()) {
            if (vendMach.getId() == id) {
                vendMach.addItem(item, quantity);
                persistence.saveVendmachtrack(machtrack);
                return vendMach.getStatus();
            }
        }

        throw new RuntimeException("No such Vending Machine with ID: " + id);
    }

    /**
     * Access method for removing a quantity of an item
     *
     * @param id       The ID of the vending machine
     * @param item     The item to be removed
     * @param quantity The quantity to be removed from the item
     * @return HashMap of inventory with item as key and quantity as value
     * @throws IllegalArgumentException if inventory doesn't
     * contain item or contains less than the quantity to be removed
     */
    @Override
    public HashMap<String, Integer> removeItem(final int id, final String item, final int quantity) {
        validateItem(item);
        validateQuantity(quantity);

        VendingMachine vendMach = getVendMach(id);

        if (!vendMach.getStatus().containsKey(item)) {
            throw new IllegalArgumentException("The vending machine's inventory does not contain this item");
        } else if (quantity > vendMach.getStatus().get(item)) {
            throw new IllegalArgumentException(
                    "The vending machine's inventory contains less than the given quantity to remove of item: "
                     + item);
        } else {
            MachineTracker machTrack = getMachtrack();

            for (VendingMachine v : machTrack.getMachines()) {
                if (v.getId() == id) {
                    v.removeItem(item, quantity);
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
     * @return HashMap of vending machine list with vending machine ID as key and
     * location as value
     */
    @Override
    public HashMap<Integer, String> addVendMach(final int id, final String location) {
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
     * @return HashMap of vending machine list with vending machine ID as key and
     * location as value
     */
    @Override
    public HashMap<Integer, String> removeVendMach(final int id) {
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
     * @return HashMap of vending machine list with vending machine ID as key and
     * location as value
     */
    @Override
    public HashMap<Integer, String> changeLocation(final int id, final String location) {
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
     *
     * @param location Location name to be validated
     * @throws IllegalArgumentException if name is not valid.
     */
    private void validateLocation(final String location) {
        if (!Pattern.compile("[A-ZÆØÅ][a-zæøå]*( [A-ZÆØÅ][a-zæøå]*)*").matcher(location).matches()) {
            throw new IllegalArgumentException("Location name not valid");
        }
    }

    /**
     * Internal method for validating quantity.
     *
     * @param quantity Quantity to be validated
     * @throws IllegalArgumentException if quantity is not valid.
     */
    private void validateQuantity(final int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity has to be higher than zero");
        }
    }

    /**
     * Internal method for validating item name.
     *
     * @param item Item name to be validated
     * @throws IllegalArgumentException if item name is not valid.
     */
    private void validateItem(final String item) {
        if (!Pattern.compile("\\S(.*\\S)?").matcher(item).matches()) {
            throw new IllegalArgumentException("Item name not valid");
        }
    }

    /**
     * Internal method for validating if the vending machine with given ID exists.
     *
     * @param id The ID of the vending machine to be validated.
     * @throws IllegalArgumentException if no vending machine with given ID exists.
     */
    private void validateVendMachId(final int id) {
        for (VendingMachine vendMach : getMachtrack().getMachines()) {
            if (vendMach.getId() == id) {
                return;
            }
        }

        throw new IllegalArgumentException("No such Vending Machine with ID: " + id);
    }

    /**
     * Internal method for validating new ID for vending machine.
     *
     * @param id The ID to be validated
     * @throws IllegalArgumentException if new ID is not valid or already existing.
     */
    private void validateNewVendMachId(final int id) {
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
     *
     * @param id The ID of the vending machine
     * @return Vending Machine with given ID
     * @throws IllegalArgumentException if no vending machine with given ID exists.
     */
    private VendingMachine getVendMach(final int id) {
        return getMachtrack().getMachines()
                .stream()
                .filter(vendMach -> vendMach.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such Vending Machine with ID: " + id));
    }

    /**
     * Internal method for retrieving vending machine tracker.
     * @return Vending machine tracker object
     * @throws RuntimeException if no vending machine tracker could be found 
     */
    private MachineTracker getMachtrack() {
        return Optional.ofNullable(persistence.getVendmachtrack())
                .orElseThrow(() -> new RuntimeException("Vending Machine Tracker not found"));
    }
}
