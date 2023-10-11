package core;

import java.util.HashMap;

public class VendingMachine implements IVendingMachine {

    private HashMap<String, Integer> status = new HashMap<>();
    private int id;
    private String location;

    public VendingMachine() {

    }

    /**
     * Initializes a new VendingMachine
     *
     * @param id        the id of the vendingmachine
     * @param inventory an overview of which items the vendingmachine contains and the number of every item
     * @param location  the location of the vendingmachine
     */
    public VendingMachine(int id, HashMap<String, Integer> inventory, String location) {
        this.status = new HashMap<>(inventory);
        this.id = id;
        this.location = location;
    }


    /**
     * @return a hashMap containing every item in the vendingmachine and the belonging quantity
     */
    @Override
    public HashMap<String, Integer> getStatus() {
        return new HashMap<>(this.status);
    }


    /**
     * @return the id of the vendingmachine
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * @return the location of the vendingmachine
     */
    @Override
    public String getLocation() {
        return this.location;
    }


    /**
     * Add the `number` of the `item` to the vendingmachine
     *
     * @param item   the name of the item to add
     * @param number the number of the item to add
     */
    @Override
    public void addItem(String item, int number) { //fylle opp hvis noen kjøper, vil evt fyllle opp fra fil... dvs. lese fra fil
        if (!status.containsKey(item)) {
            status.put(item, 0);
        }
        status.put(item, status.get(item) + number);
    }



    /**
     * Removes a specified number of items from the vending machine
     *
     * @param item   the name of the item to remove
     * @param number the number of the item to remove
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
     * sets the status
     *
     * @param status
     */
    @Override
    public void setStatus(HashMap<String, Integer> status) {
        this.status = new HashMap<>(status);
    }


    /**
     * sets the id
     *
     * @param id
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * sets the location
     *
     * @param location
     */
    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return location + ", " + "ID: " + id;
    }


}


