package vendmachtrack.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Extends javafx.application.Application and provides a method for launching
 * the application.
 */
public class App extends Application {

    private final Stage primaryStage;
    private Scene mainScene;
    private VendAppController mainController;

    public App() {
        this.primaryStage = new Stage();
    }

    /**
     * This method is called by javafx to start the application.
     * It loads the App.fxml file, sets up the main stage, and initializes the
     * controller.
     *
     * @param stage The primary stage for the application.
     * @throws IOException If an error occurs while loading the App.fxml file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("App.fxml"));
        Parent parent = fxmlLoader.load();

        mainController = fxmlLoader.getController();
        mainController.setMainApp(this);

        mainScene = new Scene(parent);
        mainScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public void switchToMainScene(int machineID) {
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
    public static void main(String[] args) {
        launch();
    }

}
