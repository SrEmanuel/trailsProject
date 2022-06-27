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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

    public QuestionCompetence save(Integer questionId, QuestionCompetenceDTO questionCompetence){
        Question question = questionService.findById(questionCompetence.getCompetenceId());
        Competence competence = competenceService.findById(questionId);
        QuestionCompetence questionCompetenceSave = new QuestionCompetence(null, question, competence);
        return repository.save(questionCompetenceSave);
    }

    public void delete(Integer id){
        try{
            QuestionCompetence questionCompetence = repository.findById(id).orElseThrow(()
                    -> new ResourceNotFoundException("Identificador '" + id + "' para a pergunta não foi encontrado no sistema"));
            repository.delete(questionCompetence);
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
