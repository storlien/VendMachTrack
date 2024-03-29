package gr2338.vendmachtrack.integrationtests;

import gr2338.vendmachtrack.ui.App;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * This JUnit test class, `IntegrationUITestIT`, serves as an Integration test.
 * It tests the integration between the UI and the Spring Boot application.
 *
 * <p>
 * <p>
 * Before the Tests are started the Spring Boot application is started.
 * This ensures that the UI module uses RemoteAccess to communicate with the
 * Spring Boot Rest API.
 * And that the application works properly while the server is running.
 *
 * <p>
 * <p>
 * The test class simulates a user scenario by interacting with the UI and
 * verifying the expected outcomes.
 * this is done using TestFX, a testing framework for JavaFX applications.
 * <p>
 * <p>
 * Note: Test method order is controlled using `@Order` annotations to ensure
 * that the tests are run in
 * a specific sequence, as some tests depend on the outcome of previous tests.
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationUITestIT extends ApplicationTest {

    /**
     * Initializes and starts the JavaFX application.
     */
    @Override
    public void start(Stage stage) throws Exception {

        new App().start(stage);
    }

    /**
     * Centers the current JavaFX stage (window) on the screen.
     * This ensures that TestFx works properly
     */
    private void centerCurrentStage() {
        Stage currentStage = (Stage) lookup(".root").queryParent().getScene().getWindow();

        double screenWidth = javafx.stage.Screen.getPrimary().getBounds().getWidth();
        double screenHeight = javafx.stage.Screen.getPrimary().getBounds().getHeight();
        double windowWidth = currentStage.getWidth();
        double windowHeight = currentStage.getHeight();

        currentStage.setX((screenWidth - windowWidth) / 2);
        currentStage.setY((screenHeight - windowHeight) / 2);
    }

    /**
     * This JUnit test method represents an integration test for our vendmachtrack
     * APplication. It simulates
     * a user's interaction with different controllers and scenes in the
     * application. The test is divided into
     * several steps.
     *
     * <p>
     * <p>
     * Step 1: ServerController:
     * <ol>
     * <li>Redirect the standard output stream to capture console output.</li>
     * <li>Set the server URL and tracker file name.</li>
     * <li>Navigate to the VendAppController scene.</li>
     * <li>Assert that the captured console output indicates the use of remote
     * access due to the healthy server connection.</li>
     * <li>Restore the original standard output stream.</li>
     * <li>Assert that the "#Add" button is visible.</li>
     * </ol>
     *
     * <p>
     * <p>
     * <p>
     * Step 2: VendAppController
     *
     * <ol>
     * <li>Add a vending machine with ID 1001 and location "Beijing".</li>
     * <li>Verify that the vending machine was added.</li>
     * <li>Choose the newly added vending machine.</li>
     * <li>Press the "Refill" button to navigate to the RefillController scene.</li>
     * </ol>
     *
     * <p>
     * <p>
     * Step 3: RefillController
     * <ol>
     * <li>Add a "Cola" item with a quantity of 1 to the vending machine.</li>
     * <li>Assert that the item was successfully added to the vending machine's
     * inventory.</li>
     * <li>Return to the VendAppController scene.</li>
     * </ol>
     *
     * <p>
     * <p>
     * Step 4: UserAppController
     * <ol>
     * <li>Navigate to the UserAppController scene by pressing the "#UserView"
     * button.</li>
     * <li>Verify that the "Cola" item is visible in the user interface.</li>
     * <li>Buy a "Cola" item from the vending machine.</li>
     * <li>Assert that the item was bought and removed from the user interface.</li>
     * <li>Navigate to the PasswordHandlerController scene by pressing the "#Back"
     * button.</li>
     * </ol>
     *
     * <p>
     * <p>
     * <p>
     * Step 5: passwordHandlerController:
     * <ol>
     * <li>Enter a wrong password and attempt to confirm it.</li>
     * <li>Verify that an error message indicates an incorrect password.</li>
     * <li>Return to the UserAppController scene.</li>
     * <li>Assert that the bought item remains removed.</li>
     * </ol>
     *
     * <p>
     *
     * @throws InterruptedException if any thread-related issues occur during test
     *                              execution.
     */
    @Test
    @Order(1)
    public void integrationTestFlow() throws InterruptedException {

        // Redirect the standard output stream to capture console output.
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // STEP 1: ServerController: Write the server url and the tracker file name
        clickOn("#serverUrlField").write("http://localhost:8080");
        clickOn("#trackerFileNameField").write("tracker.json");

        // GO to VendAppController scene
        clickOn("Submit");

        // Restore the original standard output stream.
        System.setOut(originalOut);

        // Assert that the captured console output indicates the use of remote access
        // due to the healthy server connection.
        assertTrue(outContent.toString().contains("Using remote access"),
                "Expected remote access but got local access");

        verifyThat("#addButton", NodeMatchers.isVisible());

        centerCurrentStage(); // Center the stage on the screen

        // STEP 2: VendAppController: Add a Vending Machine with Id: 1001 and location:
        // Beijing
        clickOn("#idTextFieldAdd").write("1001");
        clickOn("#locationTextField").write("Beijing");
        clickOn("#addButton");
        clickOn("#menuBar");

        // Assert that the vending machine was added to the choice box
        @SuppressWarnings("unchecked") // Suppressing unchecked cast warning because we are certain that the queried
        // object is of type ChoiceBox<String>.
        ChoiceBox<String> choiceBox = (ChoiceBox<String>) lookup("#menuBar").queryAs(ChoiceBox.class);
        String expectedItem = "id: 1001 (Beijing)";
        assertTrue(choiceBox.getItems().contains(expectedItem));

        // Click on the item in the ChoiceBox by its value
        clickOn(expectedItem);

        // GO to RefillController scene
        clickOn("#refillButton");

        centerCurrentStage(); // Center the stage on the screen

        // STEP 3: RefillController: Add a Cola item with 1 quantity to the
        // vendingmachine
        clickOn("#refillItem").write("Cola");
        clickOn("#refillNumber").write("1");
        clickOn("Refill");

        // Assert that the item was added to the vending machine
        TextArea textArea = lookup("#textArea").queryAs(TextArea.class);
        FxAssert.verifyThat(textArea, TextInputControlMatchers.hasText("Inventory:\nCola: 1\n"));

        // GO back to VendAppController scene
        clickOn("Back");

        centerCurrentStage(); // Center the stage on the screen

        // STEP 4: Go to UserAppController scene
        clickOn("#userView");

        centerCurrentStage(); // Center the stage on the screen

        // Assert that the vending machine was added
        assertNotNull(lookup(".button").match(hasText("Cola")).tryQuery().orElse(null));

        // Buy the Cola item.
        Node colaButton = lookup("#buttonContainer").lookup(".button").match(hasText("Cola")).query();
        clickOn(colaButton);
        clickOn("#buy");

        // Assert that the item was bought and removed from the UserController scene
        assertNull(lookup(".button").match(hasText("Cola")).tryQuery().orElse(null));

        // Go to the PasswordHandler Scene
        clickOn("#backButton");

        centerCurrentStage(); // Center the stage on the screen

        // STEP 5: PasswordHandlerController: Write the Wrong password
        clickOn("#passwordField").write("WRONG");
        clickOn("#confirmButton");

        // Assert that the password was wrong
        verifyThat("#label", hasText("Incorrect password. Please try again"));

        // GO back to the Userview
        clickOn("#backToUserView");

        centerCurrentStage(); // Center the stage on the screen

        // Assert that the Item still is removed
        assertNull(lookup(".button").match(hasText("Cola")).tryQuery().orElse(null));
    }

    // The reason for the split in the unit tests is because i dont want to Write
    // the correct password in plain text for security reasons.
    // Workaround for now, but will be fixed in the next release.

    /**
     * This Junit test will tests that the that you can remove a vending machine
     * from the application.
     *
     * <p>
     * <p>
     * The test is divided into several steps.
     *
     * <p>
     * <ol>
     * <li>Set the server URL and tracker file name.</li>
     * <li>Navigate to the VendAppController scene.</li>
     * <li>Center the stage on the screen.</li>
     * <li>Remove the vending machine with ID 1001 from the application.</li>
     * <li>Assert that the vending machine was removed from the choice box.</li>
     * </ol>
     */
    @Test
    @Order(2)
    public void testIntegrationFlow2() {

        // STEP 1: ServerController: Write the server url and the tracker file name
        clickOn("#serverUrlField").write("http://localhost:8080");
        clickOn("#trackerFileNameField").write("tracker.json");
        clickOn("Submit");

        // Center the stage on the screen
        centerCurrentStage();

        // Remove the Added Vending Machine
        clickOn("#idTextFieldRemove").write("1001");
        clickOn("#removeButton");

        // Assert that the vending machine was removed from the choice box
        @SuppressWarnings("unchecked") // Suppressing unchecked cast warning because we are certain that the queried
        // object is of type ChoiceBox<String>.
        ChoiceBox<String> choiceBox = (ChoiceBox<String>) lookup("#menuBar").queryAs(ChoiceBox.class);
        assertFalse(choiceBox.getItems().contains("id: 1001 (Beijing)"));
    }
}
