package core;

import java.util.List;

public interface IMachineTracker{

    /**
     * remove the vendingmachine from tracker
	 * @param v  
	 */
    public void removeVendingMachine(IVendingMachine v);

    /**
     * add the vendingmachine to tracker
	 * @param v 
	 */
    public void addVendingMachine(IVendingMachine v);


    /**
	 * 
	 * @return the list of machines 
	 */
    public List<IVendingMachine> getMachines();


    /**
     * 
	 * sets the machine
     * @param machines 
	 */
    public void setMachines(List<IVendingMachine> machines);
 
}
