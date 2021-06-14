package mk.ukim.finki.usersservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFound extends RuntimeException {
    public UserNotFound(String userEmail) {
        super(String.format("The user with the provided email (%s) was not found.", userEmail));
    }
}
