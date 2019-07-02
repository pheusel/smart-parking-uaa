package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.Area;
import dhbw.smapa.uaa.entity.Booking;
import dhbw.smapa.uaa.entity.BrokerMessage;
import dhbw.smapa.uaa.exception.BookingNotFoundException;
import dhbw.smapa.uaa.exception.ParkingNotFoundException;
import dhbw.smapa.uaa.repository.BookingRepository;
import dhbw.smapa.uaa.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ParkingRepository parkingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, ParkingRepository parkingRepository) {

        this.bookingRepository = bookingRepository;
        this.parkingRepository = parkingRepository;
    }

    @Override
    @Transactional
    public void create(BrokerMessage brokerMessage) {

        Booking newBooking = Booking.builder()
                .parkingId(brokerMessage.getParkingId())
                .parkingStart(brokerMessage.getTimestamp())
                .build();

        if (brokerMessage.getUid() != null) {
            newBooking.setUid(brokerMessage.getUid());
            newBooking.setPaid(false);
        }

        save(newBooking);
    }

    @Override
    @Transactional
    public void update(BrokerMessage brokerMessage) {

        List<Booking> bookingList = bookingRepository.findByParkingIdOrderByParkingStartDesc(brokerMessage.getParkingId()).orElseThrow(BookingNotFoundException::new);

        Booking bookingToUpdate = bookingList.get(0);
        bookingToUpdate.setParkingEnd(brokerMessage.getTimestamp());

        if (bookingToUpdate.getUid() != null) {
            bookingToUpdate.setInvoiceAmount(Area.getArea(getParkingArea(bookingToUpdate.getParkingId())).getBookingCost(bookingToUpdate));
        }
    }

    @Override
    @Transactional
    public void save(Booking booking) {

        bookingRepository.save(booking);
    }

    @Override
    public String getParkingArea(long parkingId) {
        return parkingRepository
                .findByParkingId(parkingId)
                .orElseThrow(ParkingNotFoundException::new)
                .getArea();
    }
}
