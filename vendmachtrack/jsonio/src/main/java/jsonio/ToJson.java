package jsonio;

import com.google.gson.Gson;
import core.IMachineTracker;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ToJson implements IToJson {

    private final String filePath;

    /**
     * Constructor. Requires a file name for the MachineTracker object to be saved to.
     *
     * @param file File name.
     */
    public ToJson(String file) {
        filePath = "jsonio/src/main/resources/" + file;
    }

    /**
     * Parses MachineTracker object to JSON data and returns as OutputStream.
     *
     * @return OutputStream of parsed MachineTracker object
     */
    @Override
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
    @Override
    public void writeToFile(IMachineTracker machtrack) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(machtrack);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8))) {
            bw.write(jsonString);
            System.err.println("Writing ok");
        } catch (Exception e) {
            System.err.println("Error writing to file\n" + e);
        }
    }
}