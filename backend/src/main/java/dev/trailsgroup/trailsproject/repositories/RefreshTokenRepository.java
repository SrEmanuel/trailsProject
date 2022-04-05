package dev.trailsgroup.trailsproject.repositories;


import dev.trailsgroup.trailsproject.entities.RefreshToken;
import dev.trailsgroup.trailsproject.entities.Subject;
import dev.trailsgroup.trailsproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    @Transactional(readOnly = true)
    RefreshToken findByUser(User user);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
