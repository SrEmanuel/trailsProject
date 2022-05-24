package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.UserCourseDTO;
import dev.trailsgroup.trailsproject.entities.*;
import dev.trailsgroup.trailsproject.entities.pk.UserCoursePK;
import dev.trailsgroup.trailsproject.repositories.ProfessorSubjectRepository;
import dev.trailsgroup.trailsproject.repositories.StudentSubjectRepository;
import dev.trailsgroup.trailsproject.repositories.UserCourseRepository;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSubjectService {

    @Autowired
    ProfessorSubjectRepository professorSubjectRepository;

    @Autowired
    StudentSubjectRepository studentSubjectRepository;


    public Example<? extends UserSubject> getExample(List<? extends UserSubject> list){
        return Example.of(list.get(0));
    }

    public boolean existsProfessor(ProfessorSubject professorSubject){
        Example<ProfessorSubject> example = Example.of(professorSubject);
        return professorSubjectRepository.exists(example);
    }

    public boolean existsStudent(StudentSubject studentSubject){
        Example<StudentSubject> example = Example.of(studentSubject);
        return studentSubjectRepository.exists(example);
    }


    public ProfessorSubject findOneProfessor(ProfessorSubject professorSubject) {
        return professorSubjectRepository.findOne(getProfessorExample(professorSubject)).get();
    }

    private Example<ProfessorSubject> getProfessorExample(ProfessorSubject professorSubject) {
        return Example.of(professorSubject);
    }

    public void saveProfessor(ProfessorSubject professorSubject){
        professorSubjectRepository.save(professorSubject);
    }

    public Optional<StudentSubject> findBySubjectAndUser(Subject subject, User user) {
        return studentSubjectRepository.findBySubjectAndUser(subject,user);
    }

    public void saveStudent(StudentSubject studentSubject) {
        studentSubjectRepository.save(studentSubject);
    }
}
