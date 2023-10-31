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
    private Button refillButton;

    @FXML
    private Button back;

    @FXML
    private TextArea textArea;

    @FXML
    private Text answerText;

    //    private AccessService service;
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
//        this.service = service;
        this.access = service.getAccess();
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void refillItem() {
        HashMap<String, Integer> updatedInventory = new HashMap<>();

        try {
            updatedInventory = access.addItem(selectedMachineID, refillItem.getText(),
                    Integer.parseInt(refillNumber.getText()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            StringBuilder formattedStatus = new StringBuilder("Inventory:\n");
            for (Map.Entry<String, Integer> entry : updatedInventory.entrySet()) {
                formattedStatus.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            textArea.setText(formattedStatus.toString());

        } catch (Exception e) {
            textArea.setText(e.getMessage());
        }

    }

    @FXML
    public void switchToMainScene(ActionEvent event) throws IOException {
        mainApp.switchToMainScene(selectedMachineID);
    }

}
