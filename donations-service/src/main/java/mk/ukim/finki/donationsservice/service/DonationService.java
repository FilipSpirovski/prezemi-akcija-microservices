package mk.ukim.finki.donationsservice.service;

import mk.ukim.finki.donationsservice.model.Donation;
import mk.ukim.finki.donationsservice.model.dto.DonationDto;

import java.util.List;

public interface DonationService {

    List<Donation> findAll();

    List<Donation> findAllByInitiatorEmail(String initiatorEmail);

    List<Donation> findAllByStatus(String statusName);

    Donation findById(Long donationId);

    Donation createDonation(String initiatorEmail, DonationDto donationDto);

    Donation editDonation(Long donationId, DonationDto donationDto);

    boolean deleteDonation(Long donationId);
}
