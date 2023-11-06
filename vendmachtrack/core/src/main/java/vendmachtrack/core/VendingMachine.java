package vendmachtrack.core;

import java.util.HashMap;

/**
 * Represents a vending machine with unique id, inventory, and location.
 */
public class VendingMachine {

    private HashMap<String, Integer> status;
    private int id;
    private String location;

    public VendingMachine() {
        this.status = new HashMap<>();
    }

    /**
     * Initializes a new vending machine with the provided id, inventory, and location.
     *
     * @param newId        The unique id of the vending machine.
     * @param newInventory A map containing the items in the vending machine and their quantities.
     * @param newLocation  The location where the vending machine is placed.
     */
    public VendingMachine(final int newId, final HashMap<String, Integer> newInventory, final String newLocation) {
        this.status = new HashMap<>(newInventory);
        this.id = newId;
        this.location = newLocation;
    }

    /**
     * Retrieves the current status of items in the vending machine.
     *
     * @return A HashMap containing item names as keys and their quantities as values.
     */
    public HashMap<String, Integer> getStatus() {
        return new HashMap<>(this.status);
    }

    /**
     * Retrieves the unique identifier of the vending machine.
     *
     * @return The id of the vending machine.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retrieve the location of the vending machine.
     *
     * @return The location of the vending machine.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Adds a specified number of a particular item to the vending machine's inventory.
     *
     * @param item   The name of the item to add.
     * @param number The number of the item to add.
     */
    public void addItem(final String item, final int number) {
        if (!status.containsKey(item)) {
            status.put(item, number);
        } else {
            status.put(item, status.get(item) + number);
        }
    }

    /**
     * Removes a specified number of a particular item from the vending machine's inventory.
     * If the quantity becomes zero, the item is no longer available in the vending machine.
     *
     * @param item   The name of the item to remove.
     * @param number The number of items to remove.
     * @return true if the item was removed successfully, false otherwise.
     * @throws IllegalArgumentException If the item is not found in the vending machine's inventory.
     */
    public boolean removeItem(final String item, final int number) {
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
     * Sets the inventory status of the vending machine.
     *
     * @param newStatus A HashMap containing item names as keys and their quantities as values.
     */
    public void setStatus(final HashMap<String, Integer> newStatus) {
        this.status = new HashMap<>(newStatus);
    }

    /**
     * Sets the unique identifier of the vending machine.
     *
     * @param newId The id to set for the vending machine.
     */
    public void setId(final int newId) {
        this.id = newId;
    }

    /**
     * Sets the location of the vending machine.
     *
     * @param newLocation The location to set for the vending machine.
     */
    public void setLocation(final String newLocation) {
        this.location = newLocation;
    }
}
