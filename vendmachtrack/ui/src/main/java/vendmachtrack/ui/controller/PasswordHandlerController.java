package vendmachtrack.ui.controller;

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
import vendmachtrack.ui.App;
import vendmachtrack.ui.access.AccessService;

import java.io.IOException;

/**
 * The PasswordHandlerController class handles user authentication through
 * password validation.
 * It provides methods to validate the user-entered password and switch between
 * scenes based on the validation result.
 */
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

    /**
     * Handles the event when the user clicks the confirm button.
     * Validates the entered password and navigates to the appropriate scene.
     *
     * @param event The ActionEvent triggered by the user.
     * @throws IOException If an error occurs during scene transition.
     */
    public void onConfirmButtonClicked(final ActionEvent event) throws IOException {
        String inputPassword = passwordField.getText();

        if (PasswordHandler.verifyPassword(inputPassword)) {
            backToHomePage(event);
        } else {
            label.getStyleClass().add("error-text");
            label.setText("Incorrect password. Please try again");
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
     * Sets the AccessService instance to access vending machine data.
     *
     * @param service The AccessService instance.
     */
    public void setAccessService(final AccessService service) {
        this.service = service;
    }

    /**
     * Navigates back to the main vending machine tracker scene.
     *
     * @param event The ActionEvent triggered by the user.
     * @throws IOException If an error occurs during scene transition.
     */
    @FXML
    public void backToHomePage(final ActionEvent event) throws IOException {
        mainApp.switchToMainScene(selectedMachineID);
    }

    /**
     * Sets the ID of the selected vending machine.
     *
     * @param machineID The ID of the selected vending machine.
     */
    public void setSelectedMachineID(final int machineID) {
        this.selectedMachineID = machineID;
    }

    /**
     * Switches back to the user view scene.
     *
     * @param event The ActionEvent triggered by the user.
     * @throws IOException If an error occurs during scene transition.
     */
    public void switchBackToUserView(final ActionEvent event) throws IOException {
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
