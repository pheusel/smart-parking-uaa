package dhbw.smapa.uaa.repository;

import dhbw.smapa.uaa.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    /*Optional<Parking> findByParkingId(Long parkingId);

    List<Parking> findAllByParker(AppUser parker);
*/
}
