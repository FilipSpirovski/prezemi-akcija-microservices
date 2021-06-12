package mk.ukim.finki.donationsservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.donationsservice.model.dto.DonationDto;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String initiatorEmail;

    private String title;

    private String description;

    private double amountToCollect; // in EUR;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    public Donation(String initiatorEmail, DonationDto donationDto) {
        this.initiatorEmail = initiatorEmail;
        this.title = donationDto.getTitle();
        this.description = donationDto.getDescription();
        this.amountToCollect = donationDto.getAmountToCollect();
        this.status = Status.ACTIVE;
    }

    public void update(DonationDto donationDto) {
        this.title = donationDto.getTitle();
        this.description = donationDto.getDescription();
        this.amountToCollect = donationDto.getAmountToCollect();
    }
}
