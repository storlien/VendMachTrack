package vendmachtrack.core;

/**
 * Represents an item with a specific name and price.
 */

public class Item {

    private final String name;
    private final double price;


    /**
     * Constructor. Represents an item with a speciific name and price.
     *
     * @param name  The name of the item.
     * @param price The price of the item. Must be a positive value.
     * @throws IllegalArgumentException If the price is negative or zero value.
     */

    public Item(String name, double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price can't be negative or zero");
        }
        this.name = name;
        this.price = price;
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
     * Retrieves the price of the item
     *
     * @return The price of the item
     */
    public double getPrice() {
        return this.price;
    }


}
