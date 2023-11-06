package vendmachtrack.core;

import java.util.HashMap;

/**
 * Represents a vending machine with its essential attributes and behaviors.
 * <p>
 * The VendingMachine class captures the core characteristics of a vending
 * machine including
 * its unique identifier, location, and the inventory status. The inventory is
 * managed using a
 * hashmap where the items are keys and their quantities serve as values. The
 * class provides
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
        this.status = new HashMap<>();
    }

    /**
     * Initializes a new vending machine with the provided id, inventory, and
     * location.
     *
     * @param newId        The unique id of the vending machine.
     * @param newInventory A map containing the items in the vending machine and
     *                     their quantities.
     * @param newLocation  The location where the vending machine is placed.
     */
    public VendingMachine(final int newId, final HashMap<String, Integer> newInventory, final String newLocation) {
        this.status = new HashMap<>(newInventory);
        this.id = newId;
        this.location = newLocation;
    }

    /**
     * Returns a defensive copy of the vending machine's inventory status.
     *
     * @return A map representing the inventory with item names as keys and their
     *         quantities as values.
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
     * If the item does not exist in the inventory, it gets added; otherwise, its
     * quantity is updated.
     *
     * @param item   The name of the item.
     * @param number The quantity of the item to be added.
     */
    public void addItem(final String item, final int number) {
        if (!status.containsKey(item)) {
            status.put(item, number);
        } else {
            status.put(item, status.get(item) + number);
        }
    }

    /**
     * Modifies the inventory by removing a specified quantity of an item.
     * If the remaining quantity becomes zero after removal, the item is removed
     * from the inventory.
     * If the item does not exist or the specified quantity to remove exceeds the
     * available amount, no action is taken.
     *
     * @param item   The name of the item.
     * @param number The quantity of the item to be removed.
     * @return True if removal was successful, false otherwise.
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
     * Updates the vending machine's inventory status.
     *
     * @param newStatus A HashMap containing item names as keys and their quantities
     *                  as values.
     */
    public void setStatus(final HashMap<String, Integer> newStatus) {
        this.status = new HashMap<>(newStatus);
    }

    /**
     * Updates the unique identifier of the vending machine.
     *
     * @param newId The id to set for the vending machine.
     */
    public void setId(final int newId) {
        this.id = newId;
    }

    /**
     * Sets the physical location of the vending machine.
     *
     * @param newLocation The location to set for the vending machine.
     */
    public void setLocation(final String newLocation) {
        this.location = newLocation;
    }
}
