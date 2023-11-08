package gr2338.vendmachtrack.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for providing a health check endpoint.
 * This can be used to verify the Spring Boot REST API server is up and running.
 */
@RestController
public class HealthController {

    /**
     * Provides a simple health check endpoint that responds with "Running"
     * to signify that the server is operational.
     *
     * @return ResponseEntity with the status message "Running" and HTTP status code OK
     */
    @GetMapping("/health")
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok("Running");
    }
}