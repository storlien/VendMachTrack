package jsonio.internal;

import com.google.gson.Gson;
import core.IMachineTracker;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ToJson {

    private final String filePath;

    /**
     * Constructor. Requires a file name for the MachineTracker object to be saved to.
     *
     * @param fileName File name.
     */
    public ToJson(String fileName) {
        this.filePath = System.getProperty("user.home") + "/" + fileName;
    }

    /**
     * Parses MachineTracker object to JSON data and returns as OutputStream.
     *
     * @return OutputStream of parsed MachineTracker object
     */
    public OutputStream toOutputStream(IMachineTracker machtrack) {
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
     * Parses MachineTracker object to JSON data and writes to file.
     *
     * @param machtrack MachineTracker object to be parsed and written to file.
     */
    public void writeToFile(IMachineTracker machtrack) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(machtrack);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8))) {
            bw.write(jsonString);
        } catch (Exception e) {
            System.err.println("Error writing to file\n" + e);
        }
    }
}