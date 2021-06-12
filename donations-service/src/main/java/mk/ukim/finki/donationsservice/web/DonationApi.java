package mk.ukim.finki.donationsservice.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.donationsservice.service.DonationService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class DonationApi {

    private final DonationService donationService;
}
