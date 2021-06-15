package mk.ukim.finki.initiativesservice.model.dto;

import lombok.Data;
import mk.ukim.finki.initiativesservice.model.Role;

@Data
public class RequestDetailsDto {

    private String id;

    private Role role;
}
