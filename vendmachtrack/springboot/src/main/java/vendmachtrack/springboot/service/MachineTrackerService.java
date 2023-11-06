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
    public MachineTrackerService(final MachineTrackerRepository repository) {
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

    public String getVendMachLocation(final int id) {
        HashMap<Integer, String> vendMachList = getVendMachList();

        if (vendMachList.containsKey(id)) {
            return vendMachList.get(id);
        } else {
            throw new ResourceNotFoundException("No such Vending Machine with ID: " + id);
        }
    }

    public HashMap<String, Integer> getInventory(final int id) {
        return getVendMach(id).getStatus();
    }

    public HashMap<String, Integer> addItem(final int id, final String item, final int quantity) {
        validateItem(item);
        validateQuantity(quantity);
        validateQuantity(quantity);
        validateVendMachId(id);

        return repository.addItem(id, item, quantity).getStatus();
    }

    public HashMap<String, Integer> removeItem(final int id, final String item, final int quantity) {
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
        }
    }

    public HashMap<Integer, String> addVendMach(final int id, final String location) {
        validateNewVendMachId(id);
        validateLocation(location);
        repository.addVendMach(id, location);

        return getVendMachList();
    }

    public HashMap<Integer, String> removeVendMach(final int id) {
        validateVendMachId(id);
        repository.removeVendMach(id);

        return getVendMachList();
    }

    public HashMap<Integer, String> changeLocation(final int id, final String location) {
        validateLocation(location);
        validateVendMachId(id);
        repository.changeLocation(id, location);

        return getVendMachList();
    }

    private void validateLocation(final String location) {
        if (!Pattern.compile("[A-ZÆØÅ][a-zæøå]*( [A-ZÆØÅ][a-zæøå]*)*").matcher(location).matches()) {
            throw new IllegalInputException("Location name not valid");
        }
    }

    private void validateQuantity(final int quantity) {
        if (quantity < 1) {
            throw new IllegalInputException("Quantity has to be higher than zero");
        }
    }

    private void validateItem(final String item) {
        if (!Pattern.compile("\\S(.*\\S)?").matcher(item).matches()) {
            throw new IllegalInputException("Item name not valid");
        }
    }

    private void validateVendMachId(final int id) {
        Optional<VendingMachine> vendMach = Optional.ofNullable(repository.getVendMach(id));

        if (vendMach.isEmpty()) {
            throw new ResourceNotFoundException("No such Vending Machine with ID: " + id);
        }
    }

    private void validateNewVendMachId(final int id) {
        Optional<VendingMachine> vendMach = Optional.ofNullable(repository.getVendMach(id));

        if (vendMach.isPresent()) {
            throw new IllegalInputException("A vending machine with id " + id + " already exists");
        } else if (!Pattern.compile("\\d+").matcher(String.valueOf(id)).matches()) {
            throw new IllegalInputException("Id not valid");
        }
    }

    private VendingMachine getVendMach(final int id) {
        validateVendMachId(id);
        return repository.getVendMach(id);
    }

}