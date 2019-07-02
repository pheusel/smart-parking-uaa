package dhbw.smapa.uaa.controller;

import dhbw.smapa.uaa.entity.BrokerMessage;
import dhbw.smapa.uaa.service.BookingService;
import dhbw.smapa.uaa.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

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
}
