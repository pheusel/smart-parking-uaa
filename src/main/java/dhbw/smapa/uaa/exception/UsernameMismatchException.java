package dhbw.smapa.uaa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UsernameMismatchException extends RuntimeException{

    public UsernameMismatchException() {
        super("Username does not match.");
    }
}
