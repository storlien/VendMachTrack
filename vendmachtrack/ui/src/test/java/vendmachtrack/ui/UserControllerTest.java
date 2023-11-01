package vendmachtrack.ui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import vendmachtrack.ui.access.AccessService;
import vendmachtrack.ui.access.MachineTrackerAccessible;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.HashMap;

public class UserControllerTest extends ApplicationTest{

    private UserController userController;
    private HashMap<Integer, String> vendingMachines = new HashMap<>();

    @Mock
    private AccessService mockService;

    @Mock
    private MachineTrackerAccessible mockAccess;


    @Override
    public void start(Stage stage) throws Exception {
        MockitoAnnotations.openMocks(this);

        // Mock the behavior of the AccessService and MachineTrackerAccessible
        when(mockService.getAccess()).thenReturn(mockAccess);
        
        // Mock a vending machine list 
        vendingMachines.put(1, "Oslo");
        when(mockAccess.getVendMachList()).thenReturn(vendingMachines);
        when(mockAccess.getVendMachLocation(1)).thenReturn("Oslo");
         HashMap<String, Integer> testInventory = new HashMap<>();
        testInventory.put("Cola", 1);
        testInventory.put("Øl", 3);
        testInventory.put("Pepsi", 7);
        testInventory.put("Fanta", 2);
        testInventory.put("Sprite", 5);

        when(mockAccess.getInventory(anyInt())).thenReturn(testInventory);

        // Load the FXML and set the mocked service to the controller
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserApp.fxml"));
        Parent parent = fxmlLoader.load();
        this.userController = fxmlLoader.getController();
        this.userController.setAccessService(mockService);

      
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

      @Test
    public void UserController_testuserController() {
        assertNotNull(this.userController);
    }


    @Test
    public void UserController_testUpdateButtons_twoRows() throws InterruptedException {

    // Arrange
    Platform.runLater(() -> {
        this.userController.setSelectedMachineID(1);
    });

    
    Thread.sleep(100);

    // Act
    VBox buttonContainer = lookup("#buttonContainer").query();
    HBox buttonsRow = (HBox) buttonContainer.getChildren().get(0);
    HBox buttonsRow2 = (HBox) buttonContainer.getChildren().get(1);

    // Assert
    assertEquals(4, buttonsRow.getChildren().size());  
    assertEquals(1, buttonsRow2.getChildren().size()); 


    }

    @Test
    public void UserCOntroller_testBuy_OneItemRemaining() throws InterruptedException {
        
        //Arrange  
        HashMap<String, Integer> testInventoryUpdated = new HashMap<>();
        testInventoryUpdated.put("Øl", 3);
        testInventoryUpdated.put("Pepsi", 7);
        testInventoryUpdated.put("Fanta", 2);
        testInventoryUpdated.put("Sprite", 5);
        when(mockAccess.removeItem(anyInt(), anyString(), anyInt())).thenReturn(testInventoryUpdated);
        Platform.runLater(() -> {
        this.userController.setSelectedMachineID(1);
        });
        Thread.sleep(10);
        
        //Act
        clickOn("Cola");
        clickOn("#buy");
        VBox buttonContainer = lookup("#buttonContainer").query();
        HBox buttonsRow = (HBox) buttonContainer.getChildren().get(0);


        Thread.sleep(100);
        // Assert
        assertNull(lookup(".button").match(hasText("Cola")).tryQuery().orElse(null));
        assertEquals(4, buttonsRow.getChildren().size());  
    }

    @Test
    public void UserController_testBuy_NoSelection() throws InterruptedException {
        
        //Arrange  
        when(mockAccess.removeItem(anyInt(), anyString(), anyInt())).thenThrow(new RuntimeException("Item name not valid"));
        Platform.runLater(() -> {
        this.userController.setSelectedMachineID(1);
        });
        Thread.sleep(100);

        // Click on the buy button directly without selecting an item
        clickOn("#buy");

        // Check the message in the TextField
        Thread.sleep(100);
        TextField chosenItemTextField = lookup("#chosenItem").queryAs(TextField.class);
        assertEquals("Item name not valid", chosenItemTextField.getText());
    }

    @Test
    public void UserController_BackbuttonTest_newSceneOpen() throws InterruptedException {
        
        //Arrange
        Platform.runLater(() -> {
        this.userController.setSelectedMachineID(1);
        });

        //Act
        clickOn("#backButton");

        Thread.sleep(100);

        //Assert
        assertNotNull(lookup("#passwordField").queryAs(TextField.class));
    }
}
