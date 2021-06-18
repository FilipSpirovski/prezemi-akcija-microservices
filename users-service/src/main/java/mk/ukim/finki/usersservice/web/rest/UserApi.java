package mk.ukim.finki.usersservice.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import mk.ukim.finki.usersservice.model.User;
import mk.ukim.finki.usersservice.model.dto.LoginDto;
import mk.ukim.finki.usersservice.model.dto.RegistrationDto;
import mk.ukim.finki.usersservice.model.exception.PasswordsDoNotMatch;
import mk.ukim.finki.usersservice.model.exception.UserAlreadyExists;
import mk.ukim.finki.usersservice.model.exception.UserNotFound;
import mk.ukim.finki.usersservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class UserApi {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.userService.findAll();

        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/with-role/{role}")
    public ResponseEntity<List<User>> getUsersWithRole(@PathVariable String role) {
        List<User> users = this.userService.findAllByRole(role);

        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/single-user")
    public ResponseEntity getUserDetails(@RequestParam String userEmail) {
        try {
            User user = this.userService.findByEmail(userEmail);

            return ResponseEntity.status(HttpStatus.FOUND).body(user);
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity registerNewUser(@RequestParam RegistrationDto registrationDto) {
        try {
            String token = this.userService.registerUser(registrationDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        } catch (ConstraintViolationException | PasswordsDoNotMatch | UserAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/sign-in")
    public ResponseEntity signInExistingUser(@RequestParam LoginDto loginDto) {
        try {
            String token = this.userService.loginUser(loginDto);

            return ResponseEntity.ok().body(token);
        } catch (ConstraintViolationException | UserNotFound e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteExistingUser(@RequestParam String userEmail) {
        try {
            boolean result = this.userService.deleteUser(userEmail);

            if (result) {
                String message = String.format("The user with the provided email (%s) was successfully deleted.", userEmail);

                return ResponseEntity.ok().body(message);
            } else {
                return ResponseEntity.internalServerError().build();
            }
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
