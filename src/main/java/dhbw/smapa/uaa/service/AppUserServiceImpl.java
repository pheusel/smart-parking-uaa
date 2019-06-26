package dhbw.smapa.uaa.service;

import dhbw.smapa.uaa.entity.*;
import dhbw.smapa.uaa.exception.JWTValidationException;
import dhbw.smapa.uaa.exception.LoginException;
import dhbw.smapa.uaa.exception.UsernameTakenException;
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
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final UserRepository userRepository;

    private final JWTTokenProvider JWTTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;

    @Autowired
    public AppUserServiceImpl(UserRepository userRepository, JWTTokenProvider JWTTokenProvider, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.JWTTokenProvider = JWTTokenProvider;
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
            return JWTTokenProvider.createToken(loginUser.getUsername());
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
        AddressResponse addressResponse = modelMapper.map(appUser.getAddress(), AddressResponse.class);
        UserResponse userResponse = modelMapper.map(appUser, UserResponse.class);
        userResponse.setAddressResponse(addressResponse);
        return userResponse;
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
