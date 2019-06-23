package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.Booking;
import dhbw.smapa.uaa.entity.BrokerMessage;

import javax.servlet.http.HttpServletRequest;

public interface BookingService {

    void create(BrokerMessage brokerMessage);

    void payInvoice(HttpServletRequest req);

    void update(BrokerMessage brokerMessage);

    void save(Booking booking);
}
