package vendmachtrack.core;

/**
 * Represents an item with a specific name and price.
 */
public class Item {

    private final String name;
    private final double price;

    /**
     * Constructor. Represents an item with a specific name and price.
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
     * Retrieves the name of the item.
     *
     * @return The name of the item.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the price of the item.
     *
     * @return The price of the item.
     */
    public double getPrice() {
        return this.price;
    }
}
