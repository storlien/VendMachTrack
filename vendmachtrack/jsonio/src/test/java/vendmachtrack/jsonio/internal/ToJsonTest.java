package vendmachtrack.jsonio.internal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vendmachtrack.core.model.MachineTracker;
import vendmachtrack.core.model.VendingMachine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class contains JUnit tests for the ToJson class. It tests the serialization of MachineTracker objects to JSON format.
 *
 * @see ToJson
 */
public class ToJsonTest {

    private final String filename = "/testfile.json";
    private ToJson toJson;
    private Path dir;


    /**
     * This method is annotated with {@code @BeforeEach} and runs before each individual test method.
     * It is responsible for setting up the test environment and resources needed by the tests.
     * <p>
     * In this case, it initializes the 'dir' variable with the path to a file in the user's home directory
     * and creates a 'toJson' instance for testing.
     *
     * @throws Exception if any error occurs during the setup
     */
    @BeforeEach
    public void setUp() throws Exception {
        dir = Paths.get(System.getProperty("user.home") + filename);
        toJson = new ToJson(filename);

    }

    /**
     * This method is annotated with {@code @AfterEach} and runs after each individual test method.
     * It is responsible for cleaning up the test environment and resources used by the tests.
     * <p>
     * In this case, it attempts to delete the test file located at the 'dir' path in the user's home directory.
     *
     * @throws Exception if any error occurs during the cleanup
     */
    @AfterEach
    public void tearDown() throws Exception {
        Paths.get(System.getProperty("user.home") + filename);
        Files.deleteIfExists(dir);
    }

    /**
     * Tests the {@link toJson#toOutputStream(MachineTracker)} method to ensure that it correctly
     * converts a valid {@link MachineTracker} object to a JSON representation and writes it to an output stream.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>A valid {@link MachineTracker} object is provided.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create a sample inventory {@link HashMap} and a {@link VendingMachine} object.</li>
     *   <li>Act: Convert the {@link MachineTracker} to JSON and write it to an output stream.</li>
     *   <li>Assert: Ensure that the output stream is not null, and the JSON representation matches the expected result.</li>
     * </ol>
     */
    @Test
    public void ToJson_ToOutputStream_ValidMachineTracker() {

        //Arrange
        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Øl", 10);
        inventory.put("Cola", 3);
        VendingMachine v1 = new VendingMachine(1, inventory, "Trondheim");
        MachineTracker machineTracker = new MachineTracker();
        machineTracker.addVendingMachine(v1);

        // Act
        OutputStream outputStream = toJson.toOutputStream(machineTracker);

        // Assert
        assertNotNull(outputStream);
        String result = ((ByteArrayOutputStream) outputStream).toString(StandardCharsets.UTF_8);
        assertEquals("{\"machines\":[{\"status\":{\"Øl\":10,\"Cola\":3},\"id\":1,\"location\":\"Trondheim\"}]}", result);
    }


    /**
     * Tests the {@link toJson#toOutputStream(MachineTracker)} method when a null {@link MachineTracker} object is provided.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>A null {@link MachineTracker} object is provided as input.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Act: Call the {@link toJson#toOutputStream(MachineTracker)} method with a null input.</li>
     *   <li>Assert: Ensure that the output stream is not null, and the resulting JSON representation is "null".</li>
     * </ol>
     */
    @Test
    public void ToJson_ToOutputStream_NullMachineTracker() {
        // Act
        OutputStream outputStream = toJson.toOutputStream(null);

        // Assert
        assertNotNull(outputStream);
        String result = ((ByteArrayOutputStream) outputStream).toString(StandardCharsets.UTF_8);
        assertEquals("null", result);
    }


