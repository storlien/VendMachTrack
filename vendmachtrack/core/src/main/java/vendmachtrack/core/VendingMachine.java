package vendmachtrack.core;

import java.util.HashMap;

/**
 * Represents a vending machine with its essential attributes and behaviors.
 * <p>
 * The VendingMachine class captures the core characteristics of a vending machine including
 * its unique identifier, location, and the inventory status. The inventory is managed using a
 * hashmap where the items are keys and their quantities serve as values. The class provides
 * methods to manipulate and query these attributes.
 * </p>
 */
public class VendingMachine {

    /**
     * A HashMap holding the inventory status of the vending machine.
     */
    private HashMap<String, Integer> status = new HashMap<>();

    /**
     * A unique identifier for the vending machine.
     */
    private int id;

    /**
     * The physical location of the vending machine.
     */
    private String location;

    /**
     * Default constructor for VendingMachine.
     */
    public VendingMachine() {

    }

    /**
     * Initializes a new vending machine with the specified attributes.
     *
     * @param id        The unique identifier for the vending machine.
     * @param inventory A map representing the initial inventory status with item names as keys and their quantities as values.
     * @param location  The physical location where the vending machine is placed.
     */
    public VendingMachine(int id, HashMap<String, Integer> inventory, String location) {
        this.status = new HashMap<>(inventory);
        this.id = id;
        this.location = location;
    }


    /**
     * Returns a defensive copy of the vending machine's inventory status.
     *
     * @return A map representing the inventory with item names as keys and their quantities as values.
     */
    public HashMap<String, Integer> getStatus() {
        return new HashMap<>(this.status);
    }


    /**
     * Gets the unique identifier of the vending machine.
     *
     * @return The identifier of the vending machine.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retrieves the physical location of the vending machine.
     *
     * @return The location of the vending machine.
     */
    public String getLocation() {
        return this.location;
    }


    /**
     * Modifies the inventory by adding a specified quantity of an item.
     * If the item does not exist in the inventory, it gets added; otherwise, its quantity is updated.
     *
     * @param item   The name of the item.
     * @param number The quantity of the item to be added.
     */
    public void addItem(String item, int number) {
        if (!status.containsKey(item)) {
            status.put(item, number);
        } else {
            status.put(item, status.get(item) + number);
        }
    }


    /**
     * Modifies the inventory by removing a specified quantity of an item.
     * If the remaining quantity becomes zero after removal, the item is removed from the inventory.
     * If the item does not exist or the specified quantity to remove exceeds the available amount, no action is taken.
     *
     * @param item   The name of the item.
     * @param number The quantity of the item to be removed.
     * @return True if removal was successful, false otherwise.
     */
    public boolean removeItem(String item, int number) {
        Integer itemCount = status.get(item);

        if (itemCount == null) {
            return false;
        }

        if (itemCount == number) {
            status.remove(item);
            return true;

        } else if (itemCount > number) {
            status.put(item, itemCount - number);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Updates the vending machine's inventory status.
     *
     * @param status A map representing the new inventory status.
     */
    public void setStatus(HashMap<String, Integer> status) {
        this.status = new HashMap<>(status);
    }


    /**
     * Updates the unique identifier of the vending machine.
     *
     * @param id The new identifier for the vending machine.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the physical location of the vending machine.
     *
     * @param location The new location for the vending machine.
     */
    public void setLocation(String location) {
        this.location = location;
    }

}