package dhbw.smapa.uaa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class JWTValidationException extends RuntimeException {

    public JWTValidationException() {
        super("Expired or invalid JWT token.");
    }
}
