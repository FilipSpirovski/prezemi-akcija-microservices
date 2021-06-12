package mk.ukim.finki.initiativesservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InitiativeNotFound extends RuntimeException {
    public InitiativeNotFound(Long initiativeId) {
        super(String.format("The initiative with the provided id (%d) was not found.", initiativeId));
    }
}
