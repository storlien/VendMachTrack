package gr2338.vendmachtrack.ui.access;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import gr2338.vendmachtrack.jsonio.VendmachtrackPersistence;

/**
 * The AccessService class provides access to the MachineTracker system either
 * through a remote server or a local file,
 * based on the provided URI and file name.
 */
public class AccessService {

    private final MachineTrackerAccessible access;
    private static final int TIMEOUT_SECONDS = 5;


    /**
     * Constructor for AccessService class. Initializes the access method (remote or
     * local) based on server health check.
     *
     * @param endpointUri The URI of the server endpoint.
     * @param fileName    The name of the local file to be accessed in case the
     *                    server is not available.
     */
    public AccessService(final URI endpointUri, final String fileName) {

        MachineTrackerAccessible newAccess;

        try {
            if (checkServerHealth(endpointUri)) {
                newAccess = new MachineTrackerAccessRemote(endpointUri);
                System.out.println("Using remote access");
            } else {
                newAccess = new MachineTrackerAccessLocal(new VendmachtrackPersistence(fileName));
                System.out.println("Using local access");
            }
        } catch (IOException | InterruptedException e) {
            newAccess = new MachineTrackerAccessLocal(new VendmachtrackPersistence(fileName));
            System.out.println("Using local access");
        }

        this.access = newAccess;

    }

    /**
     * Checks the health of the server by sending a GET request to the server's
     * health endpoint.
     *
     * @param endpointUri The URI of the server endpoint.
     * @return True if the server is healthy and responds with a 200 status code,
     * false otherwise.
     * @throws IOException          If an I/O error occurs while sending the
     *                              request.
     * @throws InterruptedException If the operation is interrupted.
     */
    private boolean checkServerHealth(final URI endpointUri) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointUri.resolve("health"))
                .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpURLConnection.HTTP_OK;
    }

    /**
     * Retrieves the access method (remote or local) currently being used.
     *
     * @return The MachineTrackerAccessible interface implementation representing
     * the access method.
     */
    public MachineTrackerAccessible getAccess() {
        return access;
    }

}