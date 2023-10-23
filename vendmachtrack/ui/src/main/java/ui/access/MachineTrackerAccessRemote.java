package ui.access;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class MachineTrackerAccessRemote implements IMachineTrackerAccess {

    @Override
    public HashMap<Integer, String> getVendMachList() {
        return null;
    }

    @Override
    public String getVendMachLocation(int id) {
        return null;
    }

    @Override
    public HashMap<String, Integer> getInventory(int id) {
        return null;
    }

    @Override
    public HashMap<String, Integer> addItem(int id, String item, int amount) {
        return null;
    }

    @Override
    public HashMap<String, Integer> removeItem(int id, String item, int amount) {
        return null;
    }

    @Override
    public HashMap<Integer, String> addVendMach(int id, String location) {
        return null;
    }

    @Override
    public HashMap<Integer, String> removeVendMach(int id) {
        return null;
    }

    @Override
    public HashMap<Integer, String> changeLocation(int id, String location) {
        return null;
    }
}
