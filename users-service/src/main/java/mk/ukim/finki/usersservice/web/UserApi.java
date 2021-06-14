package mk.ukim.finki.usersservice.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.usersservice.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class UserApi {

    private final UserService userService;
}
