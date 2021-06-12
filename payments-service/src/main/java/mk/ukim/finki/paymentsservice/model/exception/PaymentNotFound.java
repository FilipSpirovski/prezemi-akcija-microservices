package mk.ukim.finki.paymentsservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PaymentNotFound extends RuntimeException {
    public PaymentNotFound(Long paymentId) {
        super(String.format("The payment with the provided id (%d) was not found.", paymentId));
    }
}
