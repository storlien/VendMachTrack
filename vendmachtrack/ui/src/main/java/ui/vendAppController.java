package ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

public class vendAppController implements Initializable {

    private IMachineTracker machtrack;

    @FXML
    private Label myLabel;

    @FXML
    private TextArea textarea;

    @FXML
    private Button button;

    @FXML
    private ChoiceBox<VendingMachine> menuBar;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        IFromJson fromJson = new FromJson();
        IMachineTracker machtrack = fromJson.readFromFile();
        System.out.println("Objektet: " + fromJson.readFromFile());

        // List<VendingMachine> machines = machtrack.getMachines();
        // menuBar.getItems().addAll(machines);
        
        // List<Integer> machineID = new ArrayList<>();
        // for (VendingMachine vendingMachine : machines) {
        //    machineID.add(vendingMachine.getId());
        // }
        


    }

    

    public void onClose() {
        IToJson toJson = new ToJson(machtrack);
        toJson.writeToFile();
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        // Integer selectedItem = menuBar.getValue();
        // List<VendingMachine> machines = machtrack.getMachines();
        // String info = "";

        // for (VendingMachine vend : machines) {
        //     if(vend.getId() == selectedItem && selectedItem != 0){
        //         info = "Inventory: " + vend.getStatus();
        //         textarea.setText(info);
        //     }
            
        // }
        // if (!info.isEmpty()) {
        //     textarea.setText(info);
        // } else {
        //     textarea.setText("No vending machine selected");
        // }
    }


}
