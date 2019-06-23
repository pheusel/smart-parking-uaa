package dhbw.smapa.uaa.controller;

import dhbw.smapa.uaa.entity.BrokerMessage;
import dhbw.smapa.uaa.entity.Parking;
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

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    public void messageArrived(BrokerMessage brokerMessage){
        System.out.println(brokerMessage);
        parkingService.update(brokerMessage);
    }

    @GetMapping("/parking")
    List<Parking> getParkingList(){
        return null;
    }

    // is free = False + Flag = True --> Update in Parking + Booking Table Eintrag anlegen

    // is free = False + Flag = True --> Update in Parking + Booking Eintrag mit wenigen Daten

    // wenn Parkplatz frei --> Update Parking + Update Booking


}
