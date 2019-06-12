package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.AppUser;
import dhbw.smapa.uaa.entity.LoginUser;
import dhbw.smapa.uaa.entity.UserResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface AppUserService {

    Optional<AppUser> findByUsername(String username);

    void save(AppUser appUser);

    String login(LoginUser loginUser);

    String signup(AppUser appUser);

    void delete(String username, HttpServletRequest req);

    void update(String username, AppUser appUser, HttpServletRequest req);

    UserResponse resolve(HttpServletRequest req);

    AppUser getUserFromJWT(HttpServletRequest req);

    boolean validateToken(HttpServletRequest req);
}
