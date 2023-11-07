package vendmachtrack.ui.access;

import java.util.HashMap;

/**
 * Interface for accessing Vending Machine Tracker. For instance via REST API or
 * direct file access
 */
public interface MachineTrackerAccessible {

    /**
     * Access method for the list of vending machines
     *
     * @return HashMap of vending machine list with vending machine ID as key and
     * location as value
     */
    HashMap<Integer, String> getVendMachList();

    /**
     * Access method for the location of a vending machine
     *
     * @param id The ID of the vending machine
     * @return Location of vending machine
     */
    String getVendMachLocation(int id);

    /**
     * Access method for inventory
     *
     * @param id The ID of the vending machine
     * @return HashMap of inventory with item as key and quantity as value
     */
    HashMap<String, Integer> getInventory(int id);

    /**
     * Access method for adding item to vending machine's inventory
     *
     * @param id       The ID of the vending machine
     * @param item     The item name to be added
     * @param quantity The quantity of the item to be added
     * @return HashMap of inventory with item as key and quantity as value
     */
    HashMap<String, Integer> addItem(int id, String item, int quantity);

    /**
     * Access method for removing a quantity of an item
     *
     * @param id       The ID of the vending machine
     * @param item     The item to be removed
     * @param quantity The quantity to be removed from the item
     * @return HashMap of inventory with item as key and quantity as value
     */
    HashMap<String, Integer> removeItem(int id, String item, int quantity);

    /**
     * Access method for adding a new vending machine
     *
     * @param id       The ID of the new vending machine
     * @param location The location of the new vending machine
     * @return HashMap of vending machine list with vending machine ID as key and
     * location as value
     */
    HashMap<Integer, String> addVendMach(int id, String location);

    /**
     * Access method for removing a vending machine
     *
     * @param id The ID of the vending machine
     * @return HashMap of vending machine list with vending machine ID as key and
     * location as value
     */
    HashMap<Integer, String> removeVendMach(int id);

    /**
     * Access method for changing the location of a vending machine
     *
     * @param id       The ID of the vending machine
     * @param location The new location of the vending machine
     * @return HashMap of vending machine list with vending machine ID as key and
     * location as value
     */
    HashMap<Integer, String> changeLocation(int id, String location);
}