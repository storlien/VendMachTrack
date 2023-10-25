package vendmachtrack.ui;

import java.net.URL;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import vendmachtrack.ui.access.AccessService;
import vendmachtrack.ui.access.MachineTrackerAccessible;

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
    private Button refillButton;

    @FXML
    private ChoiceBox<String> menuBar;

    private MachineTrackerAccessible access;

    private AccessService service;

    public void setAccessService(AccessService service) {
        this.service = service;
    }

    /**
     * Initializes the controller. It reads vending machine data from a JSON file.
     *
     * @param arg0 The location used to resolve relative paths for the root object,
     *             or null if the location is not known.
     * @param arg1 The resources used to localize the root object, or null if the
     *             root object was not localized.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        URI endpointUri = URI.create("http://localhost:8080/");
        String fileName = "tracker.json";

        if (service == null) {
            Task<Void> newAccess = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    AccessService service = new AccessService(endpointUri, fileName);
                    access = service.getAccess();
                    postInitialize();
                    return null;
                }
            };

            new Thread(newAccess).start();
        } else {
            access = service.getAccess();
        }

    }

    private void postInitialize() {
        try {
            HashMap<Integer, String> vendingMachines = access.getVendMachList();
            List<String> machines = new ArrayList<>();
            for (Map.Entry<Integer, String> entry : vendingMachines.entrySet()) {
                machines.add("id: " + entry.getKey() + " (" + entry.getValue() + ")");
            }
            menuBar.getItems().addAll(machines);

        } catch (Exception e) {
            textArea.setText(e.getMessage());
        }
    }

    /**
     * Handles the button click event. It displays the inventory of the selected
     * vending machine in the text area.
     *
     * @param event The event representing the button click.
     */
    @FXML
    private void handleButtonClick(ActionEvent event) {

        if (findID() != 0) {
            Map<String, Integer> statusMap = access.getInventory(findID());
            StringBuilder formattedStatus = new StringBuilder("Inventory:\n");
            for (Map.Entry<String, Integer> entry : statusMap.entrySet()) {
                formattedStatus.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            textArea.setText(formattedStatus.toString());

        } else {
            textArea.setText("No vending machine selected");
        }

    }

    private int findID() {
        String selectedItem = menuBar.getValue();
        if (selectedItem != null) {
            int colonIndex = selectedItem.indexOf(":");
            int endIndex = selectedItem.indexOf("(");
            int id = Integer.parseInt(selectedItem.substring(colonIndex + 2, endIndex).trim());
            return id;
        } else {
            return 0;
        }
    }

}