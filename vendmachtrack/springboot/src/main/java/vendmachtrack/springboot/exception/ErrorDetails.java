package vendmachtrack.springboot.exception;

import java.util.Date;

/**
 * Represents details of an error that occurred.
 * It encapsulates the timestamp when the error occurred, a brief error message,
 * and details about the error.
 * <p>
 * This class provides a structured way to convey error information, making it
 * easier to trace, handle and return errors to the client.
 * </p>
 */
class ErrorDetails {
    private final Date timestamp;
    private final String error;
    private final String details;

    /**
     * Constructs an {@code ErrorDetails} instance with the specified timestamp,
     * error message, and details.
     *
     * @param timestamp The date and time when the error occurred.
     * @param error     A brief message indicating the type of error.
     * @param details   Details of the error.
     */
    public ErrorDetails(final Date timestamp, final String error, final String details) {
        this.timestamp = new Date(timestamp.getTime());
        this.error = error;
        this.details = details;
    }

    /**
     * Retrieves the timestamp of when the error occurred.
     *
     * @return A {@code Date} object representing the timestamp of the error.
     */
    public Date getTimestamp() {
        return new Date(timestamp.getTime());
    }

    /**
     * Retrieves the brief error message.
     *
     * @return A string representing the error message.
     */
    public String getError() {
        return error;
    }

    /**
     * Retrieves the details of the error.
     *
     * @return A string containing the details of the error.
     */
    public String getDetails() {
        return details;
    }
}
