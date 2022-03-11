package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}
