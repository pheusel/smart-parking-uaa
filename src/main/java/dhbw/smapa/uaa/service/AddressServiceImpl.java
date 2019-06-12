package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.Address;
import dhbw.smapa.uaa.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Optional<Address> findByAddressId(Long addressId){
        return addressRepository.findByAddressId(addressId);
    }
}
