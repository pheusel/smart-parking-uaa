package dhbw.smapa.uaa.repository;

import dhbw.smapa.uaa.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    Optional<Parking> findByParkingId(long parkingId);

    List<Parking> findByIsFree(Boolean isFree);

    List<Parking> findByIsFreeAndIsIdentified(Boolean isFree, Boolean isIdentified);
}
