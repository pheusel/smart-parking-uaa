package dhbw.smapa.uaa.service;

import org.springframework.stereotype.Service;

@Service
public class ParkingServiceImpl implements ParkingService {

    /*private final AppUserServiceImpl appUserService;

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final ParkingRepository parkingRepository;

    private ModelMapper modelMapper;

    @Autowired
    public ParkingServiceImpl(AppUserServiceImpl appUserService, UserRepository userRepository, AddressRepository addressRepository, ParkingRepository parkingRepository, JWTTokenProvider JWTTokenProvider, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.appUserService = appUserService;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.parkingRepository = parkingRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<Parking> findByParkingId(Long parkingId) {

        return parkingRepository.findByParkingId(parkingId);
    }

    @Override
    public Boolean getParkingBusy(Long parkingId) {
        Parking parking = findByParkingId(parkingId).get();
        return parking.getBusy();
    }

    @Override
    public Boolean getParkingPaid(Long parkingId) {
        Parking parking = findByParkingId(parkingId).get();
        return isPaid(parking.getBeginning(), LocalDateTime.now(), parking.getPaidAmount());
    }

    @Override
    public String setParkingBusy(Long parkingId, LocalDateTime begin) {
        Parking parking = findByParkingId(parkingId).get();
        parking.setBusy(true);
        parking.setBeginning(begin);
        //Parkplatz zurück in DB schreiben
        return "";
    }

    @Override
    public String setParkingBusy(Long parkingId, String username, LocalDateTime begin) {
        Parking parking = findByParkingId(parkingId).get();
        parking.setBusy(true);
        parking.setParker(appUserService.findByUsername(username).get());
        parking.setBeginning(begin);
        //Parkplatz zurück in DB schreiben
        return "";
    }

    @Override
    public String setParkingPaid(Long parkingId, LocalDateTime begin, Double amount) {
        Parking parking = findByParkingId(parkingId).get();
        parking.setBusy(true);
        parking.setBeginning(begin);
        parking.setPaidAmount(amount);
        //Parkplatz zurück in DB schreiben
        return "";
    }

    @Override
    public String setParkingFree(Long parkingId, LocalDateTime end) {
        Parking parking = findByParkingId(parkingId).get();
        parking.setBusy(false);
        Double cost = getParkingCost(parking.getBeginning(), end);
        //Parkkosten verrechnen
        if(parking.getParker()!=null) {
            parking.getParker().setMonthlyCost(parking.getParker().getMonthlyCost() + cost);
            //Parker in DB zurückschreiben
            //...
        }
        //Parkvorgang bei Betreiber dokumentieren
        //...

        parking.setBeginning(null);
        parking.setParker(null);
        parking.setPaidAmount(null);
        //Parkplatz zurück in DB schreiben
        //...
        return "";
    }

    public Boolean isPaid(LocalDateTime begin, LocalDateTime end, Double paidAmount) {
        long parkingTime = ChronoUnit.MINUTES.between(begin, end);
        return (parkingTime / 100) <= paidAmount;
    }

    public Double getParkingCost(LocalDateTime begin, LocalDateTime end) {
        long parkingTime = ChronoUnit.MINUTES.between(begin, end);
        //1ct/min => parkingTime/100 = Betrag in €
        return parkingTime / 100.0;
    }*/
}
