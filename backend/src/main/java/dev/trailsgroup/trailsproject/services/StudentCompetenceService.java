package dev.trailsgroup.trailsproject.services;


import dev.trailsgroup.trailsproject.dto.CompetencePointsDTO;
import dev.trailsgroup.trailsproject.entities.*;
import dev.trailsgroup.trailsproject.repositories.QuestionCompetenceRepository;
import dev.trailsgroup.trailsproject.repositories.StudentCompetenceRepository;
import dev.trailsgroup.trailsproject.services.exceptions.DatabaseException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class StudentCompetenceService {

    @Autowired
    StudentCompetenceRepository repository;

    @Autowired
    UserService userService;

    @Autowired
    CompetenceService competenceService;

    @Autowired
    QuestionCompetenceService questionCompetenceService;

    public Page<StudentCompetence> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public StudentCompetence findById(Integer id){
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Identificador '" + id + "' não foi encontrado no sistema"));
    }

    public StudentCompetence insert(StudentCompetence obj){
        try{
            return save(obj);
        }catch (IllegalArgumentException | NullPointerException e){
            throw new DatabaseException(e.getMessage());
        }

    }

    public StudentCompetence save(StudentCompetence studentCompetence){
        return repository.save(studentCompetence);
    }

    public void delete(Integer id){
        try{
            StudentCompetence studentCompetence = repository.findById(id).orElseThrow(()
                    -> new ResourceNotFoundException("Identificador '" + id + "' para a pergunta não foi encontrado no sistema"));
            repository.delete(studentCompetence);
        }catch  (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public void setLoggedUserPoint(Question question){
        List<Competence> competences = questionCompetenceService.getCompetences(question);
        User user = userService.findBySession();

        List<StudentCompetence> list = new ArrayList<>();
        competences.forEach(x -> {
            StudentCompetence studentCompetence = new StudentCompetence();
            studentCompetence.setCompetence(x);
            studentCompetence.setUser(user);
            list.add(studentCompetence);
        });

        repository.saveAll(list);
    }


    public List<CompetencePointsDTO> getUserPoints(User user){
        StudentCompetence studentCompetence = new StudentCompetence();
        studentCompetence.setUser(user);
        List<StudentCompetence> studentCompetenceList = repository.findAll(Example.of(studentCompetence));

        Set<Competence> userCompetenceSet = new HashSet<>();
        studentCompetenceList.forEach(x -> {
            userCompetenceSet.add(x.getCompetence());
        });

        List<CompetencePointsDTO> competencePointsDTOList = new ArrayList<>();
        userCompetenceSet.forEach(x -> {
            QuestionCompetence questionCompetence = new QuestionCompetence();
            questionCompetence.setCompetence(x);
            long total = questionCompetenceService.countExample(Example.of(questionCompetence));


            StudentCompetence studentCompetenceEx = new StudentCompetence();
            studentCompetenceEx.setUser(user);
            studentCompetenceEx.setCompetence(x);
            long received  = repository.count(Example.of(studentCompetenceEx));

            competencePointsDTOList.add(new CompetencePointsDTO(x.getId(), x.getName(), x.getDescription(), (int) total, (int) received));
        });

        return competencePointsDTOList;
    }
}
