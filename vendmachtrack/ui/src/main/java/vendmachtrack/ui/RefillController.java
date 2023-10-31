package vendmachtrack.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import vendmachtrack.ui.access.AccessService;
import vendmachtrack.ui.access.MachineTrackerAccessible;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    // private AccessService service;
    // private AccessService service;
    private MachineTrackerAccessible access;

    private App mainApp;
    private int selectedMachineID;

    public void updateTitle(int machineID) {
        title.setText("Vending machine: " + machineID);
    }

    public void setSelectedMachineID(int machineID) {
        this.selectedMachineID = machineID;
        updateTitle(machineID);
    }

    public void setAccessService(AccessService service) {
        try {
            this.access = service.getAccess();
        } catch (Exception e) {
            answerText.setText(e.getMessage());
        }

    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void refillItem() {
        HashMap<String, Integer> updatedInventory = new HashMap<>();
        if (!refillNumber.getText().matches("-?\\d+")) {
            answerText.setText("Invalid input: Please enter a valid number");
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

    @FXML
    public void switchToMainScene(ActionEvent event) throws IOException {
        mainApp.switchToMainScene(selectedMachineID);
    }

}
