package jsonio;

import core.IMachineTracker;

import java.io.InputStream;

public interface IFromJson {

    IMachineTracker fromInputStream(InputStream is);

}
