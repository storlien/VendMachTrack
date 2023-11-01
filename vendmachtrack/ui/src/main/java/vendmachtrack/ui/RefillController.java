package vendmachtrack.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import vendmachtrack.ui.access.AccessService;
import vendmachtrack.ui.access.MachineTrackerAccessible;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The RefillController class manages the user interface for refilling vending
 * machine items.
 * It provides methods to refill items, update the inventory display, and
 * navigate back to the main scene.
 */
public class RefillController {

    @FXML
    private Label title;

    @FXML
    private TextField refillItem;

    @FXML
    private TextField refillNumber;

    @FXML
    private Button refilllButton;

    @FXML
    private Button backButton;

    @FXML
    private TextArea textArea;

    @FXML
    private Label answerText;

    private MachineTrackerAccessible access;

    private App mainApp;
    private int selectedMachineID;

    /**
     * Updates the title label with the selected vending machine's ID.
     *
     * @param machineID The ID of the selected vending machine.
     */
    private void updateTitle(int machineID) {
        title.setText("Vending machine: " + machineID);
    }

    /**
     * Sets the ID of the selected vending machine.
     *
     * @param machineID The ID of the selected vending machine.
     */
    public void setSelectedMachineID(int machineID) {
        this.selectedMachineID = machineID;
        setInventory(access.getInventory(machineID));
        updateTitle(machineID);
    }

    /**
     * Sets the AccessService instance to access vending machine data.
     *
     * @param service The AccessService instance.
     */
    public void setAccessService(AccessService service) {
        try {
            this.access = service.getAccess();
        } catch (Exception e) {
            answerText.setText(e.getMessage());
        }

    }

    public void setInventory(Map<String, Integer> inventory) {
        try {
            StringBuilder formattedStatus = new StringBuilder("Inventory:\n");
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                formattedStatus.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            textArea.setText(formattedStatus.toString());
        } catch (Exception e) {
            textArea.setText(e.getMessage());

        }
    }

    /**
     * Sets the main application instance.
     *
     * @param mainApp The main application instance.
     */
    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Refills the vending machine item based on user input, updates the inventory
     * display,
     * and handles exceptions if any occur during the refill process.
     */
    @FXML
    private void refillItem() {
        HashMap<String, Integer> updatedInventory = new HashMap<>();
        if (!refillNumber.getText().matches("-?\\d+") || refillItem.getText().trim().isEmpty()) {
            answerText.setText("Invalid input: Please enter a valid number and item");
            return;
        }

        try {
            updatedInventory = access.addItem(selectedMachineID, refillItem.getText(),
                    Integer.parseInt(refillNumber.getText()));
            answerText.setText("");
        } catch (Exception e) {
            answerText.setText(e.getMessage());

        }

        try {
            StringBuilder formattedStatus = new StringBuilder("Inventory:\n");
            for (Map.Entry<String, Integer> entry : updatedInventory.entrySet()) {
                formattedStatus.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            textArea.setText(formattedStatus.toString());
            answerText.setText("");

        } catch (Exception e) {
            answerText.setText(e.getMessage());

        }

    }

    /**
     * Switches back to the main vending machine tracker scene.
     *
     * @param event The ActionEvent triggered by the user.
     * @throws IOException If an error occurs during scene transition.
     */
    @FXML
    public void switchToMainScene(ActionEvent event) throws IOException {
        mainApp.switchToMainScene(selectedMachineID);
    }

}
