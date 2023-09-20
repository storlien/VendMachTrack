package core;

import java.util.ArrayList;
import java.util.List;


public class MachineTracker implements IMachineTracker{


    private List<IVendingMachine> machines = new ArrayList<>();



    @Override
    public void removeVendingMachine(IVendingMachine v) {
        if (machines.contains(v)){
            machines.remove(v);
        }
        
    }

    @Override
    public void addVendingMachine(IVendingMachine v) {
        if (!machines.contains(v)){
            machines.add(v);
        }
    }

    public List<IVendingMachine> getMachines() { //evt legge til i interface
        return new ArrayList<>(machines);
    }


    



    
    
}
