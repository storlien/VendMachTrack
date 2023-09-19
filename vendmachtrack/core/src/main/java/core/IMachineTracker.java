package core;

public interface IMachineTracker{

    public void removeVendingMachine(IVendingMachine v, String location);
    public void addVendingMachine(IVendingMachine v, String location);
 
}
