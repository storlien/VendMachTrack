package jsonio;

import core.MachineTracker;
import jsonio.internal.FromJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class FromJsonTest {

    private FromJson fromJson;
    private String filePath;
    private String jsonString = "{\"machines\":[{\"status\":{\"Cola\":5,\"Pepsi\":3},\"id\":1,\"location\":\"Trondhjem\"},{\"status\":{\"Tuborg\":1},\"id\":2,\"location\":\"Oslo\"},{\"status\":{\"Hansa\":100,\"Regnvann\":10},\"id\":3,\"location\":\"Bergen\"}]}";

    /**
     * Sets up the test fixture. This method is called before each test case method is executed.
     * It initializes the 'fromJson' object with the 'tracker.json' file and creates a temporary file
     * with the '.json' extension to be used for testing purposes. The file path of the temporary file
     * is stored in the 'filePath' variable.
     *
     * @throws IOException if an I/O error occurs while creating the temporary file
     */
    @BeforeEach
    public void setUp() throws IOException {
        fromJson = new FromJson("tracker.json");
        File tempFile = File.createTempFile("test", ".json");
        filePath = tempFile.getAbsolutePath();
        tempFile.deleteOnExit();
    }

    /**
     * Tests the {@link fromJson#fromInputStream(InputStream)} method with valid JSON data.
     *
     * @param jsonString A string containing valid JSON data
     */
    @Test
    public void testFromInputStreamValidJson() {
        // Create an input stream with valid JSON data
        InputStream inputStream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));

        // Deserialize the JSON data into a MachineTracker object
        MachineTracker machineTracker = fromJson.fromInputStream(inputStream);

        // Assert that the MachineTracker object is not null and contains the expected data
        assertNotNull(machineTracker);
        assertEquals(3, machineTracker.getMachines().size());
        assertEquals(1, machineTracker.getMachines().get(0).getId());
    }


    /**
     * Tests the {@link FromJson#fromInputStream(InputStream)} method with invalid JSON data.
     *
     * @throws IOException if an I/O error occurs while reading the input stream
     */
    @Test
    public void testFromInputStreamInvalidJson() {
        // Create an input stream with invalid JSON data
        InputStream inputStream = new ByteArrayInputStream("{invalid json}".getBytes(StandardCharsets.UTF_8));

        // Deserialize the JSON data into a MachineTracker object
        MachineTracker machineTracker = fromJson.fromInputStream(inputStream);

        // Assert that the MachineTracker object is null
        assertNull(machineTracker);
    }

    /**
     * Tests the behavior of the {@link FromJson#fromInputStream(InputStream)} method when a null input stream is provided.
     */
    @Test
    public void testFromInputStreamNullInputStream() {
        // Deserialize a null input stream into a MachineTracker object
        MachineTracker machineTracker = fromJson.fromInputStream(null);

        // Assert that the MachineTracker object is null
        assertNull(machineTracker);
    }

    /**
     * Tests the {@link fromJson#fromInputStream(InputStream)} method with an empty input stream.
     */
    @Test
    public void testFromInputStreamEmptyInputStream() {
        // Create an input stream with empty data
        InputStream inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));

        // Deserialize the empty data into a MachineTracker object
        MachineTracker machineTracker = fromJson.fromInputStream(inputStream);

        // Assert that the MachineTracker object is null
        assertNull(machineTracker);
    }


    /**
     * Tests the readFromFile method of the FromJson class with a valid file.
     *
     * @throws IOException if an I/O error occurs while reading the file
     */
    @Test
    public void testReadFromFileValidFile() throws IOException {
        // Create a temporary file with valid JSON data
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(jsonString.getBytes(StandardCharsets.UTF_8));
        }

        // Deserialize the JSON data into a MachineTracker object
        MachineTracker machineTracker = fromJson.readFromFile();

        // Assert that the MachineTracker object is not null and contains the expected data
        assertNotNull(machineTracker);
        assertEquals(3, machineTracker.getMachines().size());
        assertEquals(5, machineTracker.getMachines().get(0).getStatus().get("Cola"));
        assertEquals(3, machineTracker.getMachines().get(0).getStatus().get("Pepsi"));
        assertEquals(1, machineTracker.getMachines().get(1).getStatus().get("Tuborg"));
        assertEquals(100, machineTracker.getMachines().get(2).getStatus().get("Hansa"));
        assertEquals(10, machineTracker.getMachines().get(2).getStatus().get("Regnvann"));
    }

    /**
     * Tests the behavior of the FromJson.readFromFile() method when given an invalid file path.
     *
     * <p>Creates a temporary file with invalid JSON data, initializes a new FromJson object with the same file path,
     * and attempts to deserialize the JSON data into a MachineTracker object. Asserts that the MachineTracker object
     * is null.</p>
     *
     * @throws IOException if an I/O error occurs while writing to the temporary file
     */
    @Test
    public void testReadFromFileInvalidFile() throws IOException {
        // Create a temporary file with invalid JSON data
        String invalidJsonString = "{invalid json}";
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(invalidJsonString.getBytes(StandardCharsets.UTF_8));
        }

        // Create a new FromJson object with the same file path
        FromJson fromJson = new FromJson(filePath);

        // Deserialize the JSON data into a MachineTracker object
        MachineTracker machineTracker = fromJson.readFromFile();

        // Assert that the MachineTracker object is null
        assertNull(machineTracker);
    }


    /**
     * This method tests the readFromFile() method of the FromJson class when a null file path is provided.
     * It creates a FromJson object with a null file path, deserializes the JSON data into a MachineTracker object,
     * and asserts that the MachineTracker object is null.
     *
     * @param None
     * @return void
     */
    @Test
    public void testReadFromFileNullFilePath() {
        // Create a FromJson object with a null file path
        FromJson fromJsontest = new FromJson(null);

        // Deserialize the JSON data into a MachineTracker object
        MachineTracker machineTracker = fromJsontest.readFromFile();

        // Assert that the MachineTracker object is null
        assertNull(machineTracker);
    }

}
    
     

    