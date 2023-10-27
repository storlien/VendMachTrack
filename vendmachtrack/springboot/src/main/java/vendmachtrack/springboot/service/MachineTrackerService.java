package vendmachtrack.springboot.service;

import vendmachtrack.core.MachineTracker;
import vendmachtrack.core.VendingMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vendmachtrack.springboot.exception.IllegalInputException;
import vendmachtrack.springboot.exception.ResourceNotFoundException;
import vendmachtrack.springboot.repository.MachineTrackerRepository;

import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class MachineTrackerService {


    private final MachineTrackerRepository repository;

    @Autowired
    public MachineTrackerService(MachineTrackerRepository repository) {
        this.repository = repository;
    }

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

    public String getVendMachLocation(int id) {
        HashMap<Integer, String> vendMachList = getVendMachList();

        if (vendMachList.containsKey(id)) {
            return vendMachList.get(id);
        } else {
            throw new ResourceNotFoundException("No such Vending Machine with ID: " + id);
        }
    }

    public HashMap<String, Integer> getInventory(int id) {
        return getVendMach(id).getStatus();
    }

    public HashMap<String, Integer> addItem(int id, String item, int amount) {
        validateItem(item);
        validateAmount(amount);
        validateVendMachId(id);

        return repository.addItem(id, item, amount).getStatus();
    }

    public HashMap<String, Integer> removeItem(int id, String item, int amount) {
        validateItem(item);
        validateAmount(amount);
        validateVendMachId(id);

        VendingMachine vendMach = getVendMach(id);

        if (!vendMach.getStatus().containsKey(item)) {
            throw new IllegalInputException("The vending machine's inventory does not contain this item");
        } else if (amount > vendMach.getStatus().get(item)) {
            throw new IllegalInputException("The vending machine's inventory contains less than the given amount to remove of item: " + item);
        } else {
            return repository.removeItem(id, item, amount).getStatus();
        }
    }

    public HashMap<Integer, String> addVendMach(int id, String location) {
        validateNewVendMachId(id);
        validateLocation(location);
        repository.addVendMach(id, location);

        return getVendMachList();
    }

    public HashMap<Integer, String> removeVendMach(int id) {
        validateVendMachId(id);
        repository.removeVendMach(id);

        return getVendMachList();
    }

    public HashMap<Integer, String> changeLocation(int id, String location) {
        validateLocation(location);
        validateVendMachId(id);
        repository.changeLocation(id, location);

        return getVendMachList();
    }

    private void validateLocation(String location) {
        if (!Pattern.compile("[A-ZÆØÅ][a-zæøå]*( [A-ZÆØÅ][a-zæøå]*)*").matcher(location).matches()) {
            throw new IllegalInputException("Location name not valid");
        }
    }

    private void validateAmount(int amount) {
        if (amount < 1) {
            throw new IllegalInputException("Amount has to be higher than zero");
        }
    }

    private void validateItem(String item) {
        if (!Pattern.compile("\\S(.*\\S)?").matcher(item).matches()) {
            throw new IllegalInputException("Item name not valid");
        }
    }

    private void validateVendMachId(int id) {
        Optional<VendingMachine> vendMach = Optional.ofNullable(repository.getVendMach(id));

        if (vendMach.isEmpty()) {
            throw new ResourceNotFoundException("No such Vending Machine with ID: " + id);
        }
    }

    private void validateNewVendMachId(int id) {
        Optional<VendingMachine> vendMach = Optional.ofNullable(repository.getVendMach(id));

        if (vendMach.isPresent()) {
            throw new IllegalInputException("A vending machine with id " + id + " already exists");
        } else if (!Pattern.compile("\\d+").matcher(String.valueOf(id)).matches()) {
            throw new IllegalInputException("Id not valid");
        }
    }

    private VendingMachine getVendMach(int id) {
        validateVendMachId(id);
        return repository.getVendMach(id);
    }
}
