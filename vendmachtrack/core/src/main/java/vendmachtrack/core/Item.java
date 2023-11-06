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

    private final String name;

    /**
     * The monetary value associated with this item.
     */

    private final double price;

    /**
     * Constructs a new instance of the Item class with the specified name and
     * price.
     *
     * @param newName  The name used to identify this item.
     * @param newPrice The monetary value of this item. This must always be a
     *                 positive
     *                 value.
     * @throws IllegalArgumentException If the price provided is negative or has a
     *                                  zero value.
     */
    public Item(final String newName, final double newPrice) {
        if (newPrice <= 0) {
            throw new IllegalArgumentException("Price can't be negative or zero");
        }
        this.name = newName;
        this.price = newPrice;
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

}