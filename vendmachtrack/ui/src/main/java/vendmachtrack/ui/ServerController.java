package vendmachtrack.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import vendmachtrack.ui.access.AccessService;

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
        URI endpointUri = URI.create(serverUrlString);
        String fileName = trackerFileNameField.getText();

        try {
            AccessService service = new AccessService(endpointUri, fileName);
            mainApp.switchToVendAppScene(service);

        } catch (Exception e) {
            System.out.println("test");
            label.setText(e.getMessage());
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
