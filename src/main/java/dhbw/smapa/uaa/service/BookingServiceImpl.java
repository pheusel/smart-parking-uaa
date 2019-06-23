package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.Booking;
import dhbw.smapa.uaa.entity.BrokerMessage;
import dhbw.smapa.uaa.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void create(BrokerMessage brokerMessage) {

        Booking newBooking = new Booking();
        newBooking.setParkingId(brokerMessage.getParking_id());
        newBooking.setParkingStart(brokerMessage.getTimestamp());

        if (brokerMessage.getUid() != null) {
            newBooking.setUid(brokerMessage.getUid());
            newBooking.setPaid(false);
        }

        this.save(newBooking);
    }

    @Override
    public void payInvoice(HttpServletRequest req) {

    }

    @Override
    @Transactional
    public void update(BrokerMessage brokerMessage) {

        List<Booking> bookingList = bookingRepository.findByParkingIdOrderByParkingStartDesc(brokerMessage.getParking_id());
        Booking bookingToUpdate = bookingList.get(0);
        bookingToUpdate.setParkingEnd(brokerMessage.getTimestamp());

        if (bookingToUpdate.getUid() != null) {
            bookingToUpdate.setInvoiceAmount(calculateInvoiceAmount(bookingToUpdate.getParkingStart(), bookingToUpdate.getParkingEnd()));
        }
    }

    @Override
    @Transactional
    public void save(Booking booking) {

        bookingRepository.save(booking);
    }

    private double calculateInvoiceAmount(@NotNull Timestamp start, @NotNull Timestamp end) {
        long milliseconds = end.getTime() - start.getTime();
        int seconds = (int) milliseconds / 1000;
        double hours = (double) seconds / 3600;
        double PRICE_PER_HOUR = 2.0;
        return hours * PRICE_PER_HOUR;
    }
}
