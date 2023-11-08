package gr2338.vendmachtrack.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Represents an exception thrown when a requested resource is not found.
 * <p>
 * This exception integrates with the {@link GlobalExceptionHandler} to automatically
 * respond with an HTTP {@code NOT_FOUND} status and provide relevant error details to the client.
 * </p>
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new exception with the specified error message.
     *
     * @param message the detail message, providing the reason the exception was thrown.
     */
    public ResourceNotFoundException(final String message) {
        super(message);
    }

}