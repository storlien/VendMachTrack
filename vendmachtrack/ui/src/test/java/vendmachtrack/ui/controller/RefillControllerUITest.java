package vendmachtrack.ui.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.net.ConnectException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.TextInputControlMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import vendmachtrack.ui.access.AccessService;
import vendmachtrack.ui.access.MachineTrackerAccessible;

/**
 * UI tests for the {@link RefillController} class.
 * This class contains a suite of UI tests to verify the behavior of the RefillController when interacting with the application's user interface.
 * Each test case focuses on specific UI interactions and expected outcomes.
 * 
 * <p>
 * Test cases cover various scenarios including valid input, invalid input, edge cases, and error handling to ensure the UI behaves as expected.
 * </p>
 * 
 * <p>
 * The tests in this class extend {@link ApplicationTest}
 * </p>
 */
public class RefillControllerUITest extends ApplicationTest {

    private RefillController refillController;
    private HashMap<Integer, String> vendingMachines = new HashMap<>();

    @Mock
    private AccessService mockService;

    @Mock
    private MachineTrackerAccessible mockAccess;


    /**
     * Sets up the testing environment and initializes the JavaFX application for testing the RefillController class.
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Mock the service and access objects to simulate interactions with external components.</li>
     *   <li>Mock a vending machine list and inventory data to provide test data for the application.</li>
     *   <li>Load the FXML file and initialize the RefillController with the mocked service and selected machine ID.</li>
     *   <li>Create and set up the JavaFX scene with the loaded parent and necessary styles.</li>
     *   <li>Show the JavaFX stage for testing.</li>
     * </ol>
     *
     * @param stage The JavaFX stage for the application.
     * @throws Exception if any error occurs during the setup.
     */
    @Override
    public void start(Stage stage) throws Exception {

        // Mock the service and access
        MockitoAnnotations.openMocks(this);
        when(mockService.getAccess()).thenReturn(mockAccess);

        // Mock a vending machine list
        vendingMachines.put(1, "Oslo");
        when(mockAccess.getVendMachLocation(1)).thenReturn("Oslo");
        HashMap<String, Integer> testInventory = new HashMap<>();
        testInventory.put("Cola", 1);
        testInventory.put("Hansa", 3);
        testInventory.put("Pepsi", 7);
        when(mockAccess.getInventory(anyInt())).thenReturn(testInventory);

        // Load the FXML and set the mocked service to the controller
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RefillApp.fxml"));
        Parent parent = fxmlLoader.load();
        this.refillController = fxmlLoader.getController();
        this.refillController.setAccessService(mockService);
        this.refillController.setSelectedMachineID(1);

        // Set the stage and show it
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Test case for the {@link RefillController} class's {@code testRefill} method with valid input.
     * Verifies that the method correctly refills an item in the inventory when valid input is provided.
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including a mock object for the access service with expected behavior, and configure input fields and UI elements.</li>
     *   <li>Act: Simulate user interaction by entering valid refill item information and clicking the refill button.</li>
     *   <li>Assert: Verify that the UI elements are updated as expected, including the text area displaying the updated inventory and the answer text field being empty.</li>
     * </ol>
     *
     * @throws ConnectException if there is a connection issue during the test.
     */
    @Test
    public void RefillController_testRefill_validInput() throws ConnectException {

        // Arrange
        HashMap<String, Integer> testInventory = new HashMap<>();
        testInventory.put("Cola", 2);
        testInventory.put("Hansa", 3);
        testInventory.put("Pepsi", 7);
        when(mockAccess.addItem(anyInt(), anyString(), anyInt())).thenReturn(testInventory);

        // Act
        clickOn("#refillNumber").write("1");
        clickOn("#refillItem").write("Cola");
        clickOn("#refilllButton");
        TextArea textArea = lookup("#textArea").queryAs(TextArea.class);
        Label textField = lookup("#answerText").queryAs(Label.class);

        // Assert
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("Inventory:\nCola: 2\nHansa: 3\nPepsi: 7\n"));
        assertEquals("", textField.getText());
    }

