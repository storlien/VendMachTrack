package core;

import org.junit.jupiter.api.Test;

import core.MachineTracker;
import core.IVendingMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

class MachineTrackerTest {

    private MachineTracker tracker;

    @BeforeEach
    void setUp() {
        tracker = new MachineTracker();
    }

    @Test
    void testAddMachine() {
        VendingMachine machine = new core.VendingMachine(1, new HashMap<>(), "location");
        tracker.addVendingMachine(machine);
        assertEquals(1, tracker.getMachines().size());
    }

    @Test
    void testAddMachineTwice() {
        VendingMachine machine = new core.VendingMachine(1, new HashMap<>(), "location");
        tracker.addVendingMachine(machine);
        assertThrows(IllegalArgumentException.class, () -> tracker.addVendingMachine(machine));
    }
}
