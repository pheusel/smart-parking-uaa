package dhbw.smapa.uaa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailTakenException extends RuntimeException {

    public EmailTakenException(String email) {
        super("email " + email + " is already taken.");
    }
}
