package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.AppUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface AppUserService {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);

    void save(AppUser appUser);

    String login(String username, String password);

    String signup(AppUser appUser);

    void delete(String username);

    void update(String username, AppUser appUser);

    AppUser getUserFromJWT(HttpServletRequest req);

    boolean validateToken(HttpServletRequest req);
}
