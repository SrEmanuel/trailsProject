package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    @Transactional(readOnly = true)
    Optional<Subject> findByLinkName(String linkName);


}
