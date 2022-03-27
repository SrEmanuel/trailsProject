package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Transactional(readOnly = true)
    Optional<Course> findByLinkName(String linkName);

    @Transactional(readOnly = true)
    Optional<Course> findByName(String name);

}
