package vendmachtrack.ui;

import java.io.IOException;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vendmachtrack.ui.access.AccessService;
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

    @FXML
    private ScrollPane scrollPane;

    private App mainApp;
    private int selectedMachineID;
    private AccessService service;
    private MachineTrackerAccessible access;
    private Stage stage;
    private Scene scene;

    public void setAccessService(AccessService service) {
        try {
            this.service = service;
            this.access = service.getAccess();
        } catch (Exception e) {
            mylabel.setText(e.getMessage());
        }
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    public void updateButtons(int machineID, Map<String, Integer> inventory) {
        try {
            buttonContainer.getChildren().clear();

            HBox currentRow = new HBox();
            currentRow.setSpacing(30);
            buttonContainer.setSpacing(30);

            int buttonsInCurrentRow = 0;

            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                String itemName = entry.getKey();
                int itemQuantity = entry.getValue();

                Button button = new Button(itemName);
                button.getStyleClass().add("buttonContainer");
                button.setPrefSize(100, 120);

                button.setOnAction(e -> chosenItem.setText(itemName));

                currentRow.getChildren().add(button);
                buttonsInCurrentRow++;

                if (buttonsInCurrentRow >= 4) {
                    buttonContainer.getChildren().add(currentRow);
                    currentRow = new HBox();
                    currentRow.setSpacing(30);
                    buttonsInCurrentRow = 0;
                }
                scrollPane.setContent(buttonContainer);

            }

            if (buttonsInCurrentRow > 0) {
                buttonContainer.getChildren().add(currentRow);
            }
        } catch (Exception e) {
            mylabel.setText(e.getMessage());
        }

    }

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

    public void updateTitle(int machineID) {
        try {
            mylabel.setText("Vending machine: " + access.getVendMachLocation(machineID));
        } catch (Exception e) {
            chosenItem.setText(e.getMessage());
        }
    }

    public void setSelectedMachineID(int machineID) {
        try {
            this.selectedMachineID = machineID;
            updateTitle(selectedMachineID);
            updateButtons(selectedMachineID, access.getInventory(selectedMachineID));
        } catch (Exception e) {
            chosenItem.setText(e.getMessage());
        }
    }

    @FXML
    public void switchToPasswordScene(ActionEvent event) throws IOException {
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