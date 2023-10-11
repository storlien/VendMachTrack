package core;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

public class MachineTrackerTest {

    private MachineTracker tracker;

    @BeforeEach
    public void setUp() {
        tracker = new MachineTracker();
    }

    @Test
    public void testAddMachine() {
        VendingMachine machine = new core.VendingMachine(1, new HashMap<>(), "location");
        tracker.addVendingMachine(machine);
        assertEquals(1, tracker.getMachines().size());
    }

    @Test
    public void testAddMachineTwice() {
        VendingMachine machine = new core.VendingMachine(1, new HashMap<>(), "location");
        tracker.addVendingMachine(machine);
        assertThrows(IllegalArgumentException.class, () -> tracker.addVendingMachine(machine));
    }
}
