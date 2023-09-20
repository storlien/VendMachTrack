package jsonio;

import com.google.gson.Gson;
import core.IMachineTracker;

import java.io.*;

public class ToJson {

    private final IMachineTracker mach;

    public ToJson(IMachineTracker mach) {
        this.mach = mach;
    }

    public OutputStream toOutputStream() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(mach);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            baos.write(jsonString.getBytes());
        }

        catch (Exception e) {
            throw new IllegalArgumentException("Error when writing to ByteArrayOutputStream");
        }

        return baos;
    }

    public boolean writeToFile() {

        /*try {
            BufferedWriter bw = new BufferedWriter(new FileOutputStream("src/main/java"));
        } catch (Exception e) {
            throw new IllegalStateException("Error writing to file");
        }*/

        return false;
    }

}