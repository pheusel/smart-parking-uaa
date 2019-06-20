package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.ParkingResponse;
import dhbw.smapa.uaa.repository.AddressRepository;
import dhbw.smapa.uaa.repository.ParkingRepository;
import dhbw.smapa.uaa.repository.UserRepository;
import dhbw.smapa.uaa.security.JWTTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperatorServiceImpl implements OperatorService {

    private final AppUserServiceImpl appUserService;

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final ParkingRepository parkingRepository;

    private ModelMapper modelMapper;

    @Autowired
    public OperatorServiceImpl(AppUserServiceImpl appUserService, UserRepository userRepository, AddressRepository addressRepository, ParkingRepository parkingRepository, JWTTokenProvider JWTTokenProvider, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.appUserService = appUserService;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.parkingRepository = parkingRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ParkingResponse> getUnpaidParkings(long parkingAreaId, String section) {
        List<ParkingResponse> liste = new ArrayList<>();
        //Abfrage aller Parkplätze, bei denen busy == true und
        //parker == null;
        return liste;
    }
}
