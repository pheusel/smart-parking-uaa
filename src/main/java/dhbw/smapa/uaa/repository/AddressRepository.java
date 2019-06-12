package dhbw.smapa.uaa.repository;

import dhbw.smapa.uaa.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByAddressId(Long addressId);
}
