package mk.ukim.finki.donationsservice.model.dto;

import lombok.Data;
import mk.ukim.finki.donationsservice.model.Role;

@Data
public class RequestDetailsDto {

    private String id;

    private Role role;
}
