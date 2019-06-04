package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.AppUser;
import dhbw.smapa.uaa.exception.JWTValidationException;
import dhbw.smapa.uaa.exception.LoginException;
import dhbw.smapa.uaa.exception.UsernameMismatchException;
import dhbw.smapa.uaa.exception.UsernameTakenException;
import dhbw.smapa.uaa.repository.UserRepository;
import dhbw.smapa.uaa.security.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final UserRepository userRepository;

    private final JWTTokenProvider JWTTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AppUserServiceImpl(UserRepository userRepository, JWTTokenProvider JWTTokenProvider, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.JWTTokenProvider = JWTTokenProvider;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return JWTTokenProvider.createToken(username);
        } catch (AuthenticationException e) {
            throw new LoginException();
        }
    }

    @Override
    public String signup(AppUser appUser) {
        this.checkIfUsernameIsPresent(appUser.getUsername());
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        this.save(appUser);
        return JWTTokenProvider.createToken(appUser.getUsername());
    }

    @Override
    @Transactional
    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    @Transactional
    public void update(String username, AppUser appUser, HttpServletRequest req) {
        AppUser userFromJWT = getUserFromJWT(req);
        boolean res = Objects.equals(username, appUser.getUsername()) && Objects.equals(appUser.getUsername(), userFromJWT.getUsername());
        if (res){
            AppUser user = userRepository.findByUsername(appUser.getUsername()).orElseThrow(() -> new UsernameNotFoundException(appUser.getUsername()));
            user.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
            user.setAddress(appUser.getAddress());
        } else
            throw new UsernameMismatchException();
    }

    @Override
    public AppUser getUserFromJWT(HttpServletRequest req) {
        return userRepository.findByUsername(JWTTokenProvider.getUsername(JWTTokenProvider.resolveToken(req))).orElseThrow(JWTValidationException::new);
    }

    @Override
    public boolean validateToken(HttpServletRequest req) {
        return JWTTokenProvider.validateToken(req);
    }

    private void checkIfUsernameIsPresent(String username) {
        this.findByUsername(username).ifPresent(user -> {
            throw new UsernameTakenException(username);
        });
    }
}
