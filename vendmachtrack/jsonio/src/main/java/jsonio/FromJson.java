package jsonio;

import com.google.gson.Gson;
import core.IMachineTracker;

import java.io.InputStream;

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
    public IMachineTracker fromInputStream(InputStream is) {
        Gson gson = new Gson();
        return null;
    }
}
