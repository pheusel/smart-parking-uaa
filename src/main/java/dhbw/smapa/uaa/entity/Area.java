package dhbw.smapa.uaa.entity;

import dhbw.smapa.uaa.exception.InvalidBookingException;
import java.util.Date;

public enum Area {

    A(2.0),
    B(1.8),
    C(1.6),
    D(1.6),
    E(1.6),
    F(2.0),
    G(1.4);


    private Double price;
    private static Long DURATION_HOUR = 3600000L;
    private static Long DURATION_DAY = 86400000L;

    Area(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return this.price;
    }

    public static Area getArea(String area) {
        switch (area) {
            case "A":
                return A;
            case "B":
                return B;
            case "C":
                return C;
            case "D":
                return D;
            case "E":
                return E;
            case "F":
                return F;
            case "G":
                return G;
            default:
                return null;
        }
    }

    public Double getBookingCost(Booking booking) {
        if (booking.getParkingStart() == null) {
            throw new InvalidBookingException("Fehlerhaftes Booking-Objekt gesendet! Kein Startzeitpunkt gesetzt!");
        }

        Date start = booking.getParkingStart();
        //Starttag um 24:00 Uhr
        Date startDayEnd = new Date(((DURATION_DAY - (start.getTime() % DURATION_DAY)) + start.getTime()));

        //Starttag um 8:00 Uhr (Beginn des kostenpflichtigen Parkens)
        Date startDayMorning = new Date(startDayEnd.getTime() - (DURATION_HOUR * 16));

        //Starttag um 20:00 Uhr (Ende des kostenpflichtigen Parkens)
        Date startDayEvening = new Date(startDayEnd.getTime() - (DURATION_HOUR * 4));

        Date end = booking.getParkingEnd() == null ? new Date(System.currentTimeMillis()) : booking.getParkingEnd();

        //Endtag um 0:00 Uhr
        Date endDayBegin = new Date(end.getTime() - (end.getTime() % DURATION_DAY));

        //Endtag um 8:00 Uhr (Beginn des kostenpflichtigen Parkens)
        Date endDayMorning = new Date(endDayBegin.getTime() + (DURATION_HOUR * 8));

        //Endtag um 20:00 Uhr (Ende des kostenpflichtigen Parkens)
        Date endDayEvening = new Date(endDayBegin.getTime() + (DURATION_HOUR * 20));

        Double wholeDaysCost = 0.0;
        Double openedDaysCost = 0.0;

        if (endDayBegin.before(startDayEnd)) {
            if (start.after(end)) {
                throw new InvalidBookingException("Fehlerhaftes Booking-Objekt gesendet! Endzeitpunkt darf nicht vor dem Startzeitpunkt liegen");
            }
            if (start.before(startDayEvening) && end.after(endDayMorning)) {
                Long chargingBegin = (start.getTime() < startDayMorning.getTime() ? startDayMorning.getTime() : start.getTime());
                Long chargingEnd = (end.getTime() > endDayEvening.getTime() ? endDayEvening.getTime() : end.getTime());
                Double duration = (chargingEnd - chargingBegin) / DURATION_HOUR.doubleValue();
                openedDaysCost = duration * this.price;
            }
        } else {
            Double startDayCost = 0.0;
            Double endDayCost = 0.0;
            if (start.before(startDayEvening)) {
                Long chargingBegin = (start.getTime() < startDayMorning.getTime() ? startDayMorning.getTime() : start.getTime());
                Double chargingDuration = (startDayEvening.getTime() - chargingBegin) / DURATION_HOUR.doubleValue();
                startDayCost = chargingDuration < 10.0 ? (chargingDuration * this.price) : (10 * this.price);
            }
            if (end.after(endDayMorning)) {
                Long chargingEnd = (end.getTime() > endDayEvening.getTime() ? endDayEvening.getTime() : end.getTime());
                Double chargingDuration = (chargingEnd - endDayMorning.getTime()) / DURATION_HOUR.doubleValue();
                endDayCost = chargingDuration < 10.0 ? (chargingDuration * this.price) : (10 * this.price);
            }
            openedDaysCost = startDayCost + endDayCost;
            Long wholeDaysParked = (endDayBegin.getTime() - startDayEnd.getTime()) / DURATION_DAY;
            wholeDaysCost = wholeDaysParked * this.price * 10;
        }
        return (Math.round((wholeDaysCost + openedDaysCost)*100) / 100.0);

    }
}
