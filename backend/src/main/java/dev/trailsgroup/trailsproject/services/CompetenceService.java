package dev.trailsgroup.trailsproject.services;


import dev.trailsgroup.trailsproject.entities.Competence;
import dev.trailsgroup.trailsproject.entities.StudentCompetence;
import dev.trailsgroup.trailsproject.repositories.CompetenceRepository;
import dev.trailsgroup.trailsproject.repositories.StudentCompetenceRepository;
import dev.trailsgroup.trailsproject.services.exceptions.DatabaseException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CompetenceService {

    @Autowired
    CompetenceRepository repository;


    public Page<Competence> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Competence findById(Integer id){
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Identificador '" + id + "' não foi encontrado no sistema"));
    }

    public Competence insert(Competence obj){
        try{
            return save(obj);
        }catch (IllegalArgumentException | NullPointerException e){
            throw new DatabaseException(e.getMessage());
        }

    }

    public Competence save(Competence studentCompetence){
        return repository.save(studentCompetence);
    }

    public void delete(Integer id){
        try{
            Optional<Competence> competence = repository.findById(id);
            competence.ifPresent(value -> repository.delete(value));
        }catch  (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public Competence update(Integer id, Competence competence) {
        Competence bdCompetence = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso "+ id + " não encontrado"));

        bdCompetence.setName(competence.getName());
        bdCompetence.setDescription(competence.getDescription());
        return save(bdCompetence);
    }
}
