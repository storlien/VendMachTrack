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

public class MachineTrackerAccessRemote implements IMachineTrackerAccess {

    private final URI endpointBaseUri;
    private final Gson gson;

    private final Type TYPE_HASHMAP_INTEGER_STRING = new TypeToken<HashMap<Integer, String>>() {
    }.getType();
    private final Type TYPE_HASHMAP_STRING_INTEGER = new TypeToken<HashMap<String, Integer>>() {
    }.getType();

    public MachineTrackerAccessRemote(URI endpointBaseUri) {
        this.endpointBaseUri = endpointBaseUri;
        this.gson = new Gson();
    }

    @Override
    public HashMap<Integer, String> getVendMachList() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve("vendmachtrack"))
                .build();

        HttpResponse<String> response = getResponse(request);
        checkError(response);

        return gson.fromJson(response.body(), TYPE_HASHMAP_INTEGER_STRING);
    }

    @Override
    public String getVendMachLocation(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve("vendmachtrack/" + String.valueOf(id) + "/name"))
                .build();

        HttpResponse<String> response = getResponse(request);
        checkError(response);

        return gson.fromJson(response.body(), String.class);
    }

    @Override
    public HashMap<String, Integer> getInventory(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve("vendmachtrack/" + String.valueOf(id)))
                .build();

        HttpResponse<String> response = getResponse(request);
        checkError(response);

        return gson.fromJson(response.body(), TYPE_HASHMAP_STRING_INTEGER);
    }

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

    private void checkError(HttpResponse<String> response) {
        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            JsonElement rootElement = JsonParser.parseString(response.body());
            JsonObject rootObject = rootElement.getAsJsonObject();

            throw new RuntimeException(rootObject.get("error").getAsString());
        }
    }

    private HttpResponse<String> getResponse(HttpRequest request) {
        try {
            return HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
