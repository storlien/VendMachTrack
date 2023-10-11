package core;

import java.util.ArrayList;
import java.util.List;


public class MachineTracker implements IMachineTracker {


    private List<VendingMachine> machines = new ArrayList<>();

    /**
     * Removes the vendingmachine from the machinetracker
     *
     * @param v the vendingmachine to remove from the tracker
     * @throws IllegalArgumentException if the machine doesn't contain the vendingmachine v
     */
    @Override
    public void removeVendingMachine(VendingMachine v) {
        if (!machines.contains(v)) {
            throw new IllegalArgumentException("There is no such vendingmachine in this tracker");
        } else {
            machines.remove(v);
        }
    }

    /**
     * Adds the vendingmachine to the machinetracker
     *
     * @param v the vendingmachine to add to the tracker
     * @throws IllegalArgumentException if the machine already contains the vendingmachine v
     */
    @Override
    public void addVendingMachine(VendingMachine v) {
        if (machines.contains(v)) {
            throw new IllegalArgumentException("The vendingmachine is already part of this tracker");
        } else {
            machines.add(v);
        }
    }


    /**
     * @return the list of the vendingmachines in this tracker
     */
    @Override
    public List<VendingMachine> getMachines() { //evt legge til i interface
        return new ArrayList<>(machines);
    }


    /**
     * sets the machine
     *
     * @param machines
     */
    @Override
    public void setMachines(List<VendingMachine> machines) {
        this.machines = new ArrayList<>(machines);
    }

}
