package core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    /**
     * Tests the valid initialization of an Item object.
     * 
     * Creates an Item object with a name and price, and checks that the name and price are correctly set.
     */
    @Test
    public void testValidInitialization() {
        Item item = new Item("Soda", 1.5);
        assertEquals("Soda", item.getName());
        assertEquals(1.5, item.getPrice());
    }

    /**
     * Tests the initialization of an Item object with a negative price.
     * Expects an IllegalArgumentException to be thrown.
     */
    @Test
    public void testNegativePriceInitialization() {
        assertThrows(IllegalArgumentException.class, () -> new Item("Soda", -1));
    }

    /**
     * Tests that an {@link IllegalArgumentException} is thrown when an {@link Item} object is initialized with a price of zero.
     */
    @Test
    public void testZeroPriceInitialization() {
        assertThrows(IllegalArgumentException.class, () -> new Item("Soda", 0));
    }

    /**
     * Tests the {@link Item#getName()} method of the {@link Item} class.
     * 
     * Creates an {@link Item} object with the name "Chips" and price 2.5, and checks that the
     * {@link Item#getName()} method returns "Chips".
     */
    @Test
    public void testGetName() {
        Item item = new Item("Chips", 2.5);
        assertEquals("Chips", item.getName());
    }

    /**
     * Tests the getPrice() method of the Item class.
     * Creates an Item object with a name and price, and checks that the getPrice() method returns the correct price.
     */
    @Test
    public void testGetPrice() {
        Item item = new Item("Chips", 2.5);
        assertEquals(2.5, item.getPrice());
    }
}

