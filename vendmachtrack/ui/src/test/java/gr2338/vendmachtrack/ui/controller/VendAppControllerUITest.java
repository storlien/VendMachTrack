package gr2338.vendmachtrack.ui.controller;

import gr2338.vendmachtrack.ui.controller.VendAppController;
import javafx.stage.Stage;
import gr2338.vendmachtrack.ui.access.AccessService;
import gr2338.vendmachtrack.ui.access.MachineTrackerAccessible;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.TextInputControlMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.net.ConnectException;
import java.util.HashMap;

/**
 * This class contains UI test cases for the {@link VendAppController} class using JavaFX Application Test.
 * It focuses on testing the user interface and interaction with the application's GUI components.
 * <p>
 * Mocks are used to simulate the behavior of the AccessService and MachineTrackerAccessible interfaces.
 * The tests cover various scenarios related to the vending machine management application.
 *
 * @see VendAppController
 * @see ApplicationTest
 */
public class VendAppControllerUITest extends ApplicationTest {

    private VendAppController controller;
    private HashMap<Integer, String> vendingMachines = new HashMap<>();

    @Mock
    private AccessService mockService;

    @Mock
    private MachineTrackerAccessible mockAccess;

    /**
     * Initializes the test environment and sets up mocks for testing the {@link AppController} class.
     * It mocks the behavior of the AccessService and MachineTrackerAccessible, loads the FXML,
     * sets the mocked service to the controller, and initializes the scene for testing.
     *
     * @param stage The primary stage for the test application.
     * @throws Exception If an exception occurs during the test setup.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Mock the behavior of the AccessService and MachineTrackerAccessible
        when(mockService.getAccess()).thenReturn(mockAccess);

        // Mock a vending machine list for testing purposes
        vendingMachines.put(1, "Oslo");
        vendingMachines.put(2, "Trondheim");
        when(mockAccess.getVendMachList()).thenReturn(vendingMachines);

        // Load the FXML and set the mocked service to the controller
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("App.fxml"));
        Parent parent = fxmlLoader.load();
        this.controller = fxmlLoader.getController();
        this.controller.setAccessService(mockService);

        controller.updateVendMachList();

        // Set the scene and show the stage
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Test case for the {@link VenpAppController} class's initialization.
     * Verifies that the controller instance is not null after initialization.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Assert: Verify that the controller instance is not null after initialization.</li>
     * </ol>
     */
    @Test
    public void testController_VenpAppController() {
        assertNotNull(this.controller);
    }

    /**
     * Test case for the {@link VendAppController} class's menu bar population.
     * Verifies that the menu bar is populated with the expected number of items after updating the vending machine list.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Update the vending machine list using the controller.</li>
     *   <li>Act: Simulate clicking on the menu bar to trigger its population.</li>
     *   <li>Assert: Verify that the menu bar contains the expected number of items (choices).</li>
     * </ol>
     */
    @Test
    public void VendAppController_testMenuBarPopulation() {

        // Arrange
        controller.updateVendMachList();

        // Act
        ChoiceBox<String> menuBar = lookup("#menuBar").query();
        clickOn("#menuBar");

        // Assert
        assertEquals(2, menuBar.getItems().size());
    }

