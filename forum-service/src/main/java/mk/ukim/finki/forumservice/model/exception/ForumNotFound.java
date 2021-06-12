package mk.ukim.finki.forumservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ForumNotFound extends RuntimeException {
    public ForumNotFound(Long forumId) {
        super(String.format("The forum with the provided id (%d) was not found.", forumId));
    }
}
