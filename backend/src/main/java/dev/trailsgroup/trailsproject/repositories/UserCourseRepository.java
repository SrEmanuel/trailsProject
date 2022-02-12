package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.UserCourse;
import dev.trailsgroup.trailsproject.entities.pk.UserCoursePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseRepository extends JpaRepository<UserCourse, UserCoursePK> {
}
