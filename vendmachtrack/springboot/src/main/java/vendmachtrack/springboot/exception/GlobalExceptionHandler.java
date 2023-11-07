package vendmachtrack.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Represents a global exception handler that catches exceptions thrown
 * across the entire Spring Boot server application.
 * <p>
 * This handler provides centralized logic for handling specific exceptions and returning relevant HTTP
 * status codes and error details to the client. Using the {@code @ControllerAdvice} annotation, it applies
 * to all controllers throughout the server application.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the {@code ResourceNotFoundException} by creating a detailed error response and returning
     * an HTTP status code of {@code NOT_FOUND}.
     *
     * @param ex      the exception that was caught.
     * @param request the current web request.
     * @return a structured error response containing a timestamp, the error message, and a description.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(final ResourceNotFoundException ex, final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@code IllegalInputException} by creating a detailed error response and returning
     * an HTTP status code of {@code BAD_REQUEST}.
     *
     * @param ex      the exception that was caught.
     * @param request the current web request.
     * @return a structured error response containing a timestamp, the error message, and a description.
     */
    @ExceptionHandler(IllegalInputException.class)
    public ResponseEntity<?> illegalInputException(final IllegalInputException ex, final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
