package mk.ukim.finki.donationsservice.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.donationsservice.model.Donation;
import mk.ukim.finki.donationsservice.model.Status;
import mk.ukim.finki.donationsservice.model.dto.DonationDto;
import mk.ukim.finki.donationsservice.model.exception.DonationNotFound;
import mk.ukim.finki.donationsservice.model.exception.InvalidStatusName;
import mk.ukim.finki.donationsservice.repository.DonationRepository;
import mk.ukim.finki.donationsservice.service.DonationService;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;

@Service
@AllArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final Validator validator;

    @Override
    public List<Donation> findAll() {
        return this.donationRepository.findAll();
    }

    @Override
    public List<Donation> findAllByInitiatorEmail(String initiatorEmail) {
        return this.donationRepository.findAllByInitiatorEmail(initiatorEmail);
    }

    @Override
    public List<Donation> findAllByStatus(String statusName) throws InvalidStatusName {
        Status status = this.validateStatusName(statusName);

        return this.donationRepository.findAllByStatus(status);
    }

    @Override
    public Donation findById(Long donationId) {
        return this.donationRepository.findById(donationId)
                .orElseThrow(() -> new DonationNotFound(donationId));
    }

    @Override
    public Donation createDonation(String initiatorEmail, DonationDto donationDto) {
        this.checkDtoForViolations(donationDto);
        Donation newDonation = new Donation(initiatorEmail, donationDto);

        return this.donationRepository.save(newDonation);
    }

    @Override
    public Donation editDonation(Long donationId, DonationDto donationDto) throws DonationNotFound {
        this.checkDtoForViolations(donationDto);
        Donation existingDonation = this.findById(donationId);

        existingDonation.update(donationDto);

        return this.donationRepository.save(existingDonation);
    }

    @Override
    public boolean deleteDonation(Long donationId) throws DonationNotFound {
        Donation existingDonation = this.findById(donationId);

        this.donationRepository.delete(existingDonation);
        try {
            existingDonation = this.findById(donationId);

            return false;
        } catch (DonationNotFound e) {
            return true;
        }
    }

    private Status validateStatusName(String statusName) {
        boolean validStatusName = false;

        for (Status status : Status.values()) {
            if (status.toString().equals(statusName)) {
                validStatusName = true;
                break;
            }
        }

        if (validStatusName) {
            return Status.valueOf(statusName);
        } else {
            throw new InvalidStatusName(statusName);
        }
    }

    private void checkDtoForViolations(DonationDto donationDto) {
        var constraintViolations = this.validator.validate(donationDto);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The provided 'Donation' object is not valid.", constraintViolations);
        }
    }
}
