package mk.ukim.finki.paymentsservice.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.paymentsservice.model.Payment;
import mk.ukim.finki.paymentsservice.model.dto.PaymentDto;
import mk.ukim.finki.paymentsservice.model.exception.PaymentNotFound;
import mk.ukim.finki.paymentsservice.repository.PaymentRepository;
import mk.ukim.finki.paymentsservice.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final Validator validator;

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
    public Payment createPayment(PaymentDto paymentDto) throws ConstraintViolationException {
        this.checkDtoForViolations(paymentDto);
        Payment newPayment = new Payment(paymentDto);

        return this.paymentRepository.save(newPayment);
    }

    private void checkDtoForViolations(PaymentDto paymentDto) {
        var constraintViolations = this.validator.validate(paymentDto);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The provided 'Payment' object is not valid.", constraintViolations);
        }
    }
}
