package dhbw.smapa.uaa.controller;

import dhbw.smapa.uaa.entity.BrokerMessage;
import dhbw.smapa.uaa.entity.Parking;
import dhbw.smapa.uaa.service.BookingService;
import dhbw.smapa.uaa.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

        if (!brokerMessage.getIs_free())
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

    @PutMapping("/pay")
    ResponseEntity<?> payInvoice(HttpServletRequest req) {
        bookingService.payInvoice(req);
        return ResponseEntity.ok().build();
    }
}
