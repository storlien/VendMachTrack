package jsonio;

import core.IMachineTracker;

import java.io.OutputStream;

public interface IToJson {

    OutputStream toOutputStream(IMachineTracker machtrack);

    void writeToFile(IMachineTracker machtrack);

}