package vendmachtrack.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vendmachtrack.core.MachineTracker;
import vendmachtrack.core.VendingMachine;
import vendmachtrack.springboot.exception.IllegalInputException;
import vendmachtrack.springboot.exception.ResourceNotFoundException;
import vendmachtrack.springboot.repository.MachineTrackerRepository;

import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Service layer responsible for managing the operations associated with
 * {@code Vending Machines}. It interacts with repositories, performs
 * validations,
 * and provides functionalities like fetching machine details, adding or
 * removing items,
 * and more.
 */
@Service
public class MachineTrackerService {

    private final MachineTrackerRepository repository;

    /**
     * Constructor injection of the MachineTrackerRepository dependency.
     *
     * @param repository the repository used for data storage interactions
     */
    @Autowired
    public MachineTrackerService(MachineTrackerRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves a list of all {@code Vending Machines} with their locations.
     *
     * @return A map containing machine IDs and their respective locations.
     * @throws ResourceNotFoundException if the Vending Machine Tracker is not
     *                                   found.
     */
    public HashMap<Integer, String> getVendMachList() {
        Optional<MachineTracker> machtrack = Optional.ofNullable(repository.getVendmachtrack());

        if (machtrack.isPresent()) {
            HashMap<Integer, String> vendMachList = new HashMap<>();

            for (VendingMachine vendMach : machtrack.get().getMachines()) {
                vendMachList.put(vendMach.getId(), vendMach.getLocation());
            }

            return vendMachList;
        } else {
            throw new ResourceNotFoundException("Vending Machine Tracker not found");
        }
    }

    /**
     * Fetches the location of a specific {@code Vending Machine} by its ID.
     *
     * @param id The ID of the Vending Machine.
     * @return The location of the Vending Machine with the given ID.
     * @throws ResourceNotFoundException if no Vending Machine with the specified ID
     *                                   exists.
     */
    public String getVendMachLocation(int id) {
        HashMap<Integer, String> vendMachList = getVendMachList();

        if (vendMachList.containsKey(id)) {
            return vendMachList.get(id);
        } else {
            throw new ResourceNotFoundException("No such Vending Machine with ID: " + id);
        }
    }

    /**
     * Retrieves the inventory status of a {@code Vending Machine} by its ID.
     *
     * @param id The ID of the Vending Machine.
     * @return A map of items and their quantities in the machine's inventory.
     * @throws ResourceNotFoundException if the Vending Machine Tracker is not
     *                                   found.
     */
    public HashMap<String, Integer> getInventory(int id) {
        return getVendMach(id).getStatus();
    }

    /**
     * Adds a specified quantity of a particular item to a {@code Vending Machine}
     * inventory.
     *
     * @param id       The ID of the Vending Machine.
     * @param item     The name of the item to add.
     * @param quantity The quantity of the item to add.
     * @return Updated inventory status of the Vending Machine.
     * @throws IllegalInputException     if the input values for item or quantity
     *                                   are invalid.
     * @throws ResourceNotFoundException if no Vending Machine with the specified ID
     *                                   exists.
     */
    public HashMap<String, Integer> addItem(int id, String item, int quantity) {
        validateItem(item);
        validateQuantity(quantity);
        validateQuantity(quantity);
        validateVendMachId(id);

        return repository.addItem(id, item, quantity).getStatus();
        return repository.addItem(id, item, quantity).getStatus();
    }

    /**
     * Removes a specified quantity of a particular item from a
     * {@code Vending Machine} inventory.
     *
     * @param id       The ID of the Vending Machine.
     * @param item     The name of the item to remove.
     * @param quantity The quantity of the item to remove.
     * @return Updated inventory status of the Vending Machine.
     * @throws IllegalInputException     if the input values for item or quantity
     *                                   are invalid or if the machine's inventory
     *                                   doesn't contain the specified item or not
     *                                   enough quantity of the item.
     * @throws ResourceNotFoundException if no Vending Machine with the specified ID
     *                                   exists.
     */
    public HashMap<String, Integer> removeItem(int id, String item, int quantity) {
        validateItem(item);
        validateQuantity(quantity);
        validateQuantity(quantity);
        validateVendMachId(id);

        VendingMachine vendMach = getVendMach(id);

        if (!vendMach.getStatus().containsKey(item)) {
            throw new IllegalInputException("The vending machine's inventory does not contain this item");
        } else if (quantity > vendMach.getStatus().get(item)) {
            throw new IllegalInputException(
                    "The vending machine's inventory contains less than the given quantity to remove of item: " + item);
        } else {
            return repository.removeItem(id, item, quantity).getStatus();
            return repository.removeItem(id, item, quantity).getStatus();
        }
    }

    /**
     * Adds a new {@code Vending Machine} with a specified ID and location.
     *
     * @param id       The ID of the new Vending Machine.
     * @param location The location of the new Vending Machine.
     * @return Updated list of Vending Machines and their locations.
     * @throws IllegalInputException if the input values for location are invalid or
     *                               if a Vending Machine with the given ID already
     *                               exists.
     */
    public HashMap<Integer, String> addVendMach(int id, String location) {
        validateNewVendMachId(id);
        validateLocation(location);
        repository.addVendMach(id, location);

        return getVendMachList();
    }

    /**
     * Removes a {@code Vending Machine} by its ID.
     *
     * @param id The ID of the Vending Machine to remove.
     * @return Updated list of Vending Machines and their locations.
     * @throws ResourceNotFoundException if no Vending Machine with the specified ID
     *                                   exists.
     */
    public HashMap<Integer, String> removeVendMach(int id) {
        validateVendMachId(id);
        repository.removeVendMach(id);

        return getVendMachList();
    }

    /**
     * Changes the location of an existing {@code Vending Machine}.
     *
     * @param id       The ID of the Vending Machine.
     * @param location The new location for the Vending Machine.
     * @return Updated list of Vending Machines and their locations.
     * @throws IllegalInputException     if the input values for location are
     *                                   invalid.
     * @throws ResourceNotFoundException if no Vending Machine with the specified ID
     *                                   exists.
     */
    public HashMap<Integer, String> changeLocation(int id, String location) {
        validateLocation(location);
        validateVendMachId(id);
        repository.changeLocation(id, location);

        return getVendMachList();
    }

    /**
     * Validates a given location string based on the specified format.
     * This method is for internal use.
     *
     * @param location The location string to validate.
     * @throws IllegalInputException if the location name is not valid.
     */
    private void validateLocation(String location) {
        if (!Pattern.compile("[A-ZÆØÅ][a-zæøå]*( [A-ZÆØÅ][a-zæøå]*)*").matcher(location).matches()) {
            throw new IllegalInputException("Location name not valid");
        }
    }

    /**
     * Validates the provided quantity to ensure it is greater than zero.
     * This method is for internal use.
     *
     * @param quantity The quantity to validate.
     * @throws IllegalInputException if the quantity is less than one.
     */
    private void validateQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalInputException("Quantity has to be higher than zero");
        }
    }

    /**
     * Validates a given item string to ensure it matches the required format.
     * This method is for internal use.
     *
     * @param item The item string to validate.
     * @throws IllegalInputException if the item name is not valid.
     */
    private void validateItem(String item) {
        if (!Pattern.compile("\\S(.*\\S)?").matcher(item).matches()) {
            throw new IllegalInputException("Item name not valid");
        }
    }

    /**
     * Validates a given Vending Machine ID to ensure it exists.
     * This method is for internal use.
     *
     * @param id The Vending Machine ID to validate.
     * @throws ResourceNotFoundException if no Vending Machine with the specified ID
     *                                   exists.
     */
    private void validateVendMachId(int id) {
        Optional<VendingMachine> vendMach = Optional.ofNullable(repository.getVendMach(id));

        if (vendMach.isEmpty()) {
            throw new ResourceNotFoundException("No such Vending Machine with ID: " + id);
        }
    }

    /**
     * Validates a new Vending Machine ID to ensure it doesn't already exist and
     * follows the correct format.
     * This method is for internal use.
     *
     * @param id The new Vending Machine ID to validate.
     * @throws IllegalInputException if a Vending Machine with the given ID already
     *                               exists or if the ID format is invalid.
     */
    private void validateNewVendMachId(int id) {
        Optional<VendingMachine> vendMach = Optional.ofNullable(repository.getVendMach(id));

        if (vendMach.isPresent()) {
            throw new IllegalInputException("A vending machine with id " + id + " already exists");
        } else if (!Pattern.compile("\\d+").matcher(String.valueOf(id)).matches()) {
            throw new IllegalInputException("Id not valid");
        }
    }

    /**
     * Retrieves a {@code VendingMachine} instance by its ID after validating its
     * existence.
     * This method is for internal use.
     *
     * @param id The ID of the Vending Machine.
     * @return The {@code VendingMachine} instance with the specified ID.
     * @throws ResourceNotFoundException if no Vending Machine with the specified ID
     *                                   exists.
     */
    private VendingMachine getVendMach(int id) {
        validateVendMachId(id);
        return repository.getVendMach(id);
    }
}
