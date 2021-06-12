package mk.ukim.finki.forumservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ForumForInitiativeNotFound extends RuntimeException {
    public ForumForInitiativeNotFound(Long initiativeId) {
        super(String.format("The forum for the initiative with the provided id (%d) was not found.",
                initiativeId));
    }
}
