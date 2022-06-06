package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.UserCourse;
import dev.trailsgroup.trailsproject.entities.pk.UserCoursePK;
import dev.trailsgroup.trailsproject.services.UserCourseService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, UserCoursePK> {
}
