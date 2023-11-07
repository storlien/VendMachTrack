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

/**
 * This class contains JUnit tests for the {@link UserController} class, focusing on the user interface and interactions.
 * 
 * <p>
 * The tests in this class verify the behavior of the user interface components and user interactions in the {@link UserController}.
 * These tests use the JavaFX testing framework to simulate user actions and interactions with the application.
 * </p>
 * 
 * @see UserController
 * @see ApplicationTest
 */
public class UserControllerTest extends ApplicationTest{

    private UserController userController;
    private HashMap<Integer, String> vendingMachines = new HashMap<>();

    @Mock
    private AccessService mockService;

    @Mock
    private MachineTrackerAccessible mockAccess;


    /**
     * Initializes the JavaFX application, sets up mock behavior for services and controllers, and opens the main application window.
     * 
     * <p>
     * This method is responsible for setting up the JavaFX application, including mocking the behavior of services and controllers,
     * and opening the main application window for testing purposes.
     * </p>
     * 
     * <ol>
     *   <li>Arrange: Mock the behavior of the {@link AccessService} and related services using Mockito.</li>
     *   <li>Arrange: Mock a list of vending machines and their locations for testing.</li>
     *   <li>Arrange: Mock the inventory for a vending machine using predefined values.</li>
     *   <li>Arrange: Load the FXML file for the user application and set the mocked service to the controller.</li>
     *   <li>Arrange: Create and display the main application window with a predefined scene.</li>
     * </ol>
     * 
     * @param stage The primary stage for the JavaFX application.
     * @throws Exception if any error occurs during application initialization.
     */
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

    /**
     * Tests the initialization of the {@link UserController} to ensure that it is not null.
     * 
     * <p>
     * This test case focuses on verifying that the {@link UserController} object is properly instantiated
     * and not null.
     * </p>
     * 
     * <ol>
     *   <li>Assert: Verify that the {@code this.userController} object is not null.</li>
     * </ol>
     */
    @Test
    public void UserController_testuserController() {
        //Assert
        assertNotNull(this.userController);
    }


    /**
     * Tests the behavior of the {@link UserController} when updating buttons with two rows of buttons.
     * 
     * <p>
     * This test case focuses on verifying that the button layout is correctly updated with two rows of buttons.
     * </p>
     * 
     * <ol>
     *   <li>Arrange: Set the selected machine ID to 1 on the JavaFX application thread using {@link Platform#runLater(Runnable)}.</li>
     *   <li>Act: Access the button container and individual button rows.</li>
     *   <li>Assert: Verify that the first row of buttons contains 4 buttons and the second row contains 1 button.</li>
     * </ol>
     * 
     * @throws InterruptedException if the thread sleep is interrupted
     */
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

     /**
     * Tests the behavior of the {@link UserController} when buying an item with only one item remaining in the inventory.
     * 
     * <p>
     * This test case focuses on verifying that the user interface updates correctly when buying an item with only one
     * item remaining in the inventory.
     * </p>
     * 
     * <ol>
     *   <li>Arrange: Set up a mock for removing an item from inventory with one item remaining.
     *       Set the selected machine ID to 1 on the JavaFX application thread using {@link Platform#runLater(Runnable)}.</li>
     *   <li>Act: Click on the "Cola" button, click on the "buy" button, and access the button container and button row.</li>
     *   <li>Assert: Verify that the "Cola" button is removed, and the button row contains the expected number of buttons.</li>
     * </ol>
     * 
     * @throws InterruptedException if the thread sleep is interrupted
     */
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
        Thread.sleep(100);
        
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

    /**
     * Tests the behavior of the {@link UserController} when attempting to buy an item without making a selection.
     * 
     * <p>
     * This test case focuses on verifying that the user interface handles the scenario where the user attempts to
     * buy an item without making a selection.
     * </p>
     * 
     * <ol>
     *   <li>Arrange: Set up a mock to throw a {@link RuntimeException} with a specific message when removing an item.
     *       Set the selected machine ID to 1 on the JavaFX application thread using {@link Platform#runLater(Runnable)}.</li>
     *   <li>Act: Click on the "buy" button.</li>
     *   <li>Assert: Verify that the text field displaying the chosen item contains the expected error message.</li>
     * </ol>
     * 
     * @throws InterruptedException if the thread sleep is interrupted
     */
    @Test
    public void UserController_testBuy_NoSelection() throws InterruptedException {
        
        //Arrange  
        when(mockAccess.removeItem(anyInt(), anyString(), anyInt())).thenThrow(new RuntimeException("Item name not valid"));
        Platform.runLater(() -> {
        this.userController.setSelectedMachineID(1);
        });
        Thread.sleep(100);

        // Act
        clickOn("#buy");

        // Assert
        Thread.sleep(100);
        TextField chosenItemTextField = lookup("#chosenItem").queryAs(TextField.class);
        assertEquals("Item name not valid", chosenItemTextField.getText());
    }

    /**
     * Tests the behavior of the {@link UserController} when clicking the back button to open a new scene.
     * 
     * <p>
     * This test case focuses on verifying that clicking the back button results in opening a new scene.
     * </p>
     * 
     * <ol>
     *   <li>Arrange: Set the selected machine ID to 1 on the JavaFX application thread using {@link Platform#runLater(Runnable)}.</li>
     *   <li>Act: Click on the back button.</li>
     *   <li>Assert: Verify that a new scene is opened, indicated by the presence of a password field in the new scene.</li>
     * </ol>
     * 
     * @throws InterruptedException if the thread sleep is interrupted
     */
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
