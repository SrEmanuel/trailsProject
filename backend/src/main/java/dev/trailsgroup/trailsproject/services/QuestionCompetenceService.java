package dev.trailsgroup.trailsproject.services;


import dev.trailsgroup.trailsproject.dto.*;
import dev.trailsgroup.trailsproject.entities.*;
import dev.trailsgroup.trailsproject.repositories.QuestionCompetenceRepository;
import dev.trailsgroup.trailsproject.repositories.QuestionRepository;
import dev.trailsgroup.trailsproject.services.exceptions.DatabaseException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class QuestionCompetenceService {

    @Autowired
    QuestionCompetenceRepository repository;

    @Autowired
    QuestionService questionService;

    @Autowired
    CompetenceService competenceService;

    public Page<QuestionCompetence> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public QuestionCompetence findById(Integer id){
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Identificador '" + id + "' não foi encontrado no sistema"));
    }

    public QuestionCompetence insert(Integer questionId, QuestionCompetenceDTO obj){
        try{
            return save(questionId, obj);
        }catch (IllegalArgumentException | NullPointerException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    private QuestionCompetence internalSave(QuestionCompetence questionCompetence){
        return repository.save(questionCompetence);
    }

    public QuestionCompetence save(Integer questionId, QuestionCompetenceDTO questionCompetence){
        Question question = questionService.findById(questionCompetence.getCompetenceId());
        Competence competence = competenceService.findById(questionId);
        QuestionCompetence questionCompetenceSave = new QuestionCompetence(null, question, competence);
        return repository.save(questionCompetenceSave);
    }

    public QuestionCompetence findOne(Example<QuestionCompetence> of){

        Optional<QuestionCompetence> questionCompetence = repository.findOne(of);

        if(questionCompetence.isPresent()){
            return questionCompetence.get();
        }
        return null;
    }
    
    public void updateRelations(Question question, Set<CompetenceOnQuestionDTO> competences) {

        //This for is a method to verify if the competences informed are valid;
        Set<Competence> dbCompetences = new HashSet<>();
        competences.forEach(x -> {
            dbCompetences.add(competenceService.findById(x.getId()));
        });

        competences.forEach(x -> {
            if (x.getOperation().equalsIgnoreCase("ADD")) {
                QuestionCompetence questionCompetence = new QuestionCompetence(null, question, competenceService.findById(x.getId()));
                internalSave(questionCompetence);
            }

            if (x.getOperation().equalsIgnoreCase("REMOVE")) {
                Competence competence = competenceService.findById(x.getId());
                QuestionCompetence questionCompetence = new QuestionCompetence();
                questionCompetence.setCompetence(competence);
                questionCompetence.setQuestion(question);

                QuestionCompetence questionCompetenceBd = findOne(Example.of(questionCompetence));
                if(questionCompetenceBd != null){
                    delete(questionCompetenceBd.getId());
                }

            }
        });

    }

    public List<QuestionCompetence> saveAll(Integer questionId, List<QuestionCompetenceDTO> questionCompetence){
        Competence competence = competenceService.findById(questionId);

        List<QuestionCompetence> questionCompetenceList = new ArrayList<>();

        questionCompetence.forEach(x -> {
            Question question = questionService.findById(x.getCompetenceId());
            QuestionCompetence questionCompetenceSave = new QuestionCompetence(null, question, competence);
            questionCompetenceList.add(questionCompetenceSave);
        });

        return repository.saveAll(questionCompetenceList);
    }

    public void delete(Integer id){
        try{
            Optional<QuestionCompetence> questionCompetence = repository.findById(id);
            questionCompetence.ifPresent(value -> repository.delete(value));
        }catch  (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }
    
    

    public List<Competence> getCompetences(Question question){
        QuestionCompetence questionCompetence = new QuestionCompetence();
        questionCompetence.setQuestion(question);
        List<QuestionCompetence> questionCompetenceList = repository.findAll(Example.of(questionCompetence));

        List<Competence> competences = new ArrayList<>();
        questionCompetenceList.forEach(x -> competences.add(x.getCompetence()));
        return competences;
    }

    public long countExample(Example<QuestionCompetence> of){
        return repository.count(of);
    }
}
