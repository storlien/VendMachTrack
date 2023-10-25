package vendmachtrack.ui;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vendmachtrack.ui.access.MachineTrackerAccessRemote;
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

    private MachineTrackerAccessible access;
    private int selectedMachine;
    private Stage stage;
    private Scene scene;

    public void updateButtons(int machineID, Map<String, Integer> inventory) {
        buttonContainer.getChildren().clear(); // Clear existing buttons

        VBox currentColumn = new VBox(); // Create a new column for buttons
        currentColumn.setSpacing(20); // Set spacing between buttons

        int buttonsInCurrentColumn = 0; // Counter for buttons in the current column

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            String itemName = entry.getKey();
            int itemQuantity = entry.getValue();

            Button button = new Button(itemName + ": " + itemQuantity);
            button.getStyleClass().add("buttonContainer"); // Add CSS class to the button

            button.setOnAction(e -> chosenItem.setText(itemName)); // Button event handling

            currentColumn.getChildren().add(button); // Add the button to the current column
            buttonsInCurrentColumn++;

            if (buttonsInCurrentColumn >= 5) {
                // If there are already 5 buttons in the column, add the column to
                // buttonContainer and create a new column
                buttonContainer.getChildren().add(currentColumn);
                currentColumn = new VBox();
                currentColumn.setSpacing(20); // Set spacing between buttons in the new column
                buttonsInCurrentColumn = 0; // Reset the button counter for the new column
            }
        }

        // Add the last remaining column if there are buttons in it
        if (buttonsInCurrentColumn > 0) {
            buttonContainer.getChildren().add(currentColumn);
        }
    }

    @FXML
    private void removeItem() {
        HashMap<String, Integer> newInventory = access.removeItem(selectedMachine, chosenItem.getText(), 1);
        updateButtons(selectedMachine, newInventory);

    }

    public void updateTitle(int machineID) {
        mylabel.setText("Vending machine: " + machineID);
    }

    public void setSelectedMachineID(int machineID) { // denne metoden sørger for at vi overfører den id-en som man er
                                                      // på i controllerklassen hit
        this.selectedMachine = machineID;
        System.out.println(selectedMachine);
        updateTitle(selectedMachine);
        access = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));
        updateButtons(selectedMachine, access.getInventory(selectedMachine));
    }

    @FXML
    public void backToHomePage(ActionEvent event) throws IOException { // legge til at den må vise passord
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}