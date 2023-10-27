package vendmachtrack.ui;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import vendmachtrack.ui.access.AccessService;
import vendmachtrack.ui.access.MachineTrackerAccessible;

public class UserController {

    @FXML
    private Label mylabel;

    @FXML
    private Pane pane;

    @FXML
    private Button backButton;

    @FXML
    private Button buy;

    @FXML
    private VBox buttonContainer;

    @FXML
    private TextField chosenItem;

    private App mainApp;
    private int selectedMachineID;
    //    private AccessService service;
    private MachineTrackerAccessible access;

    public void setAccessService(AccessService service) {
//        this.service = service;
        this.access = service.getAccess();
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    public void updateButtons(int machineID, Map<String, Integer> inventory) {
        buttonContainer.getChildren().clear();

        VBox currentColumn = new VBox();
        currentColumn.setSpacing(20);

        int buttonsInCurrentColumn = 0;

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            String itemName = entry.getKey();
            int itemQuantity = entry.getValue();

            Button button = new Button(itemName + ": " + itemQuantity);
            button.getStyleClass().add("buttonContainer");

            button.setOnAction(e -> chosenItem.setText(itemName));

            currentColumn.getChildren().add(button);
            buttonsInCurrentColumn++;

            if (buttonsInCurrentColumn >= 5) {
                buttonContainer.getChildren().add(currentColumn);
                currentColumn = new VBox();
                currentColumn.setSpacing(20);
                buttonsInCurrentColumn = 0;
            }
        }

        if (buttonsInCurrentColumn > 0) {
            buttonContainer.getChildren().add(currentColumn);
        }
    }

    @FXML
    private void removeItem() {
        HashMap<String, Integer> newInventory = access.removeItem(selectedMachineID, chosenItem.getText(), 1);
        updateButtons(selectedMachineID, newInventory);

    }

    public void updateTitle(int machineID) {
        mylabel.setText("Vending machine: " + machineID);
    }

    public void setSelectedMachineID(int machineID) {
        this.selectedMachineID = machineID;
        updateTitle(selectedMachineID);
        updateButtons(selectedMachineID, access.getInventory(selectedMachineID));
    }

    @FXML
    public void switchToMainScene(ActionEvent event) throws IOException {
        mainApp.switchToMainScene(selectedMachineID);
    }

}