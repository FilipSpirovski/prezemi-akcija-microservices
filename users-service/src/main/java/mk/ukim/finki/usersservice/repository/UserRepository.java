package mk.ukim.finki.usersservice.repository;

import mk.ukim.finki.usersservice.model.Role;
import mk.ukim.finki.usersservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllByRole(Role role);

    Optional<User> findByEmail(String userEmail);
}
