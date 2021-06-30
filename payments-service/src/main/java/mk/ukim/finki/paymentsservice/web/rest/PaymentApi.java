package mk.ukim.finki.paymentsservice.web.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.paymentsservice.model.Payment;
import mk.ukim.finki.paymentsservice.model.dto.PaymentDto;
import mk.ukim.finki.paymentsservice.model.exception.DonationNotFound;
import mk.ukim.finki.paymentsservice.model.exception.PaymentNotFound;
import mk.ukim.finki.paymentsservice.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class PaymentApi {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = this.paymentService.findAll();

        return ResponseEntity.ok().body(payments);
    }

    @GetMapping("/made-by")
    public ResponseEntity<List<Payment>> getPaymentsMadeBy(@RequestBody String donorEmail) {
        List<Payment> payments = this.paymentService.findAllByDonorEmail(donorEmail);

        return ResponseEntity.ok().body(payments);
    }

    @GetMapping("/for-donation/{donationId}")
    public ResponseEntity<List<Payment>> getPaymentsForDonation(@PathVariable Long donationId) {
        List<Payment> payments = this.paymentService.findAllByDonationId(donationId);

        return ResponseEntity.ok().body(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPaymentDetails(@PathVariable Long id) {
        try {
            Payment payment = this.paymentService.findById(id);

            return ResponseEntity.status(HttpStatus.FOUND).body(payment);
        } catch (PaymentNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/new")
    public ResponseEntity addNewPayment(@RequestBody PaymentDto paymentDto, Authentication authentication) {
        try {
            Payment payment = this.paymentService.createPayment(paymentDto, authentication);

            return ResponseEntity.status(HttpStatus.CREATED).body(payment);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DonationNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
