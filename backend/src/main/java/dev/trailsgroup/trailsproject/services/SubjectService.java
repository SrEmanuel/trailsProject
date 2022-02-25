package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.SubjectDTO;
import dev.trailsgroup.trailsproject.entities.Subject;
import dev.trailsgroup.trailsproject.entities.Topic;
import dev.trailsgroup.trailsproject.repositories.SubjectRepository;
import dev.trailsgroup.trailsproject.repositories.TopicRepository;
import dev.trailsgroup.trailsproject.services.exceptions.DatabaseException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class SubjectService {

    //TODO implement authentication and password encryption


    @Autowired
    private SubjectRepository repository;

    @Autowired
    private TopicRepository topicRepository;


    public Page<Subject> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Subject findById(Integer id){
        Optional<Subject> obj =  repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    //TODO verify that exception treatment
    public Subject insert(SubjectDTO obj){
        try {
            Topic topic = topicRepository.getById(obj.getTopicId());
            Subject subject = new Subject(null, obj.getName(), obj.getImage(), obj.getGrade(), obj.getHtmlContent(),obj.getPosition(), topic);
            topic.getId(); //an alternative method to make catch capture the EntityNotFoundException when thrown
            return repository.save(subject);
        }catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
            throw new ResourceNotFoundException(obj.getTopicId());

        }
    }

    public void delete(Integer id){
        try{
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }catch  (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public Subject update(Integer id, Subject obj){
        try{
            Subject SubjectDatabase = repository.getById(id);
            subjectUpdateInformation(SubjectDatabase, obj);
            return repository.save(SubjectDatabase);
        }catch(Exception e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void subjectUpdateInformation(Subject subjectDataBase, Subject obj){
        subjectDataBase.setName(obj.getName());
        subjectDataBase.setGrade(obj.getGrade());
        subjectDataBase.setImage(obj.getImage());
        subjectDataBase.setHtmlContent(obj.getHtmlContent());
        subjectDataBase.setPosition(obj.getPosition());
    }

}
