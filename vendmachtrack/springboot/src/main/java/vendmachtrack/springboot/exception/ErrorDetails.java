package vendmachtrack.springboot.exception;

import java.util.Date;

class ErrorDetails {
    private final Date timestamp;
    private final String error;
    private final String details;

    ErrorDetails(final Date timestampParam, final String errorParam, final String detailsParam) {
        this.timestamp = new Date(timestampParam.getTime());
        this.error = errorParam;
        this.details = detailsParam;
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
