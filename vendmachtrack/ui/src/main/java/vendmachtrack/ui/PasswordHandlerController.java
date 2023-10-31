package vendmachtrack.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import vendmachtrack.core.PasswordHandler;
import vendmachtrack.ui.access.AccessService;

import java.io.IOException;

public class PasswordHandlerController {

    @FXML
    private Button confirmButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label label;

    @FXML
    private Button backToUserView;

    private Stage stage;
    private Scene scene;
    private App mainApp;
    private int selectedMachineID;
    private AccessService service;

    public void onConfirmButtonClicked(ActionEvent event) throws IOException {
        String inputPassword = passwordField.getText();

        if (PasswordHandler.verifyPassword(inputPassword)) {
            backToHomePage(event);

        } else {
            label.getStyleClass().add("error-text");
            label.setText("Incorrect password. Please try again");
        }
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    public void setAccessService(AccessService service) {
        try {
            this.service = service;
        } catch (Exception e) {
            label.setText(e.getMessage());
        }
    }

    @FXML
    public void backToHomePage(ActionEvent event) throws IOException {
        mainApp.switchToMainScene(selectedMachineID);
    }

    public void setSelectedMachineID(int machineID) {
        this.selectedMachineID = machineID;
    }

    public void switchBackToUserView(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserApp.fxml"));
            Parent root = fxmlLoader.load();
            UserController userController = fxmlLoader.getController();
            userController.setAccessService(service);
            userController.setMainApp(mainApp);
            userController.setSelectedMachineID(selectedMachineID);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            label.setText("Could not load new scene");
        }
    }
}
