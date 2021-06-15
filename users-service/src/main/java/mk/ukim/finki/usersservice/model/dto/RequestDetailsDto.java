package mk.ukim.finki.usersservice.model.dto;

import lombok.Data;
import mk.ukim.finki.usersservice.model.Role;

@Data
public class RequestDetailsDto {

    private String id;

    private Role role;
}
