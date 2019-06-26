package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.BrokerMessage;
import dhbw.smapa.uaa.entity.Parking;
import dhbw.smapa.uaa.exception.ParkingNotFoundException;
import dhbw.smapa.uaa.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    @Transactional
    public void update(BrokerMessage brokerMessage) {

        Parking parkingToUpdate = parkingRepository.findByParkingId(brokerMessage.getParkingId()).orElseThrow(ParkingNotFoundException::new);
        parkingToUpdate.setIsFree(brokerMessage.getIsFree());
        if (brokerMessage.getUid() != null)
            parkingToUpdate.setIsIdentified(true);
        else
            parkingToUpdate.setIsIdentified(false);
    }
}
