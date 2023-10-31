package vendmachtrack.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import vendmachtrack.ui.access.AccessService;

import java.net.URI;

public class ServerController {

    @FXML
    private TextField serverUrlField;

    @FXML
    private TextField trackerFileNameField;

    @FXML
    private Label label;

    private App mainApp;

    // This method is called when the user clicks the "Submit" button
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

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }
}
