package mk.ukim.finki.paymentsservice.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.paymentsservice.model.Payment;
import mk.ukim.finki.paymentsservice.model.dto.PaymentDto;
import mk.ukim.finki.paymentsservice.model.exception.DonationNotFound;
import mk.ukim.finki.paymentsservice.model.exception.PaymentNotFound;
import mk.ukim.finki.paymentsservice.repository.PaymentRepository;
import mk.ukim.finki.paymentsservice.service.PaymentService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final Validator validator;
    private final RestTemplate restTemplate;
    private final String usersMicroserviceUrl = "http://users-service/api/users";
    private final String donationsMicroserviceUrl = "http://donations-service/api/donations";

    @Override
    public List<Payment> findAll() {
        return this.paymentRepository.findAll();
    }

    @Override
    public List<Payment> findAllByDonorEmail(String donorEmail) {
        return this.paymentRepository.findAllByDonorEmail(donorEmail);
    }

    @Override
    public List<Payment> findAllByDonationId(Long donationId) {
        return this.paymentRepository.findAllByDonationId(donationId);
    }

    @Override
    public Payment findById(Long paymentId) {
        return this.paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFound(paymentId));
    }

    @Override
    public Payment createPayment(PaymentDto paymentDto, String authPayload, Authentication authentication) throws
            ConstraintViolationException {
        this.checkDtoForViolations(paymentDto);

        if (this.checkIfDonationExists(paymentDto.getDonationId(), authPayload)) {
            String donorEmail = (String) authentication.getPrincipal();
            Payment newPayment = new Payment(donorEmail, paymentDto);

            return this.paymentRepository.save(newPayment);
        } else {
            throw new DonationNotFound(paymentDto.getDonationId());
        }
    }

    private void checkDtoForViolations(PaymentDto paymentDto) {
        var constraintViolations = this.validator.validate(paymentDto);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The provided 'Payment' object is not valid.", constraintViolations);
        }
    }

    private boolean checkIfDonationExists(Long donationId, String authPayload) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authPayload);

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(this.donationsMicroserviceUrl + "/" + donationId);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = this.restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        return response.getStatusCode().equals(HttpStatus.FOUND);
    }
}
