package mk.ukim.finki.usersservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidRoleName extends RuntimeException {
    public InvalidRoleName(String roleName) {
        super(String.format("The provided role name (%s) is invalid.", roleName));
    }
}
