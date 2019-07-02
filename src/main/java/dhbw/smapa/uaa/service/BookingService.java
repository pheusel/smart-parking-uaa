package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.Booking;
import dhbw.smapa.uaa.entity.BrokerMessage;

public interface BookingService {

    void create(BrokerMessage brokerMessage);

    void update(BrokerMessage brokerMessage);

    void save(Booking booking);

    String getParkingArea(long parkingId);
}
