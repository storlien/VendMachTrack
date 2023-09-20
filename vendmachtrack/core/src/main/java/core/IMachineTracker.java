package core;

import java.util.List;

public interface IMachineTracker{

    public void removeVendingMachine(IVendingMachine v);
    public void addVendingMachine(IVendingMachine v);
    public List<IVendingMachine> getMachines();
 
}
