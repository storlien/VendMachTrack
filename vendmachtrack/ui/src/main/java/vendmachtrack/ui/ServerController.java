package vendmachtrack.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import vendmachtrack.ui.access.AccessService;

import java.net.MalformedURLException;
import java.net.URI;

/**
 * The ServerController class manages the user interface for entering server
 * information.
 * It allows users to input the server URL and the file name for the vending
 * machine tracker data.
 */
public class ServerController {

    @FXML
    private TextField serverUrlField;

    @FXML
    private TextField trackerFileNameField;

    @FXML
    private Label label;

    private App mainApp;

    /**
     * Handles the submission of server information provided by the user.
     * Creates an AccessService instance with the provided server URL and file name,
     * switches to the vending machine tracker scene, and displays an error message
     * if any exception occurs.
     */
    @FXML
    private void handleSubmission() {
        String serverUrlString = serverUrlField.getText();

        try {
            String fileName = trackerFileNameField.getText();
            AccessService service = new AccessService(new URI(serverUrlString), fileName);
            mainApp.switchToVendAppScene(service);
        } catch (MalformedURLException e) {
            label.setText("Invalid URL: " + e.getMessage());
        } catch (Exception e) {
            label.setText("Invalid input in server URL: " + serverUrlString);
        }
    }

    /**
     * Sets the main application instance.
     *
     * @param mainApp The main application instance.
     */
    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }
}
