package mk.ukim.finki.donationsservice.web.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.donationsservice.model.Donation;
import mk.ukim.finki.donationsservice.model.dto.DonationDto;
import mk.ukim.finki.donationsservice.model.exception.DonationNotFound;
import mk.ukim.finki.donationsservice.model.exception.InvalidStatusName;
import mk.ukim.finki.donationsservice.service.DonationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class DonationApi {

    private final DonationService donationService;

    @GetMapping
    public ResponseEntity<List<Donation>> getAllDonations() {
        List<Donation> donations = this.donationService.findAll();

        return ResponseEntity.ok().body(donations);
    }

    @GetMapping("/initiated-by")
    public ResponseEntity<List<Donation>> getDonationsInitiatedBy(@RequestBody String initiatorEmail) {
        List<Donation> donations = this.donationService.findAllByInitiatorEmail(initiatorEmail);

        return ResponseEntity.ok().body(donations);
    }

    @GetMapping("/with-status/{status}")
    public ResponseEntity getDonationsWithStatus(@PathVariable String status) {
        try {
            List<Donation> donations = this.donationService.findAllByStatus(status);

            return ResponseEntity.ok().body(donations);
        } catch (InvalidStatusName e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getDonationDetails(@PathVariable Long id) {
        try {
            Donation donation = this.donationService.findById(id);

            return ResponseEntity.status(HttpStatus.FOUND).body(donation);
        } catch (DonationNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/new")
    public ResponseEntity addNewDonation(@RequestBody DonationDto donationDto, Authentication authentication) {
        try {
            Donation donation = this.donationService.createDonation(donationDto, authentication);

            return ResponseEntity.status(HttpStatus.CREATED).body(donation);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity updateExistingDonation(@PathVariable Long id,
                                                 @RequestBody DonationDto donationDto) {
        try {
            Donation donation = this.donationService.editDonation(id, donationDto);

            return ResponseEntity.ok().body(donation);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DonationNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteExistingDonation(@PathVariable Long id) {
        try {
            boolean result = this.donationService.deleteDonation(id);

            if (result) {
                String message = String.format("The donation with the provided id (%d) was successfully deleted.", id);

                return ResponseEntity.ok().body(message);
            } else {
                return ResponseEntity.internalServerError().build();
            }
        } catch (DonationNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
