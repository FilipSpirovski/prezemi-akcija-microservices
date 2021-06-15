package mk.ukim.finki.forumservice.model.dto;

import lombok.Data;
import mk.ukim.finki.forumservice.model.Role;

@Data
public class RequestDetailsDto {

    private String id;

    private Role role;
}
