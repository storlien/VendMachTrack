package vendmachtrack.ui.access;

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
 * Accesses the Spring Boot REST API server by following the documentation on
 * possible requests to perform against the API.
 */
public class MachineTrackerAccessRemote implements MachineTrackerAccessible {

    private final URI endpointBaseUri;
    private final Gson gson;
    private final HttpClient httpClient;

    private static final String ENDPOINT_DIRECTORY = "vendmachtrack";

    private static final Type TYPE_HASHMAP_INTEGER_STRING = new TypeToken<HashMap<Integer, String>>() { }.getType();
    private static final Type TYPE_HASHMAP_STRING_INTEGER = new TypeToken<HashMap<String, Integer>>() { }.getType();

    /**
     * Constructor. Requires a base URI for the REST API endpoint.
     *
     * @param endpointBaseUri The base URI of the REST API endpoint
     */
    public MachineTrackerAccessRemote(final URI endpointBaseUri) {
        this.endpointBaseUri = endpointBaseUri; // Use 'this' to refer to the class field
        this.gson = new Gson();
        this.httpClient = HttpClient.newBuilder().build();
    }

    /**
     * Access method for the list of vending machines
     *
     * @return HashMap of vending machine list with vending machine ID as key and
     * location as value
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
    public String getVendMachLocation(final int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve("vendmachtrack/" + id + "/name"))
                .build();

        HttpResponse<String> response = getResponse(request);
        checkError(response);

        return gson.fromJson(response.body(), String.class);
    }

    /**
     * Access method for inventory
     *
     * @param id The ID of the vending machine
     * @return HashMap of inventory with item as key and as value
     */
    @Override
    public HashMap<String, Integer> getInventory(final int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve("vendmachtrack/" + id))
                .build();

        HttpResponse<String> response = getResponse(request);
        checkError(response);

        return gson.fromJson(response.body(), TYPE_HASHMAP_STRING_INTEGER);
    }

    /**
     * Access method for adding item to vending machine's inventory
     *
     * @param id       The ID of the vending machine
     * @param item     The item name to be added
     * @param quantity The quantity of the item to be added
     * @return HashMap of inventory with item as key and quantity as value
     */
    @Override
    public HashMap<String, Integer> addItem(final int id, final String item, final int quantity) {

        String endpointQuery = buildEndpointWithParams(ENDPOINT_DIRECTORY + "/" + id + "/add",
         "item", item, "quantity", String.valueOf(quantity));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve(endpointQuery))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = getResponse(request);
        checkError(response);

        return gson.fromJson(response.body(), TYPE_HASHMAP_STRING_INTEGER);
    }

    /**
     * Access method for removing a quantity of an item
     *
     * @param id       The ID of the vending machine
     * @param item     The item to be removed
     * @param quantity The quantity to be removed from the item
     * @return HashMap of inventory with item as key and quantity as value
     */
    @Override
    public HashMap<String, Integer> removeItem(final int id, final String item, final int quantity) {

        String endpointQuery = buildEndpointWithParams(ENDPOINT_DIRECTORY + "/" + id + "/remove",
        "item", item, "quantity", String.valueOf(quantity));

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
     * @return HashMap of vending machine list with vending machine ID as key and
     * location as value
     */
    @Override
    public HashMap<Integer, String> addVendMach(final int id, final String location) {

        String endpointQuery = buildEndpointWithParams(ENDPOINT_DIRECTORY + "/add",
         "id", String.valueOf(id), "location", location);

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
     * @return HashMap of vending machine list with vending machine ID as key and
     * location as value
     */
    @Override
    public HashMap<Integer, String> removeVendMach(final int id) {

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
     * @return HashMap of vending machine list with vending machine ID as key and
     * location as value
     */
    @Override
    public HashMap<Integer, String> changeLocation(final int id, final String location) {

        String endpointQuery = buildEndpointWithParams(ENDPOINT_DIRECTORY + "/" + id, "location", location);

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
     * If not, it will throw a RunTimeExceptionError with the message being the
     * error message from the HTTP response.
     *
     * @param response HttpReponse to be checked
     */
    private void checkError(final HttpResponse<String> response) {
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
    private HttpResponse<String> getResponse(final HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructs an endpoint URL by appending parameters to the base URL.
     * Parameters are provided as varargs, with key-value pairs in sequence.
     *
     * @param base   The base URL to which the parameters should be appended.
     * @param params An array of strings representing key-value pairs. Must be even-numbered.
     *               For instance, ["key1", "value1", "key2", "value2"].
     * @return A string representing the constructed URL with the provided parameters.
     */
    private String buildEndpointWithParams(final String base, final String... params) {
        StringBuilder endpoint = new StringBuilder(base);
        if (params.length > 0) {
            endpoint.append("?");
            for (int i = 0; i < params.length; i += 2) {
                if (i > 0) {
                    endpoint.append("&");
                }
                endpoint.append(URLEncoder.encode(params[i], StandardCharsets.UTF_8))
                        .append("=")
                        .append(URLEncoder.encode(params[i + 1], StandardCharsets.UTF_8));
            }
        }
        return endpoint.toString();
    }
}
