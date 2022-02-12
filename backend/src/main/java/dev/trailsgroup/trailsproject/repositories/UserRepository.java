package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
