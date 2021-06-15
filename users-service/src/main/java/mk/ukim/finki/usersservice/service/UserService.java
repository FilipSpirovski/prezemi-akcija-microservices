package mk.ukim.finki.usersservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import mk.ukim.finki.usersservice.model.User;
import mk.ukim.finki.usersservice.model.dto.LoginDto;
import mk.ukim.finki.usersservice.model.dto.RegistrationDto;
import mk.ukim.finki.usersservice.model.dto.UserDetailsDto;

import java.util.List;

public interface UserService {

    List<User> findAll();

    List<User> findAllByRole(String roleName);

    User findByEmail(String userEmail);

    String registerUser(RegistrationDto registrationDto) throws JsonProcessingException;

    String loginUser(LoginDto loginDto) throws JsonProcessingException;

    boolean deleteUser(String userEmail);
}
