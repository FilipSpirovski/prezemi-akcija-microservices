package mk.ukim.finki.usersservice.model.exception;

import mk.ukim.finki.usersservice.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(User user) {
        super(String.format("A user with the provided email (%s) already exists.", user.getEmail()));
    }
}
