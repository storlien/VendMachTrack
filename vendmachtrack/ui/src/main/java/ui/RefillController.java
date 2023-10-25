package ui;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.access.IMachineTrackerAccess;
import ui.access.MachineTrackerAccessRemote;

public class RefillController {

    @FXML
    private Label title;

    @FXML
    private TextField refillItem;

    @FXML
    private TextField refillNumber;

    @FXML
    private Button refillButton;

    @FXML
    private Button back;

    @FXML
    private TextArea textArea;

    @FXML
    private Text answerText;

    private IMachineTrackerAccess access;

    private int selectedMachineID;

    private Stage stage;
    private Scene scene;

    public void updateTitle(int machineID) {
        title.setText("Vending machine: " + machineID);
    }

    public void setSelectedMachineID(int machineID) { // denne metoden sørger for at vi overfører den id-en som man er
                                                      // på i controllerklassen hit
        this.selectedMachineID = machineID;
        updateTitle(machineID);
    }

    public void setMachineTrackerAccess(IMachineTrackerAccess access) {
        this.access = new MachineTrackerAccessRemote(URI.create("http://localhost:8080/"));

    }

    @FXML
    private void refillItem() {
        if (refillItem.getText() == null || !isLettersOnly(refillItem.getText())
                || !isDigitsOnly(refillNumber.getText())) {
            answerText.setText("Not a valid input");
        }

        HashMap<String, Integer> updatedInventory = new HashMap<>();

        try {
            updatedInventory = access.addItem(selectedMachineID, refillItem.getText(),
                    Integer.parseInt(refillNumber.getText()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            StringBuilder formattedStatus = new StringBuilder("Inventory:\n");
            for (Map.Entry<String, Integer> entry : updatedInventory.entrySet()) {
                formattedStatus.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            textArea.setText(formattedStatus.toString());

        } catch (Exception e) {
            textArea.setText(e.getMessage());
        }

        // her skal den oppdatere inventory for den maskinen og returnere det (den må
        // selvfølgelig også oppdatere for inventory som kommer opp på forsiden)

    }

    @FXML
    public void goBackToFirstPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static boolean isLettersOnly(String input) {
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(input).matches();

    }

    public static boolean isDigitsOnly(String input) {
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(input).matches();
    }

}
