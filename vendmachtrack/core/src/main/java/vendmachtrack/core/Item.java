package vendmachtrack.core;

/**
 * This class encapsulates the concept of an item within a vending machine.
 * <p>
 * Each item has a distinct name and an associated price. The price of an item
 * must always be a positive value, representing the monetary value associated
 * with purchasing this particular item.
 * </p>
 */
public class Item {

    /**
     * The name by which the item is identified.
     */
    private String name;

    /**
     * The monetary value associated with this item.
     */
    private double price;


    /**
     * Constructs a new instance of the Item class with the specified name and price.
     *
     * @param name  The name used to identify this item.
     * @param price The monetary value of this item. This must always be a positive value.
     * @throws IllegalArgumentException If the price provided is negative or has a zero value.
     */
    public Item(String name, double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price can't be negative or zero");
        }
        this.name = name;
        this.price = price;
    }

    /**
     * Retrieves the name associated with this item.
     *
     * @return The name of the item.
     */
    public String getName() {
        return this.name;
    }


    /**
     * Retrieves the price associated with this item.
     *
     * @return The monetary value of this item.
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Updates the name of this item to the specified value.
     *
     * @param newName The new name for this item.
     */
    public void setName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        this.name = newName;
    }

    /**
     * Updates the price of this item to the specified value.
     *
     * @param newPrice The new price for this item. This must always be a positive value.
     * @throws IllegalArgumentException If the new price provided is negative or has a zero value.
     */
    public void setPrice(double newPrice) {
        if (newPrice <= 0) {
            throw new IllegalArgumentException("Price can't be negative or zero");
        }
        this.price = newPrice;
    }

}