package mk.ukim.finki.initiativesservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ParticipantNotFound extends RuntimeException {
    public ParticipantNotFound(String participantEmail, String initiativeTitle) {
        super(String.format("The participant with the provided email (%s) has never joined the provided initiative (%s)",
                participantEmail, initiativeTitle));
    }
}
