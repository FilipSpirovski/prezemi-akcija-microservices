package mk.ukim.finki.initiativesservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class ForumNotCreated extends RuntimeException {
    public ForumNotCreated(Long initiativeId) {
        super(String.format("A forum for the initiative with the provided id (%d) was not created.", initiativeId));
    }
}
