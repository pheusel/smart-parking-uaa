package dhbw.smapa.uaa.exception;

public class InvalidBookingException extends RuntimeException {
    public InvalidBookingException() {
        super("Buchung ist bereits vollständig! Kein Update möglich!");
    }
    public InvalidBookingException(String message) {
        super(message);
    }
}
