package ui.access;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Class for accessing Vending Machine Tracker through REST API.
 * Accesses the Spring Boot REST API server by following the documentation on possible requests to perform against the API.
 */
public class MachineTrackerAccessRemote implements MachineTrackerAccessible {

    private final URI endpointBaseUri;
    private final Gson gson;

    private final Type TYPE_HASHMAP_INTEGER_STRING = new TypeToken<HashMap<Integer, String>>() {
    }.getType();
    private final Type TYPE_HASHMAP_STRING_INTEGER = new TypeToken<HashMap<String, Integer>>() {
    }.getType();

    /**
     * Constructor. Requires a base URI for the REST API endpoint.
     *
     * @param endpointBaseUri The base URI of the REST API endpoint
     */
    public MachineTrackerAccessRemote(URI endpointBaseUri) {
        this.endpointBaseUri = endpointBaseUri;
        this.gson = new Gson();
    }

    /**
     * Access method for the list of vending machines
     *
     * @return HashMap of vending machine list with vending machine ID as key and location as value
     */
    @Override
    public HashMap<Integer, String> getVendMachList() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve("vendmachtrack"))
                .build();

        HttpResponse<String> response = getResponse(request);
        checkError(response);

        return gson.fromJson(response.body(), TYPE_HASHMAP_INTEGER_STRING);
    }

    /**
     * Access method for the location of a vending machine
     *
     * @param id The ID of the vending machine
     * @return Location of vending machine
     */
    @Override
    public String getVendMachLocation(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve("vendmachtrack/" + String.valueOf(id) + "/name"))
                .build();

        HttpResponse<String> response = getResponse(request);
        checkError(response);

        return gson.fromJson(response.body(), String.class);
    }

    /**
     * Access method for inventory
     *
     * @param id The ID of the vending machine
     * @return HashMap of inventory with item as key and amount as value
     */
    @Override
    public HashMap<String, Integer> getInventory(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve("vendmachtrack/" + String.valueOf(id)))
                .build();

        HttpResponse<String> response = getResponse(request);
        checkError(response);

        return gson.fromJson(response.body(), TYPE_HASHMAP_STRING_INTEGER);
    }

    /**
     * Access method for adding item to vending machine's inventory
     *
     * @param id     The ID of the vending machine
     * @param item   The item name to be added
     * @param amount The amount of the item to be added
     * @return HashMap of inventory with item as key and amount as value
     */
    @Override
    public HashMap<String, Integer> addItem(int id, String item, int amount) {
        String param1Value = URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8);
        String param2Key = "item";
        String param2Value = URLEncoder.encode(item, StandardCharsets.UTF_8);
        String param3Key = "amount";
        String param3Value = URLEncoder.encode(String.valueOf(amount), StandardCharsets.UTF_8);

        String endpointQuery = String.format("vendmachtrack/%s/add?%s=%s&%s=%s", param1Value, param2Key, param2Value, param3Key, param3Value);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve(endpointQuery))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = getResponse(request);
        checkError(response);

        return gson.fromJson(response.body(), TYPE_HASHMAP_STRING_INTEGER);
    }

    /**
     * Access method for removing an amount of an item
     *
     * @param id     The ID of the vending machine
     * @param item   The item to be removed
     * @param amount The amount to be removed from the item
     * @return HashMap of inventory with item as key and amount as value
     */
    @Override
    public HashMap<String, Integer> removeItem(int id, String item, int amount) {
        String param1Value = URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8);
        String param2Key = "item";
        String param2Value = URLEncoder.encode(item, StandardCharsets.UTF_8);
        String param3Key = "amount";
        String param3Value = URLEncoder.encode(String.valueOf(amount), StandardCharsets.UTF_8);

        String endpointQuery = String.format("vendmachtrack/%s/remove?%s=%s&%s=%s", param1Value, param2Key, param2Value, param3Key, param3Value);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve(endpointQuery))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = getResponse(request);
        checkError(response);

        return gson.fromJson(response.body(), TYPE_HASHMAP_STRING_INTEGER);
    }

    /**
     * Access method for adding a new vending machine
     *
     * @param id       The ID of the new vending machine
     * @param location The location of the new vending machine
     * @return HashMap of vending machine list with vending machine ID as key and location as value
     */
    @Override
    public HashMap<Integer, String> addVendMach(int id, String location) {
        String param1Key = "id";
        String param1Value = URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8);
        String param2Key = "location";
        String param2Value = URLEncoder.encode(location, StandardCharsets.UTF_8);

        String endpointQuery = String.format("vendmachtrack/add?%s=%s&%s=%s", param1Key, param1Value, param2Key, param2Value);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve(endpointQuery))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = getResponse(request);
        checkError(response);

        return gson.fromJson(response.body(), TYPE_HASHMAP_INTEGER_STRING);
    }

    /**
     * Access method for removing a vending machine
     *
     * @param id The ID of the vending machine
     * @return HashMap of vending machine list with vending machine ID as key and location as value
     */
    @Override
    public HashMap<Integer, String> removeVendMach(int id) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve("vendmachtrack/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = getResponse(request);
        checkError(response);

        return gson.fromJson(response.body(), TYPE_HASHMAP_INTEGER_STRING);
    }

    /**
     * Access method for changing the location of a vending machine
     *
     * @param id       The ID of the vending machine
     * @param location The new location of the vending machine
     * @return HashMap of vending machine list with vending machine ID as key and location as value
     */
    @Override
    public HashMap<Integer, String> changeLocation(int id, String location) {
        String param1Key = "location";
        String param1Value = URLEncoder.encode(location, StandardCharsets.UTF_8);

        String endpointQuery = String.format("vendmachtrack/" + id + "?%s=%s", param1Key, param1Value);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve(endpointQuery))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = getResponse(request);
        checkError(response);

        return gson.fromJson(response.body(), TYPE_HASHMAP_INTEGER_STRING);
    }

    /**
     * Internal method for checking if the response was successful or not.
     * If not, it will throw a RunTimeExceptionError with the message being the error message from the HTTP response.
     *
     * @param response HttpReponse to be checked
     */
    private void checkError(HttpResponse<String> response) {
        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            JsonElement rootElement = JsonParser.parseString(response.body());
            JsonObject rootObject = rootElement.getAsJsonObject();

            throw new RuntimeException(rootObject.get("error").getAsString());
        }
    }

    /**
     * Internal method for sending the request and returning the response.
     *
     * @param request HttpRequest to be sent
     * @return HttpReponse from the request sent
     */
    private HttpResponse<String> getResponse(HttpRequest request) {
        try {
            return HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
