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
        IVendingMachine machine = new VendingMachine();
        tracker.addVendingMachine(machine);
        assertEquals(1, tracker.getMachines().size());
    }

}
