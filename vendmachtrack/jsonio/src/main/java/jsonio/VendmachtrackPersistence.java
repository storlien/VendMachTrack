package jsonio;

import core.MachineTracker;
import jsonio.internal.FromJson;
import jsonio.internal.ToJson;

public class VendmachtrackPersistence {

    private FromJson fromJson;
    private ToJson toJson;

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
