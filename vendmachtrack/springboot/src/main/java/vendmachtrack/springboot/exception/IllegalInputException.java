package vendmachtrack.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Represents an exception thrown when provided input to an endpoint is illegal
 * or in an unexpected format.
 * <p>
 * This exception integrates with the {@link GlobalExceptionHandler} to
 * automatically
 * respond with an HTTP {@code BAD_REQUEST} status and provide relevant error
 * details to the client.
 * </p>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalInputException extends RuntimeException {

    /**
     * Constructs a new exception with the specified error message.
     *
     * @param message the detail message, providing the reason the exception was
     *                thrown.
     */
    public IllegalInputException(String message) {
        super(message);
    }
}