package jsonio;

import core.MachineTracker;
import core.VendingMachine;

import jsonio.internal.ToJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains JUnit tests for the ToJson class. It tests the serialization of MachineTracker objects to JSON format.
 *
 * @see ToJson
 * @see MachineTracker
 */
public class ToJsonTest {

    private ToJson toJson;
    private String filePath;
    private HashMap<String, Integer> inventory = new HashMap<>();


    /**
     * Sets up the test fixture. This method is called before each test case method is executed.
     * It initializes a new instance of the ToJson class with the filename "testfile.json".
     *
     * @throws IOException if an I/O error occurs while creating the ToJson instance.
     */
    @BeforeEach
    public void setUp() throws IOException {
        toJson = new ToJson("testfile.json");
    }


    /**
     * Tests the toJson.toOutputStream() method by creating a new MachineTracker object with a single VendingMachine object
     * and serializing it to an OutputStream. The test asserts that the OutputStream is not null and contains the expected data.
     *
     * @throws IOException if an I/O error occurs while writing to the OutputStream
     */
    @Test
    public void testToOutputStreamValidMachineTracker() {
        // Create a new inventory HashMap with 10 units of Øl and 3 units of Cola
        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Øl", 10);
        inventory.put("Cola", 3);

        // Create a new VendingMachine object with the inventory and a location of "Trondheim"
        VendingMachine v1 = new VendingMachine(1, inventory, "Trondheim");

        // Create a new MachineTracker object and add the VendingMachine object to it
        MachineTracker machineTracker = new MachineTracker();
        machineTracker.addVendingMachine(v1);

        // Serialize the MachineTracker object to an OutputStream
        OutputStream outputStream = toJson.toOutputStream(machineTracker);

        // Assert that the OutputStream is not null and contains the expected data
        assertNotNull(outputStream);
        String result = new String(((ByteArrayOutputStream) outputStream).toByteArray(), StandardCharsets.UTF_8);
        assertEquals("{\"machines\":[{\"status\":{\"Øl\":10,\"Cola\":3},\"id\":1,\"location\":\"Trondheim\"}]}", result);
    }


    /**
     * Tests the {@link ToJson#toOutputStream(MachineTracker)} method when a null MachineTracker object is passed as input.
     *
     * @result The method should serialize the null object to an OutputStream and return it. The OutputStream should contain the string "null".
     */
    @Test
    public void testToOutputStreamNullMachineTracker() {
        // Serialize a null MachineTracker object to an OutputStream
        OutputStream outputStream = toJson.toOutputStream(null);

        // Assert that the OutputStream is not null and contains the expected data
        assertNotNull(outputStream);
        String result = new String(((ByteArrayOutputStream) outputStream).toByteArray(), StandardCharsets.UTF_8);
        assertEquals("null", result);
    }


    /**
     * Tests the {@link ToJson#toOutputStream(MachineTracker)} method with an empty {@link MachineTracker} object.
     *
     * <p>Creates an empty {@link MachineTracker} object and serializes it to an {@link OutputStream}.
     * Then, asserts that the {@link OutputStream} is not null and contains the expected data.</p>
     *
     * @see ToJson#toOutputStream(MachineTracker)
     * @see MachineTracker
     */
    @Test
    public void testToOutputStreamEmptyMachineTracker() {
        // Create an empty MachineTracker object
        MachineTracker emptyMachineTracker = new MachineTracker();

        // Serialize the empty MachineTracker object to an OutputStream
        OutputStream outputStream = toJson.toOutputStream(emptyMachineTracker);

        // Assert that the OutputStream is not null and contains the expected data
        assertNotNull(outputStream);
        String result = new String(((ByteArrayOutputStream) outputStream).toByteArray(), StandardCharsets.UTF_8);
        assertEquals("{\"machines\":[]}", result);
    }


    /**
     * Tests the behavior of the writeToFile method when a null object is passed.
     *
     * @see ToJson#writeToFile(MachineTracker)
     */
    @Test
    public void testWriteToFile_nullMachineTracker() {
        // This test verifies that no exception is thrown when a null object is passed
        assertDoesNotThrow(() -> toJson.writeToFile(null));
    }


}
