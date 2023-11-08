package gr2338.vendmachtrack.jsonio.internal;

import com.google.gson.Gson;
import gr2338.vendmachtrack.core.model.MachineTracker;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * The {@code ToJson} class provides methods to serialize a {@link MachineTracker}
 * object into its JSON representation and write to either an OutputStream or a file.
 * <p>
 * This class uses the Gson library to perform the serialization. The provided file name
 * is used to determine the absolute path of the file in the user's home directory, where
 * the serialized JSON data will be saved.
 * </p>
 * Example usage:
 * <pre>
 * ToJson toJson = new ToJson("machineTracker.json");
 * toJson.writeToFile(machineTrackerInstance);
 * </pre>
 */
public class ToJson {

    private final String filePath;

    /**
     * Initializes a new {@code ToJson} instance using the specified file name.
     * The full path is derived by appending the file name to the user's home directory.
     * This path will be used to save the serialized JSON representation of the {@link MachineTracker} object.
     *
     * @param fileName Name of the file in the user's home directory to which the
     *                 {@link MachineTracker} object will be saved.
     */
    public ToJson(final String fileName) {
        this.filePath = System.getProperty("user.home") + "/" + fileName;
    }

    /**
     * Serializes the provided {@link MachineTracker} object into its JSON representation
     * and returns the data as an {@link OutputStream}. The Gson library is used for the serialization process.
     * <p>
     * This method is particularly useful for applications that might send serialized data over
     * networks, for instance via TCP/IP protocol streams.
     * <p>
     * If there's an error during the serialization or writing process, an error is printed
     * to the standard error stream and null is returned.
     *
     * @param machtrack The {@link MachineTracker} object to be serialized.
     * @return An {@link OutputStream} containing the JSON representation of the provided object,
     * or {@code null} if the serialization process fails.
     */
    public OutputStream toOutputStream(final MachineTracker machtrack) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(machtrack);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(jsonString.getBytes(StandardCharsets.UTF_8));
            return baos;
        } catch (Exception e) {
            System.err.println("Error writing to ByteArrayOutputStream\n" + e);
            return null;
        }
    }

    /**
     * Serializes the provided {@link MachineTracker} object into its JSON representation
     * and writes the resulting data to a file. The file's location is determined by the file name
     * provided during the instantiation of this class.
     * <p>
     * The Gson library is used for the serialization process. If there are issues during serialization
     * or writing the file, an error message will be printed to the standard error stream.
     *
     * @param machtrack The {@link MachineTracker} object to be serialized and saved to file.
     */
    public void writeToFile(final MachineTracker machtrack) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(machtrack);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8))) {
            bw.write(jsonString);
        } catch (Exception e) {
            System.err.println("Error writing to file\n" + e);
        }
    }
}
