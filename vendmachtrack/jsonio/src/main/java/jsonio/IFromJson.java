package jsonio;

import core.MachineTracker;

import java.io.InputStream;

public interface IFromJson {

    MachineTracker fromInputStream(InputStream is);

    MachineTracker readFromFile();

}