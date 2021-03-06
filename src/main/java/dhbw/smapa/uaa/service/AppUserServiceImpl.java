package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.*;
import dhbw.smapa.uaa.exception.JWTValidationException;
import dhbw.smapa.uaa.exception.LoginException;
import dhbw.smapa.uaa.exception.ParkingNotFoundException;
import dhbw.smapa.uaa.exception.UsernameTakenException;
import dhbw.smapa.uaa.repository.BookingRepository;
import dhbw.smapa.uaa.repository.ParkingRepository;
import dhbw.smapa.uaa.repository.UserRepository;
import dhbw.smapa.uaa.security.JWTTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;

    private final ParkingRepository parkingRepository;

    private final JWTTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;

    @Autowired
    public AppUserServiceImpl(UserRepository userRepository,
                              BookingRepository bookingRepository,
                              ParkingRepository parkingRepository,
                              JWTTokenProvider jwtTokenProvider,
                              AuthenticationManager authenticationManager,
                              BCryptPasswordEncoder bCryptPasswordEncoder,
                              ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.parkingRepository = parkingRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void save(AppUser user) {

        userRepository.save(user);
    }

    @Override
    public String login(LoginUser loginUser) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
            return jwtTokenProvider.createToken(loginUser.getUsername());
        } catch (AuthenticationException e) {
            throw new LoginException();
        }
    }

    @Override
    public String signup(AppUser appUser) {
        this.checkIfUsernameIsPresent(appUser.getUsername());
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        appUser.setUid(generateUID());
        this.save(appUser);
        return jwtTokenProvider.createToken(appUser.getUsername());
    }

    @Override
    public BookingResponse overview(HttpServletRequest req) {
        AppUser user = getUserFromJWT(req);
        List<Booking> bookingList = bookingRepository.findByUidOrderByParkingStartDesc(user.getUid());
        if (bookingList != null) {
            if (bookingList.size() != 0) {
                if (bookingList.get(0).getParkingEnd() == null) {
                    BookingResponse response = modelMapper.map(bookingList.get(0), BookingResponse.class);
                    response.setCost(Area.getArea(getDistinctParking(bookingList.get(0).getParkingId()).getArea()).getBookingCost(bookingList.get(0)));
                    return response;
                }
            }
        }
        return null;
    }

    @Override
    public List<BookingResponse> history(HttpServletRequest req) {
        AppUser user = getUserFromJWT(req);
        List<Booking> bookingList = bookingRepository.findByUidOrderByParkingStartDesc(user.getUid());
        if (bookingList != null) {
            if (bookingList.size() != 0) {
                if (bookingList.get(0).getParkingEnd() == null) {
                    bookingList.remove(0);
                }
                List<BookingResponse> response = new ArrayList<>();
                for (Booking booking : bookingList) {
                    BookingResponse responseItem = modelMapper.map(booking, BookingResponse.class);
                    responseItem.setCost(Area.getArea(getDistinctParking(responseItem.getParkingId()).getArea()).getBookingCost(booking));
                    response.add(responseItem);
                }
                return response;
            }
        }
        return null;
    }

    @Override
    public List<Parking> getFreeParkings() {

        return parkingRepository.findByIsFree(Boolean.TRUE);
    }

    @Override
    public List<Parking> getAllParkings() {

        return parkingRepository.findAll();
    }

    @Override
    public ParkingResponse getDistinctParking(long parkingId) {
        Optional<Parking> parking = parkingRepository.findByParkingId(parkingId);
        if (parking.isPresent()) {
            ParkingResponse response = modelMapper.map(parking.get(), ParkingResponse.class);
            response.setPrice(Area.getArea(response.getArea()).getPrice());
            Optional<List<Booking>> bookingList = bookingRepository.findByParkingIdOrderByParkingStartDesc(parking.get().getParkingId());
            if(bookingList.isPresent()) {
                if(bookingList.get().size() != 0) {
                    if(bookingList.get().get(0).getParkingEnd() == null) {
                        response.setParkingStart(bookingList.get().get(0).getParkingStart());
                    }
                }
            }
            return response;
        } else {
            throw new ParkingNotFoundException();
        }
    }

    @Override
    @Transactional
    public void delete(HttpServletRequest req) {
        AppUser userToDelete = getUserFromJWT(req);
        userRepository.deleteByUsername(userToDelete.getUsername());
    }

    @Override
    @Transactional
    public void update(UpdateUser updatedUserData, HttpServletRequest req) {
        AppUser userToUpdate = getUserFromJWT(req);
        userToUpdate.setPassword(bCryptPasswordEncoder.encode(updatedUserData.getPassword()));
        userToUpdate.setAddress(updatedUserData.getAddress());
    }

    @Override
    public UserResponse resolve(HttpServletRequest req) {
        AppUser appUser = getUserFromJWT(req);
        AddressResponse addressResponse = modelMapper
                .map(appUser.getAddress(), AddressResponse.class);
        UserResponse userResponse = modelMapper.map(appUser, UserResponse.class);
        userResponse.setAddressResponse(addressResponse);
        return userResponse;
    }

    @Override
    public AppUser getUserFromJWT(HttpServletRequest req) {
        return userRepository
                .findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)))
                .orElseThrow(JWTValidationException::new);
    }

    @Override
    public boolean validateToken(HttpServletRequest req) {
        return jwtTokenProvider.validateToken(req);
    }

    private void checkIfUsernameIsPresent(String username) {
        this.findByUsername(username)
                .ifPresent(user -> {
                    throw new UsernameTakenException(username);
                });
    }

    private String generateUID() {
        return new Random()
                .ints(5, 0, 255)
                .boxed()
                .collect(Collectors.toList())
                .toString();
    }
}
