package ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import core.IMachineTracker;
import core.IVendingMachine;
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
    private ChoiceBox<IVendingMachine> menuBar;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        IFromJson fromJson = new FromJson();
        IMachineTracker machtrack = fromJson.readFromFile();
        //List<IVendingMachine> machine = menuBar.getItems().addAll(machtrack.getMachines());
        //List<IVendingMachine> list = new ArrayList<>();
        //for (IVendingMachine vendingMachine : machine) {
        //list.add(vendingMachine.getId());

        //}
        //menuBar.getItems().addAll(list);


    }

    ;

    public void onClose() {
        IToJson toJson = new ToJson(machtrack);
        toJson.writeToFile();
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        IVendingMachine selectedItem = menuBar.getValue();
        if (selectedItem != null) {
            String info = "Inventory:" + selectedItem.getStatus();
            textarea.setText(info);
        }
        textarea.setText("No vending machine selected");

    }


}
