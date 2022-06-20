package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.InputProfessorCourseDTO;
import dev.trailsgroup.trailsproject.dto.UserCourseDTO;
import dev.trailsgroup.trailsproject.entities.*;
import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import dev.trailsgroup.trailsproject.entities.pk.UserCoursePK;
import dev.trailsgroup.trailsproject.repositories.ProfessorCourseRepository;
import dev.trailsgroup.trailsproject.repositories.StudentCourseRepository;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCourseService {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ProfessorCourseRepository professorCourseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public UserCourseDTO insertProfessorCourse(Integer userId, Integer courseId){
        User user = userService.findById(userId);
        Course course = courseService.findById(courseId);
        ProfessorCourse userCourse = new ProfessorCourse(course, user);

        user.addProfile(UserProfiles.PROFESSOR);
        userService.save(user);
        professorCourseRepository.save(userCourse);
        UserCourseDTO response = new UserCourseDTO();
        response.setUserId(userId);
        response.setCourseId(courseId);
        return response;
    }

    public void deleteProfessorCourse(Integer userId, Integer courseId){
        ProfessorCourse professorCourse = new ProfessorCourse(courseService.findById(courseId), userService.findById(userId));
        professorCourseRepository.delete(professorCourse);
    }

    public ProfessorCourse findProfessorCourseById(UserCoursePK userCoursePK) {
        ProfessorCourse professorCourse = new ProfessorCourse(userCoursePK.getCourse(), userCoursePK.getUser());
        return professorCourseRepository.findOne(Example.of(professorCourse)).orElseThrow(() -> new ResourceNotFoundException("Não foi possível encontrar a relação do usuário ID:"
                + userCoursePK.getUser().getId()+ "com o Curso ID: "+
                userCoursePK.getCourse().getId()));
    }

    public List<User> findProfessorsByCourse(Course course){
        ProfessorCourse professorCourse = new ProfessorCourse();
        professorCourse.setCourse(course);
        professorCourse.setUser(null);
        List<ProfessorCourse> professorCoursesList = professorCourseRepository.findAll(Example.of(professorCourse));

        List<User> users = new ArrayList<>();
        for(ProfessorCourse x : professorCoursesList){
            if(x.getUser().getProfiles().contains(UserProfiles.PROFESSOR))
                users.add(x.getUser());
        }

        return users;
    }

    public Optional<StudentCourse> findStudentCourse(StudentCourse studentCourse){
        return studentCourseRepository.findOne(Example.of(studentCourse));
    }

    public Optional<ProfessorCourse> findProfessorCourse(ProfessorCourse professorCourse) {
        return professorCourseRepository.findOne(Example.of(professorCourse));
    }


    public ProfessorCourse saveProfessorCourse(ProfessorCourse professorCourse) {
        return professorCourseRepository.save(professorCourse);
    }

    public StudentCourse saveStudentCourse(StudentCourse studentCourse) {
        return studentCourseRepository.save(studentCourse);
    }


    public void saveProfessors(List<InputProfessorCourseDTO> professors, Course course){
        ProfessorCourse professorCourse = new ProfessorCourse();
        professorCourse.setCourse(course);
        List<ProfessorCourse> userCourseList = professorCourseRepository.findAll(Example.of(professorCourse));

        List<User> updatedUserList = new ArrayList<>();
        List<User> bdUserList = new ArrayList<>();

        professors.forEach(x -> updatedUserList.add(userService.findById(x.getId())));
        userCourseList.forEach(x -> bdUserList.add(x.getUser()));

        for(User x : bdUserList){
            if(!updatedUserList.contains(x)){
                ProfessorCourse us = new ProfessorCourse();
                us.setUser(x);
                us.setCourse(course);
                professorCourseRepository.delete(us);
                if(!verifyProfessorEligibility(x) && !x.getProfiles().contains(UserProfiles.ADMIN))
                    userService.removeProfile(x, UserProfiles.PROFESSOR);
            }
        }
        for(User x : updatedUserList){
            if(!bdUserList.contains(x)){
                userService.makeProfessor(x);
                ProfessorCourse us = new ProfessorCourse();
                us.setCourse(course);
                us.setUser(x);
                professorCourseRepository.save(us);

            }
        }
        userService.flush();
        professorCourseRepository.flush();
    }

    public boolean verifyProfessorEligibility(User user){
        ProfessorCourse professorCourse = new ProfessorCourse();
        professorCourse.setUser(user);

        return professorCourseRepository.exists(Example.of(professorCourse));
    }


}
