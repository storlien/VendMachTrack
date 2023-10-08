package jsonio;

import com.google.gson.Gson;
import core.IMachineTracker;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ToJson implements IToJson {

    private final String filePath;

    /**
     * Constructor.
     */
    public ToJson(String file) {
        filePath = "jsonio/src/main/resources/" + file;

//        The following line for setting the filePath allows for several MachineTrackers. To be implemented in a later release.
//        filePath = "jsonio/src/main/resources/" + mach.toString();
    }

    /**
     * Parses MachineTracker object to JSON data and returns as OutputStream.
     *
     * @return OutputStream of MachineTracker object
     */
    @Override
    public OutputStream toOutputStream(IMachineTracker machtrack) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(machtrack);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(jsonString.getBytes(StandardCharsets.UTF_8));
            return baos;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error writing to ByteArrayOutputStream");
        }
    }

    /**
     * Parses MachineTracker object to JSON data and writes to file.
     */
    @Override
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