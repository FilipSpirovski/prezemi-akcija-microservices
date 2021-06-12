package mk.ukim.finki.paymentsservice.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.paymentsservice.service.PaymentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class PaymentApi {

    private final PaymentService paymentService;
}
