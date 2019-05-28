package dhbw.smapa.uaa.controller;

import dhbw.smapa.uaa.entity.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class UserController {

    @PostMapping("/signup")
    String signup(@RequestBody User user) {
        return "Test";
    }
}
