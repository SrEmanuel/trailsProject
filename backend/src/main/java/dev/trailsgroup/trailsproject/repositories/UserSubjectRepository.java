package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSubjectRepository extends JpaRepository<UserSubject, Integer> {
}
