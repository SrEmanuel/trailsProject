package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional(readOnly = true)
    User findByEmail(String email);
}
