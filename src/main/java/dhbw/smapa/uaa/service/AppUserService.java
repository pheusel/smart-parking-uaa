package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface AppUserService {

    Optional<AppUser> findByUsername(String username);

    void save(AppUser appUser);

    String login(LoginUser loginUser);

    //OverviewResponse overview(String token);

    //HistoryResponse history(String token);

    String signup(AppUser appUser);

    void delete(HttpServletRequest req);

    void update(UpdateUser updatedUserData, HttpServletRequest req);

    UserResponse resolve(HttpServletRequest req);

    AppUser getUserFromJWT(HttpServletRequest req);

    boolean validateToken(HttpServletRequest req);
}
