package ui.access;

import java.util.HashMap;

public interface IMachineTrackerAccess {

    HashMap<Integer, String> getVendMachList();

    String getVendMachLocation(int id);

    HashMap<String, Integer> getInventory(int id);

    HashMap<String, Integer> addItem(int id, String item, int number);

    HashMap<String, Integer> removeItem(int id, int number);

    HashMap<String, Integer> addVendMach(int id, String location);

    HashMap<Integer, String> removeVendMach(int id);

    HashMap<Integer, String> changeLocation(int id, String location);
}