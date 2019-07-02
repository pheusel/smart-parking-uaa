package dhbw.smapa.uaa.exception;

public class BookingNotFoundException extends RuntimeException {

    public BookingNotFoundException() {
        super("Keine Buchung für diesen Parkplatz vorhanden!");
    }
}
