package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
