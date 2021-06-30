package mk.ukim.finki.paymentsservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.paymentsservice.model.dto.PaymentDto;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String donorEmail;

    private Long donationId;

    private double amount; // in EUR;

    private LocalDateTime transactionDateAndTime;

    public Payment(String donorEmail, PaymentDto paymentDto) {
        this.donorEmail = donorEmail;
        this.donationId = paymentDto.getDonationId();
        this.amount = paymentDto.getAmount();
        this.transactionDateAndTime = LocalDateTime.now();
    }
}