    /**
     * Tests the {@link toJson#toOutputStream(MachineTracker)} method when an empty {@link MachineTracker} object is provided.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>An empty {@link MachineTracker} object is provided as input.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create an empty {@link MachineTracker} object.</li>
     *   <li>Act: Call the {@link toJson#toOutputStream(MachineTracker)} method with the empty input.</li>
     *   <li>Assert: Ensure that the output stream is not null, and the resulting JSON representation
     *       contains an empty list of machines (i.e., "{\"machines\":[]}").</li>
     * </ol>
     */
    @Test
    public void ToJson_toOutputStream_emptyMachineTracker() {
        //Arrange
        MachineTracker emptyMachineTracker = new MachineTracker();

        // Act
        OutputStream outputStream = toJson.toOutputStream(emptyMachineTracker);

        // Assert
        assertNotNull(outputStream);
        String result = ((ByteArrayOutputStream) outputStream).toString(StandardCharsets.UTF_8);
        assertEquals("{\"machines\":[]}", result);
    }

    /**
     * Tests the {@link toJson#writeToFile(MachineTracker)} method to ensure that it correctly
     * writes a valid {@link MachineTracker} object to a file in JSON format.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>A valid {@link MachineTracker} object is provided.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create a sample inventory {@link HashMap} and a {@link VendingMachine} object, and initialize
     *       a {@link MachineTracker} with the VendingMachine.</li>
     *   <li>Act: Call the {@link toJson#writeToFile(MachineTracker)} method to write the MachineTracker object to a file.</li>
     *   <li>Assert: Read the file content and compare it with the expected JSON representation.</li>
     * </ol>
     *
     * @throws IOException if any I/O error occurs during the test
     */
    @Test
    public void ToJson_writeToFile_ValidMachineTracker() throws IOException {

        // Arrange
        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Øl", 10);
        inventory.put("Cola", 3);
        VendingMachine v1 = new VendingMachine(1, inventory, "Trondheim");
        MachineTracker machineTracker = new MachineTracker();
        machineTracker.addVendingMachine(v1);
        String expectedJson = "{\"machines\":[{\"status\":{\"Øl\":10,\"Cola\":3},\"id\":1,\"location\":\"Trondheim\"}]}";

        // Act
        toJson.writeToFile(machineTracker);

        // Assert
        String fileContent = new String(Files.readAllBytes(dir), StandardCharsets.UTF_8);
        assertEquals(expectedJson, fileContent);
    }

    /**
     * Tests the {@link toJson#writeToFile(MachineTracker)} method when a null {@link MachineTracker} object is provided.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>A null {@link MachineTracker} object is provided as input.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Define the expected JSON representation as "null".</li>
     *   <li>Act: Call the {@link toJson#writeToFile(MachineTracker)} method with a null input.</li>
     *   <li>Assert: Read the file content and compare it with the expected "null" value.</li>
     * </ol>
     *
     * @throws IOException if any I/O error occurs during the test
     */
    @Test
    public void ToJson_writeToFile_NullMachineTracker() throws IOException {

        // Arrange
        String expectedJson = "null";

        // Act
        toJson.writeToFile(null);

        // Assert
        String fileContent = new String(Files.readAllBytes(dir), StandardCharsets.UTF_8);
        assertEquals(expectedJson, fileContent);
    }

    /**
     * Tests the {@link toJson#writeToFile(MachineTracker)} method when an empty {@link MachineTracker} object is provided.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>An empty {@link MachineTracker} object is provided as input.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create an empty {@link MachineTracker} object and define the expected JSON representation.</li>
     *   <li>Act: Call the {@link toJson#writeToFile(MachineTracker)} method to write the empty MachineTracker to a file.</li>
     *   <li>Assert: Read the file content and compare it with the expected JSON representation containing an empty list of machines.</li>
     * </ol>
     *
     * @throws IOException if any I/O error occurs during the test
     */
    @Test
    public void ToJson_writeToFile_emptyMachineTracker() throws IOException {

        // Arrange
        MachineTracker emptyMachineTracker = new MachineTracker();
        String expectedJson = "{\"machines\":[]}";

        // Act
        toJson.writeToFile(emptyMachineTracker);

        // Assert
        String fileContent = new String(Files.readAllBytes(dir), StandardCharsets.UTF_8);
        assertEquals(expectedJson, fileContent);
    }

}
