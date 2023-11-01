package vendmachtrack.springboot.exception;

import java.util.Date;

class ErrorDetails {
    private final Date timestamp;
    private final String error;
    private final String details;

    public ErrorDetails(Date timestamp, String error, String details) {
        this.timestamp = new Date(timestamp.getTime());
        this.error = error;
        this.details = details;
    }

    public Date getTimestamp() {
        return new Date(timestamp.getTime());
    }

    public String getError() {
        return error;
    }

    public String getDetails() {
        return details;
    }

}