package vendmachtrack.ui;

import javafx.stage.Stage;
import vendmachtrack.ui.access.AccessService;
import vendmachtrack.ui.access.MachineTrackerAccessible;
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

public class VendAppControllerTest extends ApplicationTest {

    private VendAppController controller;
    private HashMap<Integer, String> vendingMachines = new HashMap<>();

    @Mock
    private AccessService mockService;

    @Mock
    private MachineTrackerAccessible mockAccess;

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

    @Test
    public void testController_VenpAppController() {
        assertNotNull(this.controller);
    }

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

    @Test
    public void VendAppController_testMenuBarPopulation_selectVendingMachine() throws ConnectException {

        // Arrange
        HashMap<String, Integer> mockInventory = new HashMap<>();
        mockInventory.put("Cola", 5);
        mockInventory.put("Pepsi", 3);
        when(mockAccess.getInventory(1)).thenReturn(mockInventory);

        // Act
        @SuppressWarnings("unchecked")
        ChoiceBox<String> choiceBox = (ChoiceBox<String>) lookup("#menuBar").queryAs(ChoiceBox.class);
        interact(() -> choiceBox.getSelectionModel().select(0));

        clickOn("#okButton");

        // Assert
        TextArea textArea = lookup("#textArea").queryAs(TextArea.class);
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("Inventory:\nCola: 5\nPepsi: 3\n"));
    }

    @Test
    public void VendAppController_testMenuBarPopulation_selectNoVendingMachine() {

        // Act
        clickOn("#okButton");

        // Assert
        TextArea textArea = lookup("#textArea").queryAs(TextArea.class);
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("No vending machine selected"));
    }

    @Test
    public void VendAppController_testRefill_noVendingMachineSelected() {

        // Act
        clickOn("#refillButton");

        // Assert
        TextArea textArea = lookup("#textArea").queryAs(TextArea.class);
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("Please select a vending machine."));
    }

    @Test
    public void VendAppController_testUserview_noVendingMachineSelected() {

        // Act
        clickOn("#userView");

        // Assert
        TextArea textArea = lookup("#textArea").queryAs(TextArea.class);
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("Please select a vending machine."));
    }

    @Test
    public void VendAppController_testAddVendingMachine_validVendingMachine() throws ConnectException{
         
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

    @Test
    public void VendAppController_testAddVendingMachine_ExistingID() throws ConnectException{
        
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

    @Test
    public void VendAppController_testAddVendingMachine_InvalidLocation() throws ConnectException{
        
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

    @Test
    public void VendAppController_testAddVendingMachine_NoIdWritten() {

        // Act
        clickOn("#locationTextField").write("5");
        clickOn("#addButton");
        Label outputTextLabel = lookup("#outputText").query();

        // Assert
        assertEquals("Machine ID must be an integer", outputTextLabel.getText());
    }

    @Test
    public void VendAppController_testAddVendingMachine_NoLocationWritten() throws ConnectException{
        
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

    @Test
    public void VendAppController_testRemoveVendingMachine_removesVendingmachine() throws ConnectException{
        
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

    @Test
    public void VendAppController_testRemoveVendingMachine_invalidID() throws ConnectException{

        // Arrange
        when(mockAccess.removeVendMach(5)).thenThrow(new RuntimeException("No such Vending Machine with ID: 5"));

        // Act
        clickOn("#idTextFieldRemove").write("5");  // Use the ID for the existing vending machine
        clickOn("#removeButton");  // Click the remove button to attempt removing the vending machine
        Label outputTextLabel = lookup("#outputText").query();

        // Assert
        assertEquals("No such Vending Machine with ID: 5", outputTextLabel.getText());
    }

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
