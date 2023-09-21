package jsonio;

import core.IMachineTracker;

import java.io.OutputStream;

public interface IToJson {

    OutputStream toOutputStream();

    boolean writeToFile();

}
