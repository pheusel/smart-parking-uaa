package dhbw.smapa.uaa.controller;

import dhbw.smapa.uaa.entity.*;
import dhbw.smapa.uaa.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {

        this.appUserService = appUserService;
    }

    @PostMapping("/signup")
    TokenResponse signup(@RequestBody AppUser appUser) {

        return new TokenResponse(appUserService.signup(appUser));
    }

    @PostMapping("/login")
    TokenResponse login(@RequestBody LoginUser loginUser) {

        return new TokenResponse(appUserService.login(loginUser));
    }

    @DeleteMapping("/user")
    ResponseEntity<?> deleteUser(HttpServletRequest req) {
        appUserService.delete(req);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user")
    ResponseEntity<?> updateUser(@RequestBody UpdateUser updatedUserData, HttpServletRequest req) {
        appUserService.update(updatedUserData, req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    JWTValidationResponse validateToken(HttpServletRequest req) {
        return new JWTValidationResponse(appUserService.validateToken(req));
    }

    @GetMapping("/resolve")
    UserResponse resolveToken(HttpServletRequest req) {

        return appUserService.resolve(req);
    }
}
