package ui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        IFromJson fromJson = new FromJson();
        IMachineTracker machtrack = fromJson.readFromFile();

        List<VendingMachine> machines = machtrack.getMachines();
        menuBar.getItems().addAll(machines);
    
    }

    public void onClose() {
        IMachineTracker machtrack = new MachineTracker();
        IToJson toJson = new ToJson(machtrack);
        toJson.writeToFile();
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        VendingMachine selectedItem = menuBar.getValue();
        String info = "Inventory: " + selectedItem.getStatus();
        textArea.setText(info);

    }


}
