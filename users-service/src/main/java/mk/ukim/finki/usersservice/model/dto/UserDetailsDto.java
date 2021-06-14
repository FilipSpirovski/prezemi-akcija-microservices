package mk.ukim.finki.usersservice.model.dto;

import lombok.Data;
import mk.ukim.finki.usersservice.model.Role;
import mk.ukim.finki.usersservice.model.User;

@Data
public class UserDetailsDto {

    private String email;

    private Role role;

    public static UserDetailsDto of(User user) {
        UserDetailsDto userDetailsDto = new UserDetailsDto();

        userDetailsDto.setEmail(user.getEmail());
        userDetailsDto.setRole(user.getRole());

        return userDetailsDto;
    }
}
