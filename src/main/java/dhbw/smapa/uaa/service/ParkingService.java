package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.BrokerMessage;

public interface ParkingService {

    void update(BrokerMessage brokerMessage);

/*    Optional<Parking> findByParkingId(Long parkingId);

    Boolean getParkingBusy(Long parkingId);

    Boolean getParkingPaid(Long parkingId);

    String setParkingBusy(Long parkingId, LocalDateTime begin);

    String setParkingBusy(Long parkingId, String username, LocalDateTime begin);

    String setParkingPaid(Long parkingId, LocalDateTime begin, Double amount);

    String setParkingFree(Long parkingId, LocalDateTime end);*/
}
