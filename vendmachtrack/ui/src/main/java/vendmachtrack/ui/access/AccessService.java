package vendmachtrack.ui.access;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import vendmachtrack.jsonio.VendmachtrackPersistence;

public class AccessService {

    private MachineTrackerAccessible access;

    public AccessService(URI endpointUri, String fileName) {

        try {
            if (checkServerHealth(endpointUri)) {
                access = new MachineTrackerAccessRemote(endpointUri);
                System.out.println("Using remote access");
            } else {
                access = new MachineTrackerAccessLocal(new VendmachtrackPersistence(fileName));
                System.out.println("Using local access");
            }
        } catch (Exception e) {
            System.out.println("Error during server health check: " + e);
            access = new MachineTrackerAccessLocal(new VendmachtrackPersistence(fileName));
            System.out.println("Using local access as a fallback");
        }

    }

    public boolean checkServerHealth(URI endpointUri) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointUri.resolve("health"))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpURLConnection.HTTP_OK;
    }

    public MachineTrackerAccessible getAccess() {
        return access;
    }

}