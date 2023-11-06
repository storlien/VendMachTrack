package vendmachtrack.jsonio;

import vendmachtrack.core.MachineTracker;
import vendmachtrack.jsonio.internal.FromJson;
import vendmachtrack.jsonio.internal.ToJson;

/**
 * The {@code VendmachtrackPersistence} class offers a high-level interface for
 * persisting
 * and retrieving {@link MachineTracker} objects to and from a specified file in
 * JSON format.
 * <p>
 * Internally, this class utilizes the {@code FromJson} and {@code ToJson}
 * classes from
 * the {@code vendmachtrack.jsonio.internal} package to handle the serialization
 * and deserialization processes.
 * </p>
 * Example usage:
 * 
 * <pre>
 * VendmachtrackPersistence persistence = new VendmachtrackPersistence("machineTracker.json");
 * MachineTracker savedTracker = persistence.saveVendmachtrack(machineTrackerInstance);
 * MachineTracker loadedTracker = persistence.getVendmachtrack();
 * </pre>
 */
public class VendmachtrackPersistence {

    private final FromJson fromJson;
    private final ToJson toJson;

    /**
     * Constructor that initializes internal {@code FromJson} and {@code ToJson}
     * objects
     * using the provided file name.
     *
     * @param fileName Name of the file for reading/writing the
     *                 {@link MachineTracker} object.
     */
    public VendmachtrackPersistence(String fileName) {
        this.fromJson = new FromJson(fileName);
        this.toJson = new ToJson(fileName);
    }

    /**
     * Retrieves the {@link MachineTracker} object from the specified file.
     *
     * @return The deserialized {@link MachineTracker} object.
     */
    public MachineTracker getVendmachtrack() {
        return fromJson.readFromFile();
    }

    /**
     * Saves the given {@link MachineTracker} object to the specified file in JSON
     * format.
     *
     * @param vendmachtrack The {@link MachineTracker} object to be saved.
     * @return The same {@link MachineTracker} object that was saved.
     */
    public MachineTracker saveVendmachtrack(MachineTracker vendmachtrack) {
        toJson.writeToFile(vendmachtrack);
        return vendmachtrack;
    }

}
