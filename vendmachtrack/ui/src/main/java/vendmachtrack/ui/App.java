package vendmachtrack.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vendmachtrack.ui.access.AccessService;

import java.io.IOException;

/**
 * The App class extends javafx.application.Application and provides the entry
 * point for launching the application.
 * It initializes the main stage and manages scene transitions between Server
 * scene and Vending Machine Tracker scene.
 */
public class App extends Application {

    private final Stage primaryStage;
    private Scene mainScene;
    private VendAppController mainController;

    /**
     * Constructor for App class.
     * Initializes the primary stage for the application.
     */
    public App() {
        this.primaryStage = new Stage();
    }

    /**
     * This method is called by javafx to start the application.
     * It loads the Server.fxml file, sets up the main stage, and initializes the
     * server controller.
     *
     * @param stage The primary stage for the application.
     * @throws IOException If an error occurs while loading the Server.fxml file.
     */
    @Override
    public void start(final Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Server.fxml"));
        Parent parent = fxmlLoader.load();

        ServerController serverController = fxmlLoader.getController();
        serverController.setMainApp(this);

        mainScene = new Scene(parent);
        mainScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * Switches the scene to the Vending Machine Tracker scene.
     * Loads the App.fxml file, initializes the main controller, and updates the
     * vending machine list.
     *
     * @param service The AccessService instance providing access to vending machine
     *                data.
     * @throws IOException If an error occurs while loading the App.fxml file.
     */
    public void switchToVendAppScene(final AccessService service) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
        Parent parent = loader.load();

        mainController = loader.getController();
        mainController.setAccessService(service);
        mainController.setMainApp(this);
        mainController.updateVendMachList();

        mainScene = new Scene(parent);
        mainScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(mainScene);
    }

    /**
     * Switches the scene back to the main Server scene.
     * Updates vending machine list and inventory based on the selected vending
     * machine ID.
     *
     * @param machineID The ID of the selected vending machine.
     */
    public void switchToMainScene(final int machineID) {
        mainController.updateVendMachList();
        mainController.updateInventory(machineID);
        mainController.setIdToChoiceBox(machineID);
        primaryStage.setScene(mainScene);
    }

    /**
     * The main method, used to launch the javafx application.
     *
     * @param args Command-line arguments that are not used in this application.
     */
    public static void main(final String[] args) {
        launch();
    }

}