    /**
     * Test case for the {@link RefillController} class's {@code testRefill} method when an invalid number is provided.
     * Verifies that the method handles the scenario of an invalid number input and displays the appropriate error message.
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Act: Simulate user interaction by entering an invalid refill number (empty string) and a valid refill item, and clicking the refill button.</li>
     *   <li>Assert: Verify that the answer text field displays the expected error message indicating that the input is invalid.</li>
     * </ol>
     */
    @Test
    public void RefillController_testRefill_invalidNumber() {

        // Act
        clickOn("#refillNumber").write(""); // Use the ID for the existing vending machine
        clickOn("#refillItem").write("Cola");
        clickOn("#refilllButton");
        Label textField = lookup("#answerText").queryAs(Label.class);

        // Assert
        assertEquals("Invalid input: Please enter a valid number and item", textField.getText());
    }

    /**
     * Test case for the {@link RefillController} class's {@code testRefill} method when an invalid item is provided.
     * Verifies that the method handles the scenario of an invalid item input and displays the appropriate error message.
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Act: Simulate user interaction by entering a valid refill number and an invalid refill item (empty string), and clicking the refill button.</li>
     *   <li>Assert: Verify that the answer text field displays the expected error message indicating that the input is invalid.</li>
     * </ol>
     */
    @Test
    public void RefillController_testRefill_invalidItem() {

        // Act
        clickOn("#refillNumber").write("1"); // Use the ID for the existing vending machine
        clickOn("#refillItem").write("");
        clickOn("#refilllButton");
        Label textField = lookup("#answerText").queryAs(Label.class);

        // Assert
        assertEquals("Invalid input: Please enter a valid number and item", textField.getText());
    }

    /**
     * Test case for the {@link RefillController} class's {@code testRefill} method when a negative integer is provided as the refill number.
     * Verifies that the method handles the scenario of a negative integer input and displays the appropriate error message.
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Act: Simulate user interaction by entering a negative integer as the refill number and a valid refill item, and clicking the refill button.</li>
     *   <li>Assert: Verify that the answer text field displays the expected error message indicating that the input is invalid.</li>
     * </ol>
     */
    @Test
    public void RefillController_testRefill_negativeInteger() {

        // Act
        clickOn("#refillNumber").write("-1"); // Use the ID for the existing vending machine
        clickOn("#refillItem").write("Cola");
        clickOn("#refilllButton");
        Label textField = lookup("#answerText").queryAs(Label.class);

        // Assert
        assertEquals("Invalid input: Please enter a valid number and item", textField.getText());
    }

    /**
     * Test case for the {@link RefillController} class's {@code testRefill} method when refilling a new item.
     * Verifies that the method correctly adds a new item to the inventory when valid input is provided.
     * 
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Set up the necessary test data, including a mock object for the access service with expected behavior, and configure input fields and UI elements.</li>
     *   <li>Act: Simulate user interaction by entering valid refill information for a new item and clicking the refill button.</li>
     *   <li>Assert: Verify that the UI elements are updated as expected, including the text area displaying the updated inventory and the answer text field being empty.</li>
     * </ol>
     *
     * @throws ConnectException if there is a connection issue during the test.
     */
    @Test
    public void RefillController_testRefill_newItem() throws ConnectException {

        // Arrange
        HashMap<String, Integer> testInventory = new HashMap<>();
        testInventory.put("Cola", 1);
        testInventory.put("Hansa", 3);
        testInventory.put("Pepsi", 7);
        testInventory.put("Fanta", 1);
        when(mockAccess.addItem(anyInt(), anyString(), anyInt())).thenReturn(testInventory);

        // Act
        clickOn("#refillNumber").write("1");
        clickOn("#refillItem").write("Fanta");
        clickOn("#refilllButton");
        TextArea textArea = lookup("#textArea").queryAs(TextArea.class);
        Label textField = lookup("#answerText").queryAs(Label.class);

        // Assert
        FxAssert.verifyThat(textArea,
                TextInputControlMatchers.hasText("Inventory:\nCola: 1\nHansa: 3\nPepsi: 7\nFanta: 1\n"));
        assertEquals("", textField.getText());
    }
}
