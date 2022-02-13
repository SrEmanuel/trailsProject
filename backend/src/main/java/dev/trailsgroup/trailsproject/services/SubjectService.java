package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.SubjectDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.Subject;
import dev.trailsgroup.trailsproject.entities.Topic;
import dev.trailsgroup.trailsproject.repositories.CourseRepository;
import dev.trailsgroup.trailsproject.repositories.SubjectRepository;
import dev.trailsgroup.trailsproject.repositories.TopicRepository;
import dev.trailsgroup.trailsproject.services.exceptions.DatabaseException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    //TODO implement authentication and password encryption


    @Autowired
    private SubjectRepository repository;

    @Autowired
    private TopicRepository topicRepository;


    public List<Subject> findAll(){
        return repository.findAll();
    }

    public Subject findById(Integer id){
        Optional<Subject> obj =  repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Subject insert(SubjectDTO obj){
        Topic topic = topicRepository.getById(obj.getTopicId());
        Subject subject = new Subject(null, obj.getName(), obj.getImage(), obj.getGrade(), obj.getHtmlContent(), topic);

        return repository.save(subject);
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
            SubjectUpdateInformation(SubjectDatabase, obj);
            return repository.save(SubjectDatabase);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void SubjectUpdateInformation(Subject subjectDataBase, Subject obj){
        subjectDataBase.setName(obj.getName());
    }

}
