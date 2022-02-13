package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.TopicDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.Topic;
import dev.trailsgroup.trailsproject.repositories.CourseRepository;
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
public class TopicService {

    //TODO implement authentication and password encryption

    //TODO IMPLEMENT ADD COURSE ENDPOINT

    @Autowired
    private TopicRepository repository;

    @Autowired
    private CourseRepository courseRepository;

    public List<Topic> findAll(){
        return repository.findAll();
    }

    public Topic findById(Integer id){
        Optional<Topic> obj =  repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Topic insert(TopicDTO obj){
        Course course = courseRepository.findById(obj.getCourseId()).get();
        Topic topic = new Topic(null, obj.getName(), obj.getPosition(), course);

        return repository.save(topic);
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

    public Topic update(Integer id, Topic obj){
        try{
            Topic topicDatabase = repository.getById(id);
            topicUpdateInformation(topicDatabase, obj);
            return repository.save(topicDatabase);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void topicUpdateInformation(Topic topicDataBase, Topic obj){
        topicDataBase.setName(obj.getName());
    }

}
