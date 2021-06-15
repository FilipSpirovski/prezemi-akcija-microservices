package mk.ukim.finki.usersservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mk.ukim.finki.usersservice.model.Role;
import mk.ukim.finki.usersservice.model.User;
import mk.ukim.finki.usersservice.model.dto.LoginDto;
import mk.ukim.finki.usersservice.model.dto.RegistrationDto;
import mk.ukim.finki.usersservice.model.dto.UserDetailsDto;
import mk.ukim.finki.usersservice.model.exception.*;
import mk.ukim.finki.usersservice.model.utility.JwtAuthConstants;
import mk.ukim.finki.usersservice.repository.UserRepository;
import mk.ukim.finki.usersservice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public List<User> findAllByRole(String roleName) throws InvalidRoleName {
        Role role = this.validateRoleName(roleName);

        return this.userRepository.findAllByRole(role);
    }

    @Override
    public User findByEmail(String userEmail) {
        return this.userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFound(userEmail));
    }

    @Override
    public String registerUser(RegistrationDto registrationDto) throws ConstraintViolationException,
            PasswordsDoNotMatch, UserAlreadyExists, JsonProcessingException {
        this.checkRegistrationDtoForViolations(registrationDto);
        registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        User newUser = this.userRepository.save(new User(registrationDto));

        return this.generateToken(newUser);
    }

    @Override
    public String loginUser(LoginDto loginDto) throws ConstraintViolationException, JsonProcessingException,
            UserNotFound {
        this.checkLoginDtoForViolations(loginDto);
        String userEmail = loginDto.getEmail();
        String userPassword = loginDto.getPassword();
        User existingUser = this.findByEmail(userEmail);

        if (!passwordEncoder.matches(userPassword, existingUser.getPassword())) {
            throw new InvalidCredentials();
        }

        return this.generateToken(existingUser);
    }

    @Override
    public boolean deleteUser(String userEmail) throws UserNotFound {
        User existingUser = this.findByEmail(userEmail);

        this.userRepository.delete(existingUser);

        try {
            existingUser = this.findByEmail(userEmail);

            return false;
        } catch (UserNotFound e) {
            return true;
        }
    }

    private Role validateRoleName(String roleName) {
        boolean validRoleName = false;

        for (Role role : Role.values()) {
            if (role.toString().equals(roleName)) {
                validRoleName = true;
                break;
            }
        }

        if (validRoleName) {
            return Role.valueOf(roleName);
        } else {
            throw new InvalidRoleName(roleName);
        }
    }

    private void checkRegistrationDtoForViolations(RegistrationDto registrationDto) {
        var constraintViolations = this.validator.validate(registrationDto);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The provided 'Registration' object is not valid.", constraintViolations);
        }

        String password = registrationDto.getPassword();
        String confirmPassword = registrationDto.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            throw new PasswordsDoNotMatch();
        }

        try {
            User existingUser = this.findByEmail(registrationDto.getEmail());

            throw new UserAlreadyExists(existingUser);
        } catch (UserNotFound ignored) {
        }
    }

    private void checkLoginDtoForViolations(LoginDto loginDto) {
        var constraintViolations = this.validator.validate(loginDto);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The provided 'Login' object is not valid.", constraintViolations);
        }
    }

    private String generateToken(User user) throws JsonProcessingException {
        return JWT.create()
                .withSubject(new ObjectMapper().writeValueAsString(UserDetailsDto.of(user)))
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtAuthConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(JwtAuthConstants.SECRET.getBytes()));
    }
}
