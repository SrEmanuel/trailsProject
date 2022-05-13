package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.UserSubject;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface UserSubjectRepository extends JpaRepository<UserSubject, Integer> {
}
