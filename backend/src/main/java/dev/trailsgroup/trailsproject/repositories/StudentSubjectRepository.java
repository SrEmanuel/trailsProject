package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.StudentSubject;
import dev.trailsgroup.trailsproject.entities.Subject;
import dev.trailsgroup.trailsproject.entities.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface StudentSubjectRepository extends UserSubjectRepository {

    @Transactional(readOnly = true)
    Optional<StudentSubject> findBySubjectAndUser(Subject subject, User user);
}
