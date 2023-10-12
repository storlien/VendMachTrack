package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jsonio.FromJson;
import jsonio.IFromJson;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.testfx.matcher.control.TextInputControlMatchers;

import core.IMachineTracker;
import core.MachineTracker;
import core.VendingMachine;


public class VendAppControllerTest extends ApplicationTest {


 private VendAppController controller;

    /**
     * This method starts the application by loading the FXML file and setting up the stage.
     * 
     * @param stage The stage to be used for the application.
     * @throws Exception If there is an error loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // This uses the structure from your App class to start the application.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("App.fxml")); // Make sure to use the correct path to your FXML file.
        Parent parent = fxmlLoader.load();

        controller = fxmlLoader.getController();
        stage.setOnCloseRequest((WindowEvent event) -> {
            controller.onClose();
        });

        stage.setScene(new Scene(parent));
        stage.show();
    }

    /**
     * Tests the vending machine selection functionality of the VendAppController class.
     * Assumes that the ChoiceBox's items are populated at this point. Selects the first item in the ChoiceBox,
     * clicks the "OK" button, and verifies that the TextArea contains the expected text for the first vending machine.
     * Then, selects the second and third items in the ChoiceBox, clicks the "OK" button for each, and verifies that
     * the TextArea contains the expected text for the corresponding vending machines.
     *
     */
    @Test
    public void testVendingMachineSelection() {
        // Assuming the ChoiceBox's items are populated at this point,
        // we'll select the first item in the ChoiceBox. Adjust as necessary if you have a specific item to test.
        ChoiceBox choiceBox = lookup("#menuBar").queryAs(ChoiceBox.class);
        interact(() -> choiceBox.getSelectionModel().select(0));

        // Click the "OK" button.
        clickOn("#button");

        // Now, we'll verify that the TextArea contains the expected text.
        // Adjust the expected text based on the VendingMachine's status that you're testing.
        TextArea textArea = lookup("#textArea").queryAs(TextArea.class);
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("Inventory:\nCola: 5\nPepsi: 3\n"));

          // Second Vending Machine Selection and Verification

        interact(() -> choiceBox.getSelectionModel().select(1)); // select the second item
        clickOn("#button");

        // Verify the TextArea for the second vending machine. 
        // Change the expected text to match what's expected for your second machine.
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("Inventory:\nTuborg: 1\n")); 

        interact(() -> choiceBox.getSelectionModel().select(2)); // select the second item
        clickOn("#button");

        // Verify the TextArea for the third vending machine. 
        // Change the expected text to match what's expected for your second machine.
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("Inventory:\nHansa: 100\nRegnvann: 10\n")); 
    }


    // private Label myLabel;
    // private ChoiceBox<VendingMachine> menuBar;

    // @BeforeEach
    // public void setUp() {
    //     myLabel = new Label();
    //     menuBar = new ChoiceBox<>();

    //     controller = new VendAppController();
    //     controller.myLabel = myLabel;
    //     controller.menuBar = menuBar;
    // }

    // @Test
    // public void testInitializeWithNonNullMachineTracker() {
    //     VendingMachine machine1 = new VendingMachine(); 
    //     VendingMachine machine2 = new VendingMachine(); 
    //     List<VendingMachine> machines = Arrays.asList(machine1, machine2);
    //     MachineTracker machineTracker = new MachineTracker();
    //     machineTracker.setMachines(machines);

    //     IFromJson fromJson = new TestFromJson(machineTracker);
    //     controller.fromJson = fromJson;

    //     controller.initialize(null, null);

    //     ObservableList<VendingMachine> items = FXCollections.observableArrayList(machines);
    //     assertEquals(items, menuBar.getItems());
    //     assertEquals("", myLabel.getText());
    // }

}