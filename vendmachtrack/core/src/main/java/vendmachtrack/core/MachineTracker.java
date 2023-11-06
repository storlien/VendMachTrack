package vendmachtrack.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of vending machines and provides methods to manage
 * them.
 * <p>
 * This class encapsulates a list of {@link VendingMachine} objects and offers
 * functionalities to
 * add, remove, and retrieve them. The MachineTracker ensures that each vending
 * machine is
 * unique within its list and throws an exception if any operation violates this
 * constraint.
 * </p>
 */
public class MachineTracker {

  /**
   * List holding the vending machines managed by this tracker.
   */
  private List<VendingMachine> machines = new ArrayList<>();

  /**
   * Removes the specified vending machine from the machine tracker.
   *
   * @param v The vending machine to be removed from the tracker.
   * @throws IllegalArgumentException If the machine is not found in the tracker.
   */
  public void removeVendingMachine(final VendingMachine v) {
    if (!machines.contains(v)) {
      throw new IllegalArgumentException(
          "The specified vending machine is not part of this tracker");
    } else {
      machines.remove(v);
    }
  }

  /**
   * Adds the specified vending machine to the machine tracker.
   * <p>
   * The method ensures that each vending machine in the tracker is unique.
   * 
   * @param v The vending machine to be added to the tracker.
   * @throws IllegalArgumentException If the vending machine is already in the
   *                                  tracker.
   */
  public void addVendingMachine(final VendingMachine v) {
    if (machines.contains(v)) {
      throw new IllegalArgumentException(
          "The vending machine is already part of this tracker");
    } else {
      machines.add(v);
    }
  }

  /**
   * Retrieves a defensive copy of the list of vending machines in the tracker.
   * <p>
   * This ensures that external modifications to the returned list won't affect
   * the internal state
   * of the tracker.
   *
   * @return A list containing the vending machines in the tracker.
   */
  public List<VendingMachine> getMachines() {
    return new ArrayList<>(machines);
  }

  /**
   * Sets the vending machines managed by this tracker.
   * <p>
   * The method takes a defensive copy of the provided list, ensuring the internal
   * state is protected from external modifications.
   *
   * @param newMachines The list of vending machines to be managed by the tracker.
   */
  public void setMachines(final List<VendingMachine> newMachines) {
    this.machines = new ArrayList<>(newMachines);
  }
}
