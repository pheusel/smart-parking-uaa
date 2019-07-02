package dhbw.smapa.uaa.controller;

import dhbw.smapa.uaa.entity.BrokerMessage;
import dhbw.smapa.uaa.entity.Parking;
import dhbw.smapa.uaa.service.BookingService;
import dhbw.smapa.uaa.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ParkingController {

    private final ParkingService parkingService;
    private final BookingService bookingService;

    @Autowired
    public ParkingController(ParkingService parkingService, BookingService bookingService) {
        this.parkingService = parkingService;
        this.bookingService = bookingService;
    }

    public void messageArrived(BrokerMessage brokerMessage) {

        System.out.println(brokerMessage);

        parkingService.update(brokerMessage);

        if (!brokerMessage.getIsFree())
            bookingService.create(brokerMessage);
        else
            bookingService.update(brokerMessage);
    }


    @GetMapping("/parking")
    List<Parking> getParkingList() {
        return null;
    }

    @GetMapping("/unpaid")
    List<Parking> getUnpaidParkingList() {
        return null;
    }
}
