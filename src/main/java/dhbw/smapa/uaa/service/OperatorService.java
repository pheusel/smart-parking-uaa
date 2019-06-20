package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.ParkingResponse;

import java.util.List;

public interface OperatorService {

    List<ParkingResponse> getUnpaidParkings(long parkingAreaId, String section);


}
