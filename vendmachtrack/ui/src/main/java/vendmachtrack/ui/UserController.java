package vendmachtrack.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vendmachtrack.ui.access.AccessService;
import vendmachtrack.ui.access.MachineTrackerAccessible;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The UserController class manages the user interface for vending machine
 * operations.
 * It allows users to view inventory, select items for purchase, and remove
 * items from the inventory.
 */
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

    @FXML
    private ScrollPane scrollPane;

    private App mainApp;
    private int selectedMachineID;
    private AccessService service;
    private MachineTrackerAccessible access;
    private Stage stage;
    private Scene scene;

    private static final int BUTTONS_PER_ROW = 4;
    private static final int BUTTON_SPACING = 30;
    private static final double BUTTON_WIDTH = 100;
    private static final double BUTTON_HEIGHT = 120;
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
            mylabel.setText(e.getMessage());
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
     * Updates the buttons in the user interface based on the vending machine
     * inventory.
     *
     * @param machineID The ID of the vending machine to update.
     * @param inventory A map representing the items and quantities in the vending
     *                  machine inventory.
     */
    private void updateButtons(final int machineID, final Map<String, Integer> inventory) {
        buttonContainer.getChildren().clear();

        HBox currentRow = new HBox(BUTTON_SPACING);
        buttonContainer.setSpacing(BUTTON_SPACING);

        int buttonsInCurrentRow = 0;

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            String itemName = entry.getKey();

            Button button = new Button(itemName);
            button.getStyleClass().add("buttonContainer");
            button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

            button.setOnAction(e -> chosenItem.setText(itemName));

            currentRow.getChildren().add(button);
            buttonsInCurrentRow++;

            if (buttonsInCurrentRow >= BUTTONS_PER_ROW) {
                buttonContainer.getChildren().add(currentRow);
                currentRow = new HBox(BUTTON_SPACING);
                buttonsInCurrentRow = 0;
            }
        }

        if (buttonsInCurrentRow > 0) {
            buttonContainer.getChildren().add(currentRow);
        }
        scrollPane.setContent(buttonContainer);
    }

    /**
     * Removes the selected item from the vending machine inventory.
     */
    @FXML
    private void removeItem() {
        try {
            HashMap<String, Integer> newInventory = access.removeItem(selectedMachineID, chosenItem.getText(), 1);
            updateButtons(selectedMachineID, newInventory);
            chosenItem.clear();
        } catch (Exception e) {
            chosenItem.setText(e.getMessage());
        }
    }

    /**
     * Updates the title label based on the selected vending machine.
     *
     * @param machineID The ID of the selected vending machine.
     */
    private void updateTitle(final int machineID) {
        try {
            mylabel.setText("Vending machine: " + access.getVendMachLocation(machineID));
        } catch (Exception e) {
            chosenItem.setText(e.getMessage());
        }
    }

    /**
     * Sets the selected vending machine ID and updates the user interface.
     *
     * @param machineID The ID of the selected vending machine.
     */
    public void setSelectedMachineID(final int machineID) {
        try {
            this.selectedMachineID = machineID;
            updateTitle(selectedMachineID);
            updateButtons(selectedMachineID, access.getInventory(selectedMachineID));
        } catch (Exception e) {
            chosenItem.setText(e.getMessage());
        }
    }

    /**
     * Switches the scene to the password authentication screen.
     *
     * @param event The ActionEvent triggered by the user.
     * @throws IOException If an error occurs while loading the PasswordApp.fxml
     *                     file.
     */
    @FXML
    public void switchToPasswordScene(final ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PasswordApp.fxml"));
            Parent root = fxmlLoader.load();
            PasswordHandlerController passwordController = fxmlLoader.getController();
            passwordController.setMainApp(mainApp);
            passwordController.setAccessService(service);
            passwordController.setSelectedMachineID(selectedMachineID);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            mylabel.setText("Could not load new scene");
        }
    }

}