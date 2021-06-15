package mk.ukim.finki.paymentsservice.model.dto;

import lombok.Data;
import mk.ukim.finki.paymentsservice.model.Role;

@Data
public class RequestDetailsDto {

    private String id;

    private Role role;
}
