package vendmachtrack.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of vending machines.
 * It allows adding, removing, and retrieving vending machines from the machine tracker.
 */

public class MachineTracker {


    private List<VendingMachine> machines = new ArrayList<>();

    /**
     * Removes the specifies vending machine from the machinetracker.
     *
     * @param v The vending machine to be removed from the tracker.
     * @throws IllegalArgumentException If the machine is not found in the tracker.
     */
    public void removeVendingMachine(VendingMachine v) {
        if (!machines.contains(v)) {
            throw new IllegalArgumentException("The specified vending machine is not part of this tracker");
        } else {
            machines.remove(v);
        }
    }

    /**
     * Adds the specified vending machine to the machinetracker.
     *
     * @param v The vending machine to be added to the tracker.
     * @throws IllegalArgumentException If the vending machine is already in the tracker.
     */
    public void addVendingMachine(VendingMachine v) {
        if (machines.contains(v)) {
            throw new IllegalArgumentException("The vendingmachine is already part of this tracker");
        } else {
            machines.add(v);
        }
    }


    /**
     * Retrieves a list of vending machines in this tracker.
     *
     * @return A list of vending machines in this tracker.
     */
    public List<VendingMachine> getMachines() {
        return new ArrayList<>(machines);
    }


    /**
     * Sets the list of vending machines in this tracker.
     *
     * @param machines The list of vending machines to be set in the tracker.
     */
    public void setMachines(List<VendingMachine> machines) {
        this.machines = new ArrayList<>(machines);
    }

}
