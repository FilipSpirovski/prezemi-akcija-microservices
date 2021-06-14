package mk.ukim.finki.usersservice.model.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RegistrationDto {

    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 16)
    private String password;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 16)
    private String confirmPassword;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "ROLE_USER|ROLE_ADMIN")
    private String role;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String surname;
}
