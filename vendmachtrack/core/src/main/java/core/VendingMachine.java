package core;

import java.util.HashMap;

/**
 * Represents a vending machine with unique id, inventory, and location.
 * It implements the IVendingMachine interface.
 */

public class VendingMachine implements IVendingMachine {

    private HashMap<String, Integer> status = new HashMap<>();
    private int id;
    private String location;

    public VendingMachine() {

    }

    /**
     * Initializes a new vending machine with the provided id, inventory and location.
     *
     * @param id        The unique id of the vending machine.
     * @param inventory A map containing the items in the vending machine and their quantities. 
     * @param location  The location where the vending machine is placed.
     */
    public VendingMachine(int id, HashMap<String, Integer> inventory, String location) {
        this.status = new HashMap<>(inventory);
        this.id = id;
        this.location = location;
    }


    /**
     * Retrieves the current status of items in the vending machine.
     * 
     * @return A HashMap containing item names as keys and their quantities as values. 
     */
    @Override
    public HashMap<String, Integer> getStatus() {
        return new HashMap<>(this.status);
    }


    /**
     * Retrieves the unique indentifier of the vending machine. 
     * 
     * @return The id of the vending machine.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Retrieve the location of the vending machine. 
     * 
     * @return The location of the vending machine.
     */
    @Override
    public String getLocation() {
        return this.location;
    }


    /**
     * Adds a specified number of a particular item to the vending machine's inventory. 
     *
     * @param item   The name of the item to add.
     * @param number The number of the item to add.
     */
    @Override
    public void addItem(String item, int number) { //fylle opp hvis noen kjøper, vil evt fyllle opp fra fil... dvs. lese fra fil
        if (!status.containsKey(item)) {
            status.put(item, 0);
        }
        status.put(item, status.get(item) + number);
    }



    /**
     * Removes a specified number of a particular item from the vending machine's inventory (someone buys an item). 
     * If the quantity becomes zero, the item is no longer avaliable in the vending machine.
     *
     * @param item   The name of the item to remove.
     * @param number The number of items to remove.
     * @throws IllegalArgumentException If the item is not found in the vending machine's inventory.
     */
    @Override
    public void removeItem(String item, int number) {
        Integer itemCount = status.get(item); // Retrieve the count of the items

        if (itemCount == null) {
            throw new IllegalArgumentException("there is no item to remove");
        }

        if (itemCount == number) {
            status.remove(item);
        } else if (itemCount > number) {
            status.put(item, itemCount - number); // Use itemCount instead of status.get(item)
        } else {
            // This is the case where itemCount < number
            //migth want to resolve this another way
            throw new IllegalArgumentException("not enough items to remove");
        }
    }
    


    /**
     * Sets the inventory status of the vending machine. 
     *
     * @param status A HashMap containing item names as keys and their quantities as values.
     */
    @Override
    public void setStatus(HashMap<String, Integer> status) {
        this.status = new HashMap<>(status);
    }


    /**
     * Sets the unique indentifier of the vending machine. 
     *
     * @param id The id to set for the vending machine. 
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the location of the vending machine.
     *
     * @param location The location to set for the vending machine. 
     */
    @Override
    public void setLocation(String location) {
        this.location = location;
    }


    /**
     * Returns a string representation of the vending machine, including its id and location. 
     *
     * @param location A formatted string representing the vending machine.
     */
    @Override
    public String toString() {
        return "Machine: " + id + " (" + location + ")";
    }


}


