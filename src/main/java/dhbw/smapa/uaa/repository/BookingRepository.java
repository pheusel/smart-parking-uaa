package dhbw.smapa.uaa.repository;

import dhbw.smapa.uaa.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<List<Booking>> findByParkingIdOrderByParkingStartDesc(Long parkingId);

    Optional<List<Booking>> findByUidOrderByParkingStartDesc(String uid);
}
