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

    private final String name;
    private final double price;

    /**
     * Constructor. Represents an item with a speciific name and price.
     *
     * @param newName  The name of the item.
     * @param newPrice The price of the item. Must be a positive value.
     * @throws IllegalArgumentException If the price is negative or zero value.
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