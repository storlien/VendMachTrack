package vendmachtrack.jsonio;

import vendmachtrack.core.MachineTracker;
import vendmachtrack.jsonio.internal.FromJson;
import vendmachtrack.jsonio.internal.ToJson;

public class VendmachtrackPersistence {

    private final FromJson fromJson;
    private final ToJson toJson;

    public VendmachtrackPersistence(String fileName) {
        this.fromJson = new FromJson(fileName);
        this.toJson = new ToJson(fileName);
    }

    public MachineTracker getVendmachtrack() {
        return fromJson.readFromFile();
    }

    public MachineTracker saveVendmachtrack(MachineTracker vendmachtrack) {
        toJson.writeToFile(vendmachtrack);
        return vendmachtrack;
    }

}
