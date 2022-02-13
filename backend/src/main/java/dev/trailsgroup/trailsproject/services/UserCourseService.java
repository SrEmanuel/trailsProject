package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.UserCourseDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.entities.UserCourse;
import dev.trailsgroup.trailsproject.repositories.CourseRepository;
import dev.trailsgroup.trailsproject.repositories.UserCourseRepository;
import dev.trailsgroup.trailsproject.repositories.UserRepository;
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
        User user = userRepository.getById(userId);
        Course course = courseRepository.getById(courseId);
        UserCourse userCourse = new UserCourse(course, user);

        userCourseRepository.save(userCourse);
        UserCourseDTO response = new UserCourseDTO();
        response.setUserId(userId);
        response.setCourseId(courseId);
        return response;



    }

}