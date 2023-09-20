package jsonio;

import com.google.gson.Gson;
import core.MachineTracker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FromJson implements IFromJson {

    private final String filePath;

    public FromJson() {
        this.filePath = "jsonio/src/main/resources/machine1";
    }

    /**
     * Deserializes MachineTracker object from InputStream
     *
     * @param is InputStream with JSON data
     * @return MachineTracker object from InputStream
     */
    @Override
    public MachineTracker fromInputStream(InputStream is) {
        Gson gson = new Gson();

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            MachineTracker machtrack = gson.fromJson(br, MachineTracker.class);

            return machtrack;

        } catch (Exception e) {

            System.out.println("Error deserializing from InputStream\n" + e);
            return null;

        }

    }

    @Override
    public MachineTracker readFromFile() {
        return null;
    }
}