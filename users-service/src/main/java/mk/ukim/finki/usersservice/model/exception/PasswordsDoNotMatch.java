package mk.ukim.finki.usersservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PasswordsDoNotMatch extends RuntimeException {
    public PasswordsDoNotMatch() {
        super("The 'Password' and 'Confirm Password' fields do not match.");
    }
}
