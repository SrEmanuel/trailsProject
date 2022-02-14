package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.UserCourseDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.entities.UserCourse;
import dev.trailsgroup.trailsproject.entities.pk.UserCoursePK;
import dev.trailsgroup.trailsproject.repositories.CourseRepository;
import dev.trailsgroup.trailsproject.repositories.UserCourseRepository;
import dev.trailsgroup.trailsproject.repositories.UserRepository;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCourseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    public UserCourseDTO insert(Integer userId, Integer courseId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(userId));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(userId));
        UserCourse userCourse = new UserCourse(course, user);

        userCourseRepository.save(userCourse);
        UserCourseDTO response = new UserCourseDTO();
        response.setUserId(userId);
        response.setCourseId(courseId);
        return response;
    }

    public void delete(Integer userId, Integer courseId){
        UserCoursePK pk = new UserCoursePK();
        pk.setUser(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(userId)));
        pk.setCourse(courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(userId)));

        UserCourse userCourse = userCourseRepository.findById(pk).orElseThrow(()
                -> new ResourceNotFoundException("The course "+ courseId + " is NOT associated with user " + userId));

        userCourseRepository.delete(userCourse);
    }

}
