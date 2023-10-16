package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class RefillApp extends Application{


/**
 * Extends javafx.application.Application and provides a method for launching the application. 
 */

    /**
     * This method is called by javafx to start the application.
     * It loads the App.fxml file, sets up the main stage, and initializes the controller.
     * 
     * @param stage The primary stage for the application.
     * @throws IOException If an error occurs while loading the App.fxml file.
     */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("RefillApp.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
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

    
