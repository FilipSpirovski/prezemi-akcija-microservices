package mk.ukim.finki.paymentsservice.repository;

import mk.ukim.finki.paymentsservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByDonorEmail(String donorEmail);

    List<Payment> findAllByDonationId(Long donationId);
}
