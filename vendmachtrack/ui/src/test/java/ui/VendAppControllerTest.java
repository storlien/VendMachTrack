package ui;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import core.MachineTracker;
import core.VendingMachine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jsonio.IFromJson;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import static org.testfx.api.FxAssert.verifyThat;
import javafx.fxml.FXMLLoader;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;




public class VendAppControllerTest extends ApplicationTest {


 private VendAppController controller;

    @Override
    public void start(Stage stage) throws Exception {
        // This uses the structure from your App class to start the application.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("App.fxml")); // Make sure to use the correct path to your FXML file.
        Parent parent = fxmlLoader.load();

        controller = fxmlLoader.getController();
        stage.setOnCloseRequest((WindowEvent event) -> {
            controller.onClose();
        });

        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    public void testVendingMachineSelection() {
        // Assuming the ChoiceBox's items are populated at this point,
        // we'll select the first item in the ChoiceBox. Adjust as necessary if you have a specific item to test.
        ChoiceBox choiceBox = lookup("#menuBar").queryAs(ChoiceBox.class);
        interact(() -> choiceBox.getSelectionModel().select(0));

        // Click the "OK" button.
        clickOn("#button");

        // Now, we'll verify that the TextArea contains the expected text.
        // Adjust the expected text based on the VendingMachine's status that you're testing.
        TextArea textArea = lookup("#textArea").queryAs(TextArea.class);
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("Inventory:\nCola: 5\nPepsi: 3\n"));

    
    }

    // private VendAppController controller;
    // private Parent root;
    // private MachineTracker tracker;
    
   
    // @Override
    // public void start(Stage stage) throws IOException {
    //     FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../App.fxml"));
    //     root = fxmlLoader.load();
    //     VendAppController controller = fxmlLoader.getController();
    //     stage.setScene(new Scene(root));
    //     stage.show();
    // }

    // public Parent getRootNode() {
    //     return root;
    // }

    // @BeforeEach
    // public void setUpItems(){
    //     VendingMachine machine = new VendingMachine(); 
    //     machine.addItem("ØL", 5);
    //     machine.setId(1);
    //     machine.setLocation("Trondheim");
    //     tracker = new MachineTracker();
    //     tracker.addVendingMachine(machine);
    // }

    // @Test
    // public void test
}