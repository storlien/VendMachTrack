package vendmachtrack.jsonio.internal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vendmachtrack.core.model.MachineTracker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains JUnit tests for the {@link FromJson} class, specifically focusing on deserializing
 * JSON data into {@link MachineTracker} objects.
 *
 * @see FromJson
 * @see MachineTracker
 */
public class FromJsonTest {

    private FromJson fromJson;
    private final String jsonString = "{\"machines\":[{\"status\":{\"Cola\":5,\"Pepsi\":3},\"id\":1,\"location\":\"Trondhjem\"},{\"status\":{\"Tuborg\":1},\"id\":2,\"location\":\"Oslo\"},{\"status\":{\"Hansa\":100,\"Regnvann\":10},\"id\":3,\"location\":\"Bergen\"}]}";
    private final String filename = "/testfile.json";
    private Path dir;


    /**
     * This method is annotated with {@code @BeforeEach} and runs before each individual test method.
     * It is responsible for setting up the test environment and resources needed by the tests.
     * <p>
     * In this case, it initializes the 'dir' variable with the path to a file in the user's home directory,
     * writes a JSON string to that file, and creates an instance of {@link FromJson} for testing.
     *
     * @throws IOException if any error occurs during the setup
     */
    @BeforeEach
    public void setUp() throws IOException {
        dir = Paths.get(System.getProperty("user.home") + filename);
        Files.write(dir, jsonString.getBytes(StandardCharsets.UTF_8));
        fromJson = new FromJson("testfile.json");
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
     * Tests the {@link FromJson#fromInputStream(InputStream)} method when provided with an input stream containing
     * valid JSON data.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>An input stream containing valid JSON data is provided to the {@link FromJson#fromInputStream(InputStream)}
     *       method.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create an input stream containing valid JSON data.</li>
     *   <li>Act: Call the {@link FromJson#fromInputStream(InputStream)} method to deserialize the valid JSON data
     *       from the input stream into a {@link MachineTracker} object.</li>
     *   <li>Assert: Verify that the resulting {@link MachineTracker} object is not null and contains the expected data,
     *       such as the number of machines and their properties.</li>
     * </ol>
     */
    @Test
    public void FromJson_fromInputStream_ValidJson() {
        // Arrange
        InputStream inputStream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));

        // Act
        MachineTracker machineTracker = fromJson.fromInputStream(inputStream);

        // Assert
        assertNotNull(machineTracker);
        assertEquals(3, machineTracker.getMachines().size());
        assertEquals(1, machineTracker.getMachines().get(0).getId());
    }


    /**
     * Tests the {@link FromJson#fromInputStream(InputStream)} method when provided with an input stream containing
     * invalid JSON data.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>An input stream containing invalid JSON data is provided to the {@link FromJson#fromInputStream(InputStream)}
     *       method.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create an input stream containing invalid JSON data.</li>
     *   <li>Act: Call the {@link FromJson#fromInputStream(InputStream)} method to deserialize the invalid JSON data
     *       from the input stream into a {@link MachineTracker} object.</li>
     *   <li>Assert: Verify that the resulting {@link MachineTracker} object is null, as it should not be able to
     *       deserialize invalid JSON data.</li>
     * </ol>
     */
    @Test
    public void FromJson_fromInputStream_InvalidJson() {
        // Arrange
        InputStream inputStream = new ByteArrayInputStream("{invalid json}".getBytes(StandardCharsets.UTF_8));

        // Act
        MachineTracker machineTracker = fromJson.fromInputStream(inputStream);

        // Assert
        assertNull(machineTracker);
    }

    /**
     * Tests the {@link FromJson#fromInputStream(InputStream)} method when provided with a null input stream.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>A null input stream is provided to the {@link FromJson#fromInputStream(InputStream)} method.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Act: Call the {@link FromJson#fromInputStream(InputStream)} method with a null input stream.</li>
     *   <li>Assert: Verify that the resulting {@link MachineTracker} object is null, as there is no data to deserialize.</li>
     * </ol>
     */
    @Test
    public void FromJson_fromInputStream_NullInputStream() {

        // Act
        MachineTracker machineTracker = fromJson.fromInputStream(null);

        // Assert
        assertNull(machineTracker);
    }

    /**
     * Tests the {@link FromJson#fromInputStream(InputStream)} method when provided with an empty inputstream.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>An empty input stream is provided to the {@link FromJson#fromInputStream(InputStream)} method.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create an empty input stream.</li>
     *   <li>Act: Call the {@link FromJson#fromInputStream(InputStream)} method to deserialize the JSON content
     *       from the empty input stream into a {@link MachineTracker} object.</li>
     *   <li>Assert: Verify that the resulting {@link MachineTracker} object is null, as there is no data to deserialize.</li>
     * </ol>
     */
    @Test
    public void FromJson_fromInputStream_EmptyInputStream() {

        // Arrange
        InputStream inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));

        // Act
        MachineTracker machineTracker = fromJson.fromInputStream(inputStream);

        // Assert
        assertNull(machineTracker);
    }

    /**
     * Tests the {@link FromJson#readFromFile()} method when reading from a valid file
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     *     <li>A valid file path is provided to the constructor of {@link FromJson}.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Act: Call the {@link FromJson#readFromFile()} method to read from the valid file path.</li>
     *   <li>Assert: Verify that the result is a Valid MachineTracker, with the correct VedningMachines</li>
     * </ol>
     */
    @Test
    public void FromJson_readFromFile_ValidFile() throws IOException {
        // Act
        MachineTracker machineTracker = fromJson.readFromFile();

        // Assert
        assertNotNull(machineTracker);
        assertEquals(3, machineTracker.getMachines().size());
        assertEquals(5, machineTracker.getMachines().get(0).getStatus().get("Cola"));
        assertEquals(3, machineTracker.getMachines().get(0).getStatus().get("Pepsi"));
        assertEquals(1, machineTracker.getMachines().get(1).getStatus().get("Tuborg"));
        assertEquals(100, machineTracker.getMachines().get(2).getStatus().get("Hansa"));
        assertEquals(10, machineTracker.getMachines().get(2).getStatus().get("Regnvann"));

    }


    /**
     * Tests the {@link FromJson#readFromFile()} method when provided with a null file path.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>A null file path is provided to the constructor of {@link FromJson}.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create an instance of {@link FromJson} with a null file path.</li>
     *   <li>Act: Call the {@link FromJson#readFromFile()} method to read from the null file path.</li>
     *   <li>Assert: Verify that the result is null, as the method should return null when a null file path is provided.</li>
     * </ol>
     */
    @Test
    public void FromJson_readFromFile_NullFilePath() {
        // Arrange
        FromJson fromJsontest = new FromJson(null);

        // Act
        MachineTracker machineTracker = fromJsontest.readFromFile();

        // Assert
        assertNull(machineTracker);
    }

    /**
     * Tests the {@link FromJson#readFromFile()} method when provided with an invalid file path.
     * <p>
     * This test case focuses on the scenario where:
     * <ul>
     *   <li>An invalid file path is provided to the constructor of {@link FromJson}.</li>
     * </ul>
     *
     * <p>
     * To conduct this test, we perform the following steps:
     * </p>
     * <ol>
     *   <li>Arrange: Create an instance of {@link FromJson} with an invalid file path.</li>
     *   <li>Act: Call the {@link FromJson#readFromFile()} method to read from the invalid file path.</li>
     *   <li>Assert: Verify that the result is null, as the method should return null when it encounters an exception
     *       due to the invalid file path.</li>
     * </ol>
     */
    @Test
    public void FromJson_readFromFile_InvalidFile() {
        // Arrange
        FromJson fromJson = new FromJson("invalid_file_path.json");

        // Act
        MachineTracker result = fromJson.readFromFile();

        // Assert
        assertNull(result);
    }

}



