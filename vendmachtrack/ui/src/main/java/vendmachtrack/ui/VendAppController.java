package vendmachtrack.ui;

import java.io.IOException;
import java.net.URL;
import java.net.URI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.Parent;
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
    private Button userView;

    @FXML
    private ChoiceBox<String> menuBar;

    private App mainApp;

    private MachineTrackerAccessible access;

    private AccessService service;

    public void setAccessService(AccessService service) {
        this.service = service;
        this.access = service.getAccess();
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
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

        Task<Void> newAccess = new Task<>() {
            @Override
            protected Void call() throws Exception {
                AccessService service = new AccessService(endpointUri, fileName);
                setAccessService(service);
                updateVendMachList();
                return null;
            }
        };

        new Thread(newAccess).start();
    }

    public void updateVendMachList() {
        try {
            HashMap<Integer, String> vendingMachines = access.getVendMachList();
            List<String> machines = new ArrayList<>();
            for (Map.Entry<Integer, String> entry : vendingMachines.entrySet()) {
                machines.add("id: " + entry.getKey() + " (" + entry.getValue() + ")");
            }

            menuBar.getItems().setAll(machines);

        } catch (Exception e) {
            textArea.setText(e.getMessage());
        }

    }

    public void updateInventory(int machineID) {
        Map<String, Integer> statusMap = access.getInventory(machineID);
        StringBuilder formattedStatus = new StringBuilder("Inventory:\n");
        for (Map.Entry<String, Integer> entry : statusMap.entrySet()) {
            formattedStatus.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        textArea.setText(formattedStatus.toString());
    }

    /**
     * Handles the button click event. It displays the inventory of the selected
     * vending machine in the text area.
     *
     * @param event The event representing the button click.
     */
    @FXML
    private void handleButtonClick(ActionEvent event) {
        if (findID() != null) {
            updateInventory(Integer.parseInt(findID()));
        } else {
            textArea.setText("No vending machine selected");
        }
    }

    private String findID() {
        String selectedItem = menuBar.getValue();
        if (selectedItem != null) {
            int colonIndex = selectedItem.indexOf(":");
            int endIndex = selectedItem.indexOf("(");
            return selectedItem.substring(colonIndex + 2, endIndex).trim();
        } else {
            System.out.println("No vending machine selected"); // Print a message for debugging
            return null;
        }
    }

    @FXML
    public void changeToRefillScene(ActionEvent event) throws IOException {
        int selectedMachineID = Integer.parseInt(findID());

        if (selectedMachineID != 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RefillApp.fxml"));
            Parent root = loader.load();
            RefillController refillController = loader.getController();
            refillController.setAccessService(service);
            refillController.setMainApp(mainApp);
            refillController.setSelectedMachineID(selectedMachineID);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            textArea.setText("No vending machine selected");
        }
    }

    @FXML
    public void userViewScene(ActionEvent event) throws IOException {
        int selectedMachineID = Integer.parseInt(findID());

        if (selectedMachineID != 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserApp.fxml"));
            Parent root = loader.load();
            UserController userController = loader.getController();
            userController.setAccessService(service);
            userController.setMainApp(mainApp);
            userController.setSelectedMachineID(selectedMachineID);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } else {
            textArea.setText("No vending machine selected");
        }

    }

}