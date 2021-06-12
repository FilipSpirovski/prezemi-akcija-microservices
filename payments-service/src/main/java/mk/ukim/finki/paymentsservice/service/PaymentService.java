package mk.ukim.finki.paymentsservice.service;

import mk.ukim.finki.paymentsservice.model.Payment;
import mk.ukim.finki.paymentsservice.model.dto.PaymentDto;

import java.util.List;

public interface PaymentService {

    List<Payment> findAll();

    List<Payment> findAllByDonorEmail(String donorEmail);

    List<Payment> findAllByDonationId(Long donationId);

    Payment findById(Long paymentId);

    Payment createPayment(PaymentDto paymentDto);
}
