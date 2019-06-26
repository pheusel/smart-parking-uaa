package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface AppUserService {

    Optional<AppUser> findByUsername(String username);

    void save(AppUser appUser);

    String login(LoginUser loginUser);

    String signup(AppUser appUser);

    Booking overview(HttpServletRequest req);

    List<Booking> history(HttpServletRequest req);

    List<Parking> getFreeParkings();

    List<Parking> getAllParkings();

    Parking getDistinctParking(long parkingId);

    void delete(HttpServletRequest req);

    void update(UpdateUser updatedUserData, HttpServletRequest req);

    UserResponse resolve(HttpServletRequest req);

    AppUser getUserFromJWT(HttpServletRequest req);

    boolean validateToken(HttpServletRequest req);
}
