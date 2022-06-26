package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.Competence;
import dev.trailsgroup.trailsproject.entities.StudentCompetence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetenceRepository extends JpaRepository<Competence, Integer> {
}
