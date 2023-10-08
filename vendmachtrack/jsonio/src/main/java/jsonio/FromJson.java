package jsonio;

import com.google.gson.Gson;
import core.MachineTracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FromJson implements IFromJson {

    private final String filePath;

    public FromJson() {
        this.filePath = "/machine1"; // Note the leading slash for resource access
    }

    /**
     * Deserializes MachineTracker object from InputStream
     *
     * @param is InputStream with JSON data
     * @return MachineTracker object from InputStream
     */
    @Override
    public MachineTracker fromInputStream(InputStream is) {

        try {

            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return gson.fromJson(br, MachineTracker.class);
        } catch (Exception e) {
            System.out.println("Error deserializing from InputStream\n" + e);
            return null;
        }
    }

    @Override
    public MachineTracker readFromFile() {
        try (InputStream is = this.getClass().getResourceAsStream(filePath)) {
            return fromInputStream(is);
        } catch (Exception e) {
            System.err.println("Error" + e);
            return null;
        }
    }

}