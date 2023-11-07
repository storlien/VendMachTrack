package vendmachtrack.ui;

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

public class RefillControllerTest extends ApplicationTest {

    private RefillController refillController;
    private HashMap<Integer, String> vendingMachines = new HashMap<>();

    @Mock
    private AccessService mockService;

    @Mock
    private MachineTrackerAccessible mockAccess;

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
