package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
