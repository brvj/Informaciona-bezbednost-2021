package ib.project.repository;

import ib.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByEmailIgnoreCaseContaining(String email);
    List<User> findByActive(boolean active);
}
