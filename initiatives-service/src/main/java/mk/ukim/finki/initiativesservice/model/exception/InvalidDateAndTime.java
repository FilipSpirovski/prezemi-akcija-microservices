package mk.ukim.finki.initiativesservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidDateAndTime extends RuntimeException {
    public InvalidDateAndTime() {
        super("The date and time of the new initiative must be in the future.");
    }
}