    /**
     * Test case for the {@link VendAppController} class's menu bar population and selection of a vending machine.
     * Verifies that the menu bar displays available vending machines, allows selecting one, and shows its inventory when selected.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the inventory data for a vending machine and set up the initial test conditions.</li>
     *   <li>Act: Simulate selecting a vending machine from the menu bar and clicking the OK button to display its inventory.</li>
     *   <li>Assert: Verify that the text area displays the expected inventory of the selected vending machine.</li>
     * </ol>
     *
     * @throws ConnectException if there is a connection issue during the test.
     */
    @Test
    public void VendAppController_testMenuBarPopulation_selectVendingMachine() throws ConnectException {

        // Arrange
        HashMap<String, Integer> mockInventory = new HashMap<>();
        mockInventory.put("Cola", 5);
        mockInventory.put("Pepsi", 3);
        when(mockAccess.getInventory(1)).thenReturn(mockInventory);

        // Act
        @SuppressWarnings("unchecked") // Suppressing unchecked cast warning because we are certain that the queried object is of type ChoiceBox<String>.
        ChoiceBox<String> choiceBox = (ChoiceBox<String>) lookup("#menuBar").queryAs(ChoiceBox.class);
        interact(() -> choiceBox.getSelectionModel().select(0));

        clickOn("#okButton");

        // Assert
        TextArea textArea = lookup("#textArea").queryAs(TextArea.class);
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("Inventory:\nCola: 5\nPepsi: 3\n"));
    }

    /**
     * Test case for the {@link VendAppController} class's menu bar population and attempting to select no vending machine.
     * Verifies that when the user does not select any vending machine from the menu bar and clicks the OK button,
     * an message is displayed indicating that no vending machine has been selected.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Act: Simulate clicking the OK button without selecting any vending machine from the menu bar.</li>
     *   <li>Assert: Verify that the text area displays the message indicating that no vending machine has been selected.</li>
     * </ol>
     */
    @Test
    public void VendAppController_testMenuBarPopulation_selectNoVendingMachine() {

        // Act
        clickOn("#okButton");

        // Assert
        TextArea textArea = lookup("#textArea").queryAs(TextArea.class);
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("No vending machine selected"));
    }

    /**
     * Test case for the {@link VendAppController} class's refill functionality when no vending machine is selected.
     * Verifies that when the user clicks the refill button without selecting any vending machine,
     * an appropriate message is displayed indicating that a vending machine should be selected first.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Act: Simulate clicking the refill button without selecting any vending machine.</li>
     *   <li>Assert: Verify that the text area displays the message indicating that a vending machine should be selected first.</li>
     * </ol>
     */
    @Test
    public void VendAppController_testRefill_noVendingMachineSelected() {

        // Act
        clickOn("#refillButton");

        // Assert
        TextArea textArea = lookup("#textArea").queryAs(TextArea.class);
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("Please select a vending machine."));
    }

    /**
     * Test case for the {@link VendAppController} class's refill functionality when no vending machine is selected.
     * Verifies that when the user clicks the refill button without selecting any vending machine,
     * an appropriate message is displayed indicating that a vending machine should be selected first.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Act: Simulate clicking the refill button without selecting any vending machine.</li>
     *   <li>Assert: Verify that the text area displays the message indicating that a vending machine should be selected first.</li>
     * </ol>
     */
    @Test
    public void VendAppController_testUserview_noVendingMachineSelected() {

        // Act
        clickOn("#userView");

        // Assert
        TextArea textArea = lookup("#textArea").queryAs(TextArea.class);
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("Please select a vending machine."));
    }

    /**
     * Test case for the {@link VendAppController} when adding a valid vending machine.
     * Verifies that when the user adds a new vending machine with a valid ID and location,
     * the vending machine is successfully added to the tracker, and the menu bar is updated with the new vending machine.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the behavior to add a new vending machine and set up initial test conditions.</li>
     *   <li>Act: Simulate entering a valid ID and location, clicking the add button, and checking the menu bar and output text.</li>
     *   <li>Assert: Verify that the vending machine is added successfully, and the menu bar and output text are updated accordingly.</li>
     * </ol>
     *
     * @throws ConnectException if there is a connection issue during the test.
     */
    @Test
    public void VendAppController_testAddVendingMachine_validVendingMachine() throws ConnectException {

        // Arrange
        when(mockAccess.addVendMach(anyInt(), anyString())).thenAnswer(invocation -> {
            int id = invocation.getArgument(0);
            String location = invocation.getArgument(1);
            vendingMachines.put(id, location);
            return vendingMachines;
        });

        //Act
        clickOn("#idTextFieldAdd").write("3");  // Add an ID for the new vending machine
        clickOn("#locationTextField").write("Bergen");  // Add a location for the new vending machine
        clickOn("#addButton");  // Click the add button to add the vending machine
        ChoiceBox<String> menuBar = lookup("#menuBar").query();
        Label outputTextLabel = lookup("#outputText").query();


        //Assert
        assertEquals("The machine 3 was successfully added to your tracker ", outputTextLabel.getText());
        assertEquals(3, menuBar.getItems().size());
        assertTrue(menuBar.getItems().contains("id: 3 (Bergen)"));
    }

    /**
     * Test case for the {@link VendAppController} class's addition of a vending machine with an existing ID.
     * Verifies that when the user attempts to add a new vending machine with an existing ID,
     * an appropriate error message is displayed indicating that the machine with that ID already exists.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the behavior to throw an exception when attempting to add a vending machine with an existing ID.</li>
     *   <li>Act: Simulate entering an existing ID, a different location, clicking the add button, and checking the output text.</li>
     *   <li>Assert: Verify that the appropriate error message is displayed in the output text.</li>
     * </ol>
     *
     * @throws ConnectException if there is a connection issue during the test.
     */
    @Test
    public void VendAppController_testAddVendingMachine_ExistingID() throws ConnectException {

        // Arrange
        when(mockAccess.addVendMach(2, "Random Location"))
                .thenThrow(new RuntimeException("Machine with ID: 2 already exists"));

        // Act
        clickOn("#idTextFieldAdd").write("2");  // Use the ID for the existing vending machine
        clickOn("#locationTextField").write("Random Location");  // Add a different location for testing
        clickOn("#addButton");  // Click the add button to attempt adding the vending machine
        Label outputTextLabel = lookup("#outputText").query();

        // Assert
        assertEquals("Machine with ID: 2 already exists", outputTextLabel.getText());
    }

    /**
     * Test case for the {@link VendAppController} class's addition of a vending machine with an invalid ID.
     * Verifies that when the user attempts to add a new vending machine with an invalid (non-integer) ID,
     * an appropriate error message is displayed indicating that the machine ID must be an integer.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Act: Simulate entering an invalid (non-integer) ID, a location, clicking the add button, and checking the output text.</li>
     *   <li>Assert: Verify that the appropriate error message is displayed in the output text.</li>
     * </ol>
     */
    @Test
    public void VendAppController_testAddVendingMachine_InvalidID() {

        // Act
        clickOn("#idTextFieldAdd").write("NaN"); // Use the ID for the existing vending machine
        clickOn("#locationTextField").write("Random Location"); // Add a different location for testing
        clickOn("#addButton"); // Click the add button to attempt adding the vending machine
        Label outputTextLabel = lookup("#outputText").query();

        // Assert
        assertEquals("Machine ID must be an integer", outputTextLabel.getText());
    }

    /**
     * Test case for the {@link VendAppController} class's addition of a vending machine with an invalid location.
     * Verifies that when the user attempts to add a new vending machine with an invalid location name,
     * an appropriate error message is displayed indicating that the location name is not valid.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the behavior to throw an exception when attempting to add a vending machine with an invalid location.</li>
     *   <li>Act: Simulate entering a valid ID, an invalid location name, clicking the add button, and checking the output text.</li>
     *   <li>Assert: Verify that the appropriate error message is displayed in the output text.</li>
     * </ol>
     *
     * @throws ConnectException if there is a connection issue during the test.
     */
    @Test
    public void VendAppController_testAddVendingMachine_InvalidLocation() throws ConnectException {

        // Arrange
        when(mockAccess.addVendMach(5, "5"))
                .thenThrow(new RuntimeException("Location Name not valid"));

        // Act
        clickOn("#idTextFieldAdd").write("5");  // Use the ID for the existing vending machine
        clickOn("#locationTextField").write("5");  // Add a different location for testing
        clickOn("#addButton");  // Click the add button to attempt adding the vending machine
        Label outputTextLabel = lookup("#outputText").query();

        // Assert
        assertEquals("Location Name not valid", outputTextLabel.getText());
    }

    /**
     * Test case for the {@link VendAppController} class's addition of a vending machine when no ID is written.
     * Verifies that when the user attempts to add a new vending machine without entering an ID,
     * an appropriate error message is displayed indicating that the machine ID must be an integer.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Act: Simulate not entering an ID, entering a location, clicking the add button, and checking the output text.</li>
     *   <li>Assert: Verify that the appropriate error message is displayed in the output text.</li>
     * </ol>
     */
    @Test
    public void VendAppController_testAddVendingMachine_NoIdWritten() {

        // Act
        clickOn("#locationTextField").write("5");
        clickOn("#addButton");
        Label outputTextLabel = lookup("#outputText").query();

        // Assert
        assertEquals("Machine ID must be an integer", outputTextLabel.getText());
    }

    /**
     * Test case for the {@link VendAppController} class's addition of a vending machine when no location is written.
     * Verifies that when the user attempts to add a new vending machine without entering a location name,
     * an appropriate error message is displayed indicating that the location name is not valid.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the behavior to throw an exception when attempting to add a vending machine with an empty location.</li>
     *   <li>Act: Simulate entering a valid ID, not entering a location, clicking the add button, and checking the output text.</li>
     *   <li>Assert: Verify that the appropriate error message is displayed in the output text.</li>
     * </ol>
     *
     * @throws ConnectException if there is a connection issue during the test.
     */
    @Test
    public void VendAppController_testAddVendingMachine_NoLocationWritten() throws ConnectException {

        // Arrange
        when(mockAccess.addVendMach(5, ""))
                .thenThrow(new RuntimeException("Location Name not valid"));

        // Act
        clickOn("#idTextFieldAdd").write("5");  // Use the ID for the existing vending machine
        clickOn("#addButton");  // Click the add button to attempt adding the vending machine
        Label outputTextLabel = lookup("#outputText").query();

        // Assert
        assertEquals("Location Name not valid", outputTextLabel.getText());
    }

    /**
     * Test case for the {@link VendAppController} class's removal of a vending machine.
     * Verifies that when the user attempts to remove an existing vending machine,
     * it is successfully removed from the tracker, and the appropriate confirmation message is displayed.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the behavior to remove a vending machine from the tracker when requested.</li>
     *   <li>Act: Simulate entering the ID of an existing vending machine, clicking the remove button, and checking the output text and menu bar.</li>
     *   <li>Assert: Verify that the appropriate confirmation message is displayed, and the vending machine is removed from the menu bar.</li>
     * </ol>
     *
     * @throws ConnectException if there is a connection issue during the test.
     */
    @Test
    public void VendAppController_testRemoveVendingMachine_removesVendingmachine() throws ConnectException {

        // Arrange
        when(mockAccess.removeVendMach(1)).thenAnswer(invocation -> {
            vendingMachines.remove(1);
            return vendingMachines;
        });

        // Act
        clickOn("#idTextFieldRemove").write("1");  // Use the ID for the existing vending machine
        clickOn("#removeButton");  // Click the remove button to attempt removing the vending machine
        Label outputTextLabel = lookup("#outputText").query();
        ChoiceBox<String> menuBar = lookup("#menuBar").query();

        // Assert
        assertEquals("The machine 1 was successfully removed from your tracker", outputTextLabel.getText());
        assertFalse(menuBar.getItems().contains("id: 1 (Oslo)"));
    }

    /**
     * Test case for the {@link VendAppController} class's removal of a vending machine with an invalid ID.
     * Verifies that when the user attempts to remove a vending machine with an invalid ID,
     * an appropriate error message is displayed indicating that there is no such vending machine with the given ID.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Arrange: Mock the behavior to throw an exception when attempting to remove a vending machine with an invalid ID.</li>
     *   <li>Act: Simulate entering an invalid ID, clicking the remove button, and checking the output text.</li>
     *   <li>Assert: Verify that the appropriate error message is displayed in the output text.</li>
     * </ol>
     *
     * @throws ConnectException if there is a connection issue during the test.
     */
    @Test
    public void VendAppController_testRemoveVendingMachine_invalidID() throws ConnectException {

        // Arrange
        when(mockAccess.removeVendMach(5)).thenThrow(new RuntimeException("No such Vending Machine with ID: 5"));

        // Act
        clickOn("#idTextFieldRemove").write("5");  // Use the ID for the existing vending machine
        clickOn("#removeButton");  // Click the remove button to attempt removing the vending machine
        Label outputTextLabel = lookup("#outputText").query();

        // Assert
        assertEquals("No such Vending Machine with ID: 5", outputTextLabel.getText());
    }

    /**
     * Test case for the {@link VendAppController} class's removal of a vending machine with a non-integer ID.
     * Verifies that when the user attempts to remove a vending machine with a non-integer ID,
     * an appropriate error message is displayed indicating that the machine ID must be an integer.
     *
     * <p>
     * Test Steps:
     * </p>
     * <ol>
     *   <li>Act: Simulate entering a non-integer ID, clicking the remove button, and checking the output text.</li>
     *   <li>Assert: Verify that the appropriate error message is displayed in the output text.</li>
     * </ol>
     */
    @Test
    public void VendAppController_testRemoveVendingMachine_notInteger() {

        // Act
        clickOn("#idTextFieldRemove").write("NaN"); // Use the ID for the existing vending machine
        clickOn("#removeButton"); // Click the remove button to attempt removing the vending machine
        Label outputTextLabel = lookup("#outputText").query();

        // Assert
        assertEquals("Machine ID must be an integer", outputTextLabel.getText());
    }
}
