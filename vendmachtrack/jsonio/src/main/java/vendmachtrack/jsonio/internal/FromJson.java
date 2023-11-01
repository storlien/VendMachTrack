package vendmachtrack.jsonio.internal;

import com.google.gson.Gson;
import vendmachtrack.core.MachineTracker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * The {@code FromJson} class provides methods to deserialize a {@link MachineTracker}
 * object from its JSON representation. The class can read from either an InputStream or a file.
 * <p>
 * This class uses the Gson library to perform the deserialization. The provided file name
 * is used to determine the absolute path of the file in the user's home directory, where
 * the JSON data is expected to be stored.
 * </p>
 * Example usage:
 * <pre>
 * FromJson fromJson = new FromJson("machineTracker.json");
 * MachineTracker machineTracker = fromJson.readFromFile();
 * </pre>
 */
public class FromJson {

    private final String filePath;

    /**
     * Initializes a new {@code FromJson} instance using the specified file name.
     * The full path is derived by appending the file name to the user's home directory.
     *
     * @param fileName Name of the file in the user's home directory from which the
     *                 {@link MachineTracker} object will be read.
     */
    public FromJson(String fileName) {
        this.filePath = System.getProperty("user.home") + "/" + fileName;
    }

    /**
     * Deserializes a {@link MachineTracker} object from the provided {@link InputStream}
     * containing its JSON representation. Utilizes the Gson library for the deserialization process.
     * <p>
     * If there are issues during the deserialization process (such as malformed JSON data),
     * an error is printed to the standard error stream and null is returned.
     *
     * @param is InputStream containing the JSON data of a {@link MachineTracker} object.
     * @return The deserialized {@link MachineTracker} object or {@code null} if deserialization fails.
     */
    public MachineTracker fromInputStream(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            Gson gson = new Gson();
            return gson.fromJson(br, MachineTracker.class);
        } catch (Exception e) {
            System.err.println("Error deserializing from InputStream\n" + e);
            return null;
        }
    }

    /**
     * Reads the content of the specified file (determined during instantiation) and attempts
     * to deserialize a {@link MachineTracker} object from its JSON representation.
     * <p>
     * If there are issues during reading the file or the deserialization process
     * (such as the file not existing or containing malformed JSON data),
     * an error is printed to the standard error stream and null is returned.
     *
     * @return The deserialized {@link MachineTracker} object from the file or {@code null}
     * if reading or deserialization fails.
     */
    public MachineTracker readFromFile() {
        try (InputStream is = new FileInputStream(filePath)) {
            return fromInputStream(is);
        } catch (Exception e) {
            System.err.println("Error reading from file: " + filePath + "\n" + e);
            return null;
        }
    }

}