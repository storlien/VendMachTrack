package vendmachtrack.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalInputException extends RuntimeException {

    public IllegalInputException(String message) {
        super(message);
    }
}