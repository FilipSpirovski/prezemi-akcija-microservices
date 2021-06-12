package mk.ukim.finki.donationsservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidStatusName extends RuntimeException {
    public InvalidStatusName(String statusName) {
        super(String.format("The provided status name (%s) is invalid.", statusName));
    }
}
