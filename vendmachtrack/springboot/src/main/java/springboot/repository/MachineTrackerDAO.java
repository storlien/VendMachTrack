package springboot.repository;

import core.MachineTracker;

public interface MachineTrackerDAO {
    MachineTracker getMachtrack();

    MachineTracker saveMachtrack();
}
