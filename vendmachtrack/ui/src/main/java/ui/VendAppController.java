package ui;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.crypto.Mac;

import core.IMachineTracker;
import core.MachineTracker;
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
        IMachineTracker machdeliver = this.machtrack;
        return machdeliver;
    }



    public void onClose() {

        if (this.machtrack != null) {
            IToJson toJson = new ToJson("tracker.json");
            toJson.writeToFile(machtrack);
        }
    }

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