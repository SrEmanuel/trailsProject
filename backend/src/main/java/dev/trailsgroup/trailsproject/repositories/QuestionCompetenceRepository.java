package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.ProfessorCourse;
import dev.trailsgroup.trailsproject.entities.QuestionCompetence;
import dev.trailsgroup.trailsproject.entities.pk.UserCoursePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCompetenceRepository extends JpaRepository<QuestionCompetence, Integer> {
}
