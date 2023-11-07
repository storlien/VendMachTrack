package vendmachtrack.ui.access;

import java.net.ConnectException;
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
     *         location as value
     * @throws ConnectException Throws {@link ConnectException} if connection to server is lost
     */
    HashMap<Integer, String> getVendMachList() throws ConnectException;

    /**
     * Access method for the location of a vending machine
     *
     * @param id The ID of the vending machine
     * @return Location of vending machine
     * @throws ConnectException Throws {@link ConnectException} if connection to server is lost
     */
    String getVendMachLocation(int id) throws ConnectException;

    /**
     * Access method for inventory
     *
     * @param id The ID of the vending machine
     * @return HashMap of inventory with item as key and quantity as value
     * @throws ConnectException Throws {@link ConnectException} if connection to server is lost
     */
    HashMap<String, Integer> getInventory(int id) throws ConnectException;

    /**
     * Access method for adding item to vending machine's inventory
     *
     * @param id       The ID of the vending machine
     * @param item     The item name to be added
     * @param quantity The quantity of the item to be added
     * @return HashMap of inventory with item as key and quantity as value
     * @throws ConnectException Throws {@link ConnectException} if connection to server is lost
     */
    HashMap<String, Integer> addItem(int id, String item, int quantity) throws ConnectException;

    /**
     * Access method for removing a quantity of an item
     *
     * @param id       The ID of the vending machine
     * @param item     The item to be removed
     * @param quantity The quantity to be removed from the item
     * @return HashMap of inventory with item as key and quantity as value
     * @throws ConnectException Throws {@link ConnectException} if connection to server is lost
     */
    HashMap<String, Integer> removeItem(int id, String item, int quantity) throws ConnectException;

    /**
     * Access method for adding a new vending machine
     *
     * @param id       The ID of the new vending machine
     * @param location The location of the new vending machine
     * @return HashMap of vending machine list with vending machine ID as key and
     *         location as value
     * @throws ConnectException Throws {@link ConnectException} if connection to server is lost
     */
    HashMap<Integer, String> addVendMach(int id, String location) throws ConnectException;

    /**
     * Access method for removing a vending machine
     *
     * @param id The ID of the vending machine
     * @return HashMap of vending machine list with vending machine ID as key and
     *         location as value
     * @throws ConnectException Throws {@link ConnectException} if connection to server is lost
     */
    HashMap<Integer, String> removeVendMach(int id) throws ConnectException;

    /**
     * Access method for changing the location of a vending machine
     *
     * @param id       The ID of the vending machine
     * @param location The new location of the vending machine
     * @return HashMap of vending machine list with vending machine ID as key and
     *         location as value
     * @throws ConnectException Throws {@link ConnectException} if connection to server is lost
     */
    HashMap<Integer, String> changeLocation(int id, String location) throws ConnectException;
}