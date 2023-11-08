package gr2338.vendmachtrack.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import gr2338.vendmachtrack.ui.App;
import gr2338.vendmachtrack.ui.access.AccessService;
import gr2338.vendmachtrack.ui.access.MachineTrackerAccessible;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controller class for the vending machine application's user interface.
 * Manages the interaction between the UI components and the backend services.
 */
public class VendAppController implements Initializable {

    @FXML
    private Label myLabel;

    @FXML
    private TextArea textArea;

    @FXML
    private Button okButton;

    @FXML
    private Button refillButton;

    @FXML
    private Button userView;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private TextField idTextFieldAdd;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField idTextFieldRemove;

    @FXML
    private Label outputText;

    @FXML
    private ChoiceBox<String> menuBar;

    private App mainApp;

    private MachineTrackerAccessible access;

    private AccessService service;

    /**
     * Sets the AccessService instance to interact with vending machine data.
     *
     * @param service The AccessService instance.
     */
    public void setAccessService(final AccessService service) {
        try {
            this.service = service;
            this.access = service.getAccess();
        } catch (Exception e) {
            outputText.setText(e.getMessage());
        }
    }

    /**
     * Sets the main application instance.
     *
     * @param mainApp The main application instance.
     */
    public void setMainApp(final App mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Initializes the controller.
     *
     * @param arg0 The location used to resolve relative paths for the root object,
     *             or null if the location is not known.
     * @param arg1 The resources used to localize the root object, or null if the
     *             root object was not localized.
     */
    @Override
    public void initialize(final URL arg0, final ResourceBundle arg1) {

    }

    /**
     * Updates the list of vending machines in the menu bar.
     */
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

    /**
     * Updates the displayed inventory based on the selected vending machine.
     *
     * @param machineID The ID of the selected vending machine.
     */
    public void updateInventory(final int machineID) {
        try {
            Map<String, Integer> statusMap = access.getInventory(machineID);
            StringBuilder formattedStatus = new StringBuilder("Inventory:\n");
            for (Map.Entry<String, Integer> entry : statusMap.entrySet()) {
                formattedStatus.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            textArea.setText(formattedStatus.toString());
        } catch (Exception e) {
            textArea.setText(e.getMessage());

        }
    }

    /**
     * Handles the add button click event. Adds a new vending machine to the system.
     *
     * @param event The event representing the button click.
     */
    @FXML
    private void handleAddButton(final ActionEvent event) {
        try {
            int machineId = Integer.parseInt(idTextFieldAdd.getText());
            String location = locationTextField.getText();
            access.addVendMach(machineId, location);
            updateVendMachList();
            textArea.clear();
            menuBar.setValue(null);
            idTextFieldAdd.clear();
            locationTextField.clear();
            outputText.getStyleClass().removeAll("success-text", "error-text");
            outputText.getStyleClass().add("success-text");
            outputText.setText("The machine " + machineId + " was successfully added to your tracker ");

        } catch (NumberFormatException e) {
            textArea.clear();
            menuBar.setValue(null);
            idTextFieldAdd.clear();
            locationTextField.clear();
            outputText.getStyleClass().removeAll("success-text", "error-text");
            outputText.getStyleClass().add("error-text");
            outputText.setText("Machine ID must be an integer");
        } catch (Exception e) {
            textArea.clear();
            menuBar.setValue(null);
            idTextFieldAdd.clear();
            locationTextField.clear();
            outputText.getStyleClass().removeAll("success-text", "error-text");
            outputText.getStyleClass().add("error-text");
            outputText.setText(e.getMessage());
        }
    }

    /**
     * Handles the remove button click event. Removes a vending machine from the
     * system.
     *
     * @param event The event representing the button click.
     */
    @FXML
    private void handleRemoveButton(final ActionEvent event) {
        try {
            int machineId = Integer.parseInt(idTextFieldRemove.getText());
            access.removeVendMach(machineId);
            updateVendMachList();
            textArea.clear();
            menuBar.setValue(null);
            idTextFieldRemove.clear();
            outputText.getStyleClass().removeAll("success-text", "error-text");
            outputText.getStyleClass().add("success-text");
            outputText.setText("The machine " + machineId + " was successfully removed from your tracker");

        } catch (NumberFormatException e) {
            textArea.clear();
            menuBar.setValue(null);
            idTextFieldRemove.clear();
            outputText.getStyleClass().removeAll("success-text", "error-text");
            outputText.getStyleClass().add("error-text");
            outputText.setText("Machine ID must be an integer");
        } catch (Exception e) {
            textArea.clear();
            menuBar.setValue(null);
            idTextFieldRemove.clear();
            outputText.getStyleClass().removeAll("success-text", "error-text");
            outputText.getStyleClass().add("error-text");
            outputText.setText(e.getMessage());

        }

    }

    /**
     * Handles the button click event. It displays the inventory of the selected
     * vending machine in the text area.
     *
     * @param event The event representing the button click.
     */
    @FXML
    private void handleButtonClick(final ActionEvent event) {
        if (findID() != null) {
            updateInventory(Integer.parseInt(findID()));
            outputText.setText(null);
        } else {
            textArea.setText("No vending machine selected");
        }
    }

    /**
     * Sets the selected vending machine ID to the ChoiceBox menuBar.
     *
     * @param machineID The ID of the vending machine to be set in the menuBar.
     */
    public void setIdToChoiceBox(final int machineID) {
        try {
            HashMap<Integer, String> vendingMachines = access.getVendMachList();
            for (Map.Entry<Integer, String> entry : vendingMachines.entrySet()) {
                if (entry.getKey() == machineID) {
                    menuBar.setValue("id: " + entry.getKey() + " (" + entry.getValue() + ")");

                }
            }
        } catch (Exception e) {
            textArea.setText(e.getMessage());
        }

    }

    /**
     * Retrieves the ID of the selected vending machine from the menuBar ChoiceBox.
     *
     * @return The ID of the selected vending machine, or null if no machine is
     * selected.
     */
    private String findID() {
        String selectedItem = menuBar.getValue();
        if (selectedItem != null) {
            int colonIndex = selectedItem.indexOf(":");
            int endIndex = selectedItem.indexOf("(");
            return selectedItem.substring(colonIndex + 2, endIndex).trim();
        } else {
            return null;
        }
    }

    /**
     * Switches to the refill scene based on the selected vending machine.
     *
     * @param event The event representing the button click.
     * @throws IOException If an I/O error occurs while loading the refill scene.
     */
    @FXML
    public void changeToRefillScene(final ActionEvent event) throws IOException {
        try {
            int selectedMachineID = Integer.parseInt(findID());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("RefillApp.fxml"));
            Parent root = loader.load();
            RefillController refillController = loader.getController();
            refillController.setAccessService(service);
            refillController.setMainApp(mainApp);
            refillController.setSelectedMachineID(selectedMachineID);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

            textArea.setText("No vending machine selected");

        } catch (NumberFormatException e) {
            textArea.setText("Please select a vending machine.");
        } catch (Exception e) {
            textArea.setText("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Switches to the user view scene based on the selected vending machine.
     *
     * @param event The event representing the button click.
     * @throws IOException If an I/O error occurs while loading the user view scene.
     */
    @FXML
    public void userViewScene(final ActionEvent event) throws IOException {
        try {
            int selectedMachineID = Integer.parseInt(findID());

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

        } catch (NumberFormatException e) {
            textArea.setText("Please select a vending machine.");
        } catch (Exception e) {
            textArea.setText("An error occurred: " + e.getMessage());
        }

    }

}