package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.UserCourseDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.entities.UserCourse;
import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import dev.trailsgroup.trailsproject.entities.pk.UserCoursePK;
import dev.trailsgroup.trailsproject.repositories.UserCourseRepository;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCourseService {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserCourseRepository userCourseRepository;

    public UserCourseDTO insert(Integer userId, Integer courseId){
        User user = userService.findById(userId);
        Course course = courseService.findById(courseId);
        UserCourse userCourse = new UserCourse(course, user);

        user.addProfile(UserProfiles.PROFESSOR);
        userService.save(user);
        userCourseRepository.save(userCourse);
        UserCourseDTO response = new UserCourseDTO();
        response.setUserId(userId);
        response.setCourseId(courseId);
        return response;
    }

    public void delete(Integer userId, Integer courseId){
        UserCoursePK pk = new UserCoursePK();
        pk.setUser(userService.findById(userId));
        pk.setCourse(courseService.findById(courseId));

        UserCourse userCourse = userCourseRepository.findById(pk)
                .orElseThrow(() -> new ResourceNotFoundException("O curso "+
                        courseId + " não está associado ao usuário " + userId));

        userCourseRepository.delete(userCourse);
    }

    public UserCourse findById(UserCoursePK userCoursePK) {
        return userCourseRepository.findById(userCoursePK).orElseThrow(()
                -> new ResourceNotFoundException("Não foi possível encontrar a relação do usuário ID:"
                + userCoursePK.getUser().getId()+ "com o Curso ID: "+
                userCoursePK.getCourse().getId()));
    }

    public Optional<UserCourse> findByIdOptional(UserCoursePK userCoursePK){
        return userCourseRepository.findById(userCoursePK);
    }

    public UserCourse save(UserCourse userCourse) {
        return userCourseRepository.save(userCourse);
    }


}
