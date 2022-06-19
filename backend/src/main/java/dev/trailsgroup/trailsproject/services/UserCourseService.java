package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.InputProfessorCourseDTO;
import dev.trailsgroup.trailsproject.dto.UserCourseDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.entities.UserCourse;
import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import dev.trailsgroup.trailsproject.entities.pk.UserCoursePK;
import dev.trailsgroup.trailsproject.repositories.UserCourseRepository;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<User> findProfessorsByCourse(Course course){
        UserCourse userCourse = new UserCourse();
        userCourse.setCourse(course);
        List<UserCourse> userCourseList = userCourseRepository.findAll(Example.of(userCourse));

        List<User> users = new ArrayList<>();
        for(UserCourse x : userCourseList){
            if(x.getUser().getProfiles().contains(UserProfiles.PROFESSOR))
                users.add(x.getUser());
        }

        return users;
    }

    public Optional<UserCourse> findByIdOptional(UserCoursePK userCoursePK){
        return userCourseRepository.findById(userCoursePK);
    }

    public UserCourse save(UserCourse userCourse) {
        return userCourseRepository.save(userCourse);
    }


    public void saveProfessors(List<InputProfessorCourseDTO> professors, Course course){
        UserCourse userCourse = new UserCourse();
        userCourse.setCourse(course);
        List<UserCourse> userCourseList = userCourseRepository.findAll(Example.of(userCourse));

        List<User> updatedUserList = new ArrayList<>();
        List<User> bdUserList = new ArrayList<>();

        professors.forEach(x -> updatedUserList.add(userService.findById(x.getId())));
        userCourseList.forEach(x -> bdUserList.add(x.getUser()));

        for(User x : bdUserList){
            if(!updatedUserList.contains(x)){
                UserCourse us = new UserCourse();
                us.setUser(x);
                us.setCourse(course);
                userCourseRepository.delete(us);
                //TODO remove user professor role if he doesn't have more UserCourse relations as professor;
            }
        }
        for(User x : updatedUserList){
            if(!bdUserList.contains(x)){
                userService.makeProfessor(x);
                UserCourse us = new UserCourse();
                us.setCourse(course);
                us.setUser(x);
                userCourseRepository.save(us);

            }
        }
        userService.flush();
        userCourseRepository.flush();
    }
}
