package mk.ukim.finki.donationsservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DonationNotFound extends RuntimeException {
    public DonationNotFound(Long donationId) {
        super(String.format("The donation with the provided id (%d) was not found.", donationId));
    }
}
