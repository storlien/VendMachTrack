package jsonio.internal;

import com.google.gson.Gson;
import core.MachineTracker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FromJson {

    private final String filePath;

    /**
     * Constructor. Requires a file name for which the MachineTracker object will be read from.
     *
     * @param fileName Name of file.
     */
    public FromJson(String fileName) {
        this.filePath = System.getProperty("user.home") + "/" + fileName;
    }

    /**
     * Deserializes MachineTracker object from InputStream.
     *
     * @param is InputStream with JSON data
     * @return MachineTracker object from InputStream
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
     * Reads and returns MachineTracker object from file.
     *
     * @return MachineTracker object
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