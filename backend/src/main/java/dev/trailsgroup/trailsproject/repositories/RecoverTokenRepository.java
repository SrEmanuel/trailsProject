package dev.trailsgroup.trailsproject.repositories;


import dev.trailsgroup.trailsproject.entities.RecoverToken;
import dev.trailsgroup.trailsproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RecoverTokenRepository extends JpaRepository<RecoverToken, Integer> {

    @Transactional(readOnly = true)
    RecoverToken findByUser(User user);
}
