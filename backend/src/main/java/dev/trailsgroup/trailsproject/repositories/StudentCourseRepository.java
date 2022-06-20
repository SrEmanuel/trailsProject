package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.StudentCourse;
import dev.trailsgroup.trailsproject.entities.pk.StudentCoursePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCoursePK> {
}
