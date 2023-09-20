package core;

import java.util.List;

public interface IMachineTracker {

    /**
     * remove the vendingmachine from tracker
     *
     * @param v
     */
    public void removeVendingMachine(VendingMachine v);

    /**
     * add the vendingmachine to tracker
     *
     * @param v
     */
    public void addVendingMachine(VendingMachine v);


    /**
     * @return the list of machines
     */
    public List<VendingMachine> getMachines();


    /**
     * sets the machine
     *
     * @param machines
     */
    public void setMachines(List<VendingMachine> machines);

}
