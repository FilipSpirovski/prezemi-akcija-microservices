package mk.ukim.finki.donationsservice.repository;

import mk.ukim.finki.donationsservice.model.Donation;
import mk.ukim.finki.donationsservice.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findAllByInitiatorEmail(String initiatorEmail);

    List<Donation> findAllByStatus(Status status);
}
