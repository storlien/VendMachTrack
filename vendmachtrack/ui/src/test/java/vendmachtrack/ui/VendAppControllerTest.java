package vendmachtrack.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.TextInputControlMatchers;
import vendmachtrack.core.MachineTracker;
import vendmachtrack.core.VendingMachine;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class VendAppControllerTest extends ApplicationTest {


    private VendAppController controller;
    private MachineTracker tracker;
    private final MachineTracker trackerTest = new MachineTracker();

    /**
     * This method starts the application by loading the FXML file and setting up the stage.
     *
     * @param stage The stage to be used for the application.
     * @throws Exception If there is an error loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // This uses the structure from the App class to start the application.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("App.fxml")); // Make sure to use the correct path to FXML file.
        Parent parent = fxmlLoader.load();
        this.controller = fxmlLoader.getController();

        //this.tracker = this.controller.getMachtrack();

        stage.setOnCloseRequest((WindowEvent event) -> {
            //this.controller.onClose();
        });

        stage.setScene(new Scene(parent));
        stage.show();
    }


    /**
     * Sets up the test fixture before each test method runs.
     * Initializes a VendingMachine object with a HashMap of product names and quantities,
     * and adds it to the trackerTest object.
     */
    @BeforeEach
    public void setup() {
        //same as a vendingmachine in Tracker.Json (might read instead)
        HashMap<String, Integer> status = new HashMap<>();
        status.put("Cola", 5);
        status.put("Pepsi", 3);
        VendingMachine machine1 = new VendingMachine(1, status, "Trondhjem");

        this.trackerTest.addVendingMachine(machine1);

    }

    /**
     * Tests the VendAppController class.
     * Ensures that the controller and tracker objects are not null.
     */
    @Test
    public void testController_VenpAppController() {
        assertNotNull(this.controller);
        assertNotNull(this.tracker);
    }


    /**
     * Tests the vending machine controller by comparing the ID, location, and status of the vending machine
     * in the test tracker with the vending machine in the actual tracker.
     */
    @Test
    public void testController_vendingMachine() {
        assertEquals(trackerTest.getMachines().get(0).getId(), this.tracker.getMachines().get(0).getId());
        assertEquals(trackerTest.getMachines().get(0).getLocation(), this.tracker.getMachines().get(0).getLocation());
        assertEquals(trackerTest.getMachines().get(0).getStatus(), this.tracker.getMachines().get(0).getStatus());
    }

    /**
     * Tests the vending machine selection functionality of the VendAppController class.
     * Assumes that the ChoiceBox's items are populated at this point. Selects the first item in the ChoiceBox,
     * clicks the "OK" button, and verifies that the TextArea contains the expected text for the first vending machine.
     * Then, selects the second and third items in the ChoiceBox, clicks the "OK" button for each, and verifies that
     * the TextArea contains the expected text for the corresponding vending machines.
     */
    @Test
    public void testVendingMachineSelection() {
        // Assuming the ChoiceBox's items are populated at this point,
        // we'll select the first item in the ChoiceBox. 
        ChoiceBox choiceBox = lookup("#menuBar").queryAs(ChoiceBox.class);
        interact(() -> choiceBox.getSelectionModel().select(0));

        // Click the "OK" button.
        clickOn("#button");

        // verify that the TextArea contains the expected text.
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

    /**
     * Tests the behavior of the "OK" button when no vending machine is selected in the ChoiceBox.
     */
    @Test
    public void testButtonClickWithNoSelection() {
        // Deselect any currently selected item in the ChoiceBox.
        ChoiceBox choiceBox = lookup("#menuBar").queryAs(ChoiceBox.class);
        interact(() -> choiceBox.getSelectionModel().clearSelection());

        // Click the "OK" button.
        clickOn("#button");

        // Verify the TextArea shows the "No vending machine selected" message.
        TextArea textArea = lookup("#textArea").queryAs(TextArea.class);
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("No vending machine selected"));
    }

    /**
     * This method tests the existence of UI elements in the VendAppController.
     * It checks if the Label, TextArea, Button and ChoiceBox elements exist.
     * If any of the elements do not exist, the test will fail.
     */
    @Test
    public void testUIElementsExistence() {
        assertNotNull(lookup("#myLabel").queryAs(Label.class));
        assertNotNull(lookup("#textArea").queryAs(TextArea.class));
        assertNotNull(lookup("#button").queryAs(Button.class));
        assertNotNull(lookup("#menuBar").queryAs(ChoiceBox.class));
    }


}