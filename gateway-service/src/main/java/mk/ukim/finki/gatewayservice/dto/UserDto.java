package mk.ukim.finki.gatewayservice.dto;

import lombok.Data;

@Data
public class UserDto {

    private String email;

    private String jwtToken;
}