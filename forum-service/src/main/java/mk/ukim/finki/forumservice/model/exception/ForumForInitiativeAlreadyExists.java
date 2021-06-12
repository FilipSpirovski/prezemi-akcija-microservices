package mk.ukim.finki.forumservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ForumForInitiativeAlreadyExists extends RuntimeException {
    public ForumForInitiativeAlreadyExists(Long initiativeId) {
        super(String.format("A forum for the initiative with the provided id (%d) already exists.",
                initiativeId));
    }
}
