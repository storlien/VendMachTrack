package jsonio;

import com.google.gson.Gson;
import core.IMachineTracker;

import java.io.*;

public class ToJson implements IToJson {

    private final IMachineTracker machtrack;
    private final String filePath;

    /**
     * Constructor.
     *
     * @param machtrack MachineTracker to be parsed to JSON data.
     */
    public ToJson(IMachineTracker machtrack) {
        this.machtrack = machtrack;
        this.filePath = "jsonio/src/main/resources/machine1";

//        The following line for setting the filePath allows for several MachineTrackers. To be implemented in a later release.
//        this.filePath = "jsonio/src/main/resources/" + mach.toString();
    }

    /**
     * Parses MachineTracker object to JSON data and returns as OutputStream.
     *
     * @return OutputStream of MachineTracker object
     */
    @Override
    public OutputStream toOutputStream() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(machtrack);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            baos.write(jsonString.getBytes());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error when writing to ByteArrayOutputStream");
        }

        return baos;
    }

    /**
     * Parses MachineTracker object to JSON data and writes to file.
     *
     * @return True if writing to file went well, false if not.
     */
    @Override
    public boolean writeToFile() {

        Gson gson = new Gson();
        String jsonString = gson.toJson(machtrack);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));

            bw.write(jsonString);
            bw.close();

        } catch (Exception e) {
            System.out.println("Error writing to file\n" + e);
            return false;
        }

        return true;
    }

}