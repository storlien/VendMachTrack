package vendmachtrack.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * This class contains test methods for testing the functionality of the {@link Item} class.
 * <p>
 * The tests in this class focus on various aspects of the Item class, including its initialization, accessors, and behavior under specific conditions.
 */
public class ItemTest {


    /**
     * Tests the valid initialization of an {@link Item} object.
     * 
     * This test case focuses on ensuring that an Item object can be initialized with the correct name and price values.
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create an Item object with the name "Soda" and a price of 1.5.</li>
     *   <li>Act & Assert: Verify that the getName() method returns "Soda" and the getPrice() method returns 1.5 for the created Item object.</li>
     * </ol>
     */
    @Test
    public void Item_testValidInitialization() {
        //Arrange 
        Item item = new Item("Soda", 1.5);

        //Act & Assert
        assertEquals("Soda", item.getName());
        assertEquals(1.5, item.getPrice());
    }

    /**
     * Tests the initialization of an {@link Item} object with a negative price, expecting an {@link IllegalArgumentException}.
     * 
     * This test case focuses on verifying that attempting to create an Item object with a negative price results in an {@link IllegalArgumentException}.
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Act & Assert: Attempt to create an Item object with the name "Soda" and a negative price of -1, and assert that it throws an {@link IllegalArgumentException}.</li>
     * </ol>
     */
    @Test
    public void Item_testNegativePriceInitialization() {

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Item("Soda", -1));
    }

    /**
     * Tests that an {@link IllegalArgumentException} is thrown when an {@link Item} object is initialized with a price of zero.
     */
    @Test
    public void Item_testZeroPriceInitialization() {

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Item("Soda", 0));
    }

    /**
     * Tests the {@link Item#getName()} method to ensure it returns the correct item name.
     * 
     * This test case focuses on verifying that the getName() method of an Item object returns the expected item name.
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create an Item object with the name "Chips" and a price of 2.5.</li>
     *   <li>Act & Assert: Call the getName() method on the Item object and verify that it returns "Chips," which is the expected item name.</li>
     * </ol>
     */
    @Test
    public void Item_testGetName() {

        //Arrange
        Item item = new Item("Chips", 2.5);

        //Act & Assert
        assertEquals("Chips", item.getName());
    }

    /**
     * Tests the {@link Item#getPrice()} method to ensure it returns the correct item price.
     * 
     * This test case focuses on verifying that the getPrice() method of an Item object returns the expected item price.
     * 
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create an Item object with the name "Chips" and a price of 2.5.</li>
     *   <li>Act & Assert: Call the getPrice() method on the Item object and verify that it returns 2.5, which is the expected item price.</li>
     * </ol>
     */
    @Test
    public void Item_testGetPrice(){

        //Arrange
        Item item = new Item("Chips", 2.5);

        //Act & Assert
        assertEquals(2.5, item.getPrice());
    }
}

