package dev.trailsgroup.trailsproject.repositories;


import dev.trailsgroup.trailsproject.entities.ProfessorCourse;
import dev.trailsgroup.trailsproject.entities.pk.UserCoursePK;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface ProfessorCourseRepository extends JpaRepository<ProfessorCourse, UserCoursePK> {

}
