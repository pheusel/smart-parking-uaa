package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.Address;

import java.util.Optional;

public interface AddressService {

    Optional<Address> findByAddressId(Long addressId);
}
