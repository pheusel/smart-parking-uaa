package dhbw.smapa.uaa.repository;

import dhbw.smapa.uaa.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByParkingIdOrderByParkingStartDesc(Long parkingId);

    List<Booking> findByUidOrderByParkingStartDesc(Long uid);
}
