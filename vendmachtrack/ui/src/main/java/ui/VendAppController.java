package ui;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import core.IMachineTracker;
import core.VendingMachine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.TextArea;
import jsonio.FromJson;
import jsonio.IFromJson;
import jsonio.IToJson;
import jsonio.ToJson;

/**
 * Controller class for the vending machine application's user interface.
 */

public class VendAppController implements Initializable {

    @FXML
    private Label myLabel;

    @FXML
    private TextArea textArea;

    @FXML
    private Button button;

    @FXML
    private ChoiceBox<VendingMachine> menuBar;

    private IMachineTracker machtrack;

    /**
     * Initializes the controller. It reads vending machine data from a JSON file.
     * 
     * @param arg0 The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param arg1 The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        IFromJson fromJson = new FromJson("tracker.json");
        this.machtrack = fromJson.readFromFile();

        if (this.machtrack != null) {
            List<VendingMachine> machines = machtrack.getMachines();
            menuBar.getItems().addAll(machines);
        } else {
            myLabel.setText("Error! Cannot read file");
        }

    }

    public IMachineTracker getMachtrack() {
       // Use a copy constructor, clone method, or another appropriate way to copy the object
        IFromJson fromJson = new FromJson("tracker.json");
        IMachineTracker machtrackdeliver = fromJson.readFromFile();
        return machtrackdeliver;
       
    }

    /**
     * Handles the close event of the application. It writes the vending machine data back to the JSON file before closing. 
     */

    public void onClose() {

        if (this.machtrack != null) {
            IToJson toJson = new ToJson("tracker.json");
            toJson.writeToFile(machtrack);
        }
    }

    /**
     * Handles the button click event. It displays the inventory of the selected vending machine in the text area.
     * 
     * @param event The event representing the button click.
     */

    @FXML
    private void handleButtonClick(ActionEvent event) {
        VendingMachine selectedItem = menuBar.getValue();
        if (selectedItem != null) {
            Map<String, Integer> statusMap = selectedItem.getStatus();
            StringBuilder formattedStatus = new StringBuilder("Inventory:\n");
            for (Map.Entry<String, Integer> entry : statusMap.entrySet()) {
                formattedStatus.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            textArea.setText(formattedStatus.toString());
        } else {
            textArea.setText("No vending machine selected");
        }

    }


}