package mk.ukim.finki.paymentsservice.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PaymentDto {

    @NotNull
    @NotEmpty
    private String donorEmail;

    @NotNull
    private Long donationId;

    @Min(5)
    private double amount;
}
