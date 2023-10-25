package ui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.TextArea;
import jsonio.VendmachtrackPersistence;
import ui.access.MachineTrackerAccessible;
import ui.access.MachineTrackerAccessLocal;
import ui.access.MachineTrackerAccessRemote;

import java.net.URI;

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
    private ChoiceBox<String> menuBar;


    private MachineTrackerAccessible access;


    /**
     * Initializes the controller. It reads vending machine data from a JSON file.
     *
     * @param arg0 The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param arg1 The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        URI endpointUri = URI.create("http://localhost:8080/");

        Task<Boolean> checkServerHealthTask = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return checkServerHealth(endpointUri);
            }
        };

        checkServerHealthTask.setOnSucceeded(event -> {
            if (checkServerHealthTask.getValue()) {
                access = new MachineTrackerAccessRemote(endpointUri);
                System.out.println("Using remote access");
            } else {
                access = new MachineTrackerAccessLocal(new VendmachtrackPersistence("tracker.json"));
                System.out.println("Using local access");
            }

            postInitialize();
        });

        checkServerHealthTask.setOnFailed(event -> {
            System.out.println("Error during server health check: " + checkServerHealthTask.getException());
            access = new MachineTrackerAccessLocal(new VendmachtrackPersistence("tracker.json"));
            System.out.println("Using local access as a fallback");
            postInitialize();
        });

        new Thread(checkServerHealthTask).start();
    }

    private void postInitialize() {
        try {
            HashMap<Integer, String> vendingMachines = access.getVendMachList();
            List<String> machines = new ArrayList<>();
            for (Map.Entry<Integer, String> entry : vendingMachines.entrySet()) {
                machines.add("id: " + entry.getKey() + " (" + entry.getValue() + ")");
            }
            menuBar.getItems().addAll(machines);

        } catch (Exception e) {
            textArea.setText(e.getMessage());
        }
    }

    /**
     * Handles the button click event. It displays the inventory of the selected vending machine in the text area.
     *
     * @param event The event representing the button click.
     */
    @FXML
    private void handleButtonClick(ActionEvent event) {
        if (findID() != 0) {
            Map<String, Integer> statusMap = access.getInventory(findID());
            StringBuilder formattedStatus = new StringBuilder("Inventory:\n");
            for (Map.Entry<String, Integer> entry : statusMap.entrySet()) {
                formattedStatus.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            textArea.setText(formattedStatus.toString());

        } else {
            textArea.setText("No vending machine selected");
        }

    }

    private int findID() {
        String selectedItem = menuBar.getValue();
        if (selectedItem != null) {
            int colonIndex = selectedItem.indexOf(":");
            int endIndex = selectedItem.indexOf("(");
            int id = Integer.parseInt(selectedItem.substring(colonIndex + 2, endIndex).trim());
            return id;
        } else {
            return 0;
        }
    }

    private boolean checkServerHealth(URI endpointUri) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointUri.resolve("health"))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpURLConnection.HTTP_OK;
    }
}