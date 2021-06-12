package mk.ukim.finki.forumservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CommentNotFound extends RuntimeException {
    public CommentNotFound(Long commentId) {
        super(String.format("The comment with the provided id (%d) was not found.", commentId));
    }
}
