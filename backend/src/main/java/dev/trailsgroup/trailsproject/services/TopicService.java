package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.TopicDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.Subject;
import dev.trailsgroup.trailsproject.entities.Topic;
import dev.trailsgroup.trailsproject.entities.UserSubject;
import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import dev.trailsgroup.trailsproject.repositories.CourseRepository;
import dev.trailsgroup.trailsproject.repositories.TopicRepository;
import dev.trailsgroup.trailsproject.security.UserSS;
import dev.trailsgroup.trailsproject.services.exceptions.AuthorizationException;
import dev.trailsgroup.trailsproject.services.exceptions.DatabaseException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;

@Service
public class TopicService {

    //TODO implement authentication and password encryption

    @Autowired
    private UserService userService;

    @Autowired
    private TopicRepository repository;

    @Autowired
    private CourseRepository courseRepository;

    public Page<Topic> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Topic findById(Integer id){
        Optional<Topic> obj =  repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Topic insert(TopicDTO obj){
        if(!verifyPosition(obj.getPosition()))
            throw new DatabaseException("Já existe um topico com essa posição!");
        Course course = courseRepository.findById(obj.getCourseId()).orElseThrow(() -> new ResourceNotFoundException(obj.getCourseId()));
        Topic topic = new Topic(null, obj.getName(), obj.getPosition(), course);
        verifyUserPermission(topic);

        return repository.save(topic);
    }

    public void delete(Integer id){
        try{
            Topic topic = repository.getById(id);
            verifyUserPermission(topic);
            repository.delete(topic);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }catch  (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public Topic update(Integer id, TopicDTO obj){
        try{
            Topic topicDatabase = repository.getById(id);
            verifyUserPermission(topicDatabase);
            topicUpdateInformation(topicDatabase, obj);
            return repository.save(topicDatabase);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void topicUpdateInformation(Topic topicDataBase, TopicDTO obj){
        topicDataBase.setName(obj.getName());
        topicDataBase.setPosition(obj.getPosition());
    }

    public Page<Topic> getTopicsByCourse(Integer courseId, Pageable pageable){
        //I make an example of a topic with the referred course on it.
        //I use this Example to make a query in findAll, passing it alongside with pageable.
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(courseId));
        Topic topic = new Topic();
        topic.setCourse(course);
        Example<Topic> example = Example.of(topic);

        return repository.findAll(example, pageable);
    }

    public boolean verifyPosition(Integer num){
        Topic topic = new Topic();
        topic.setPosition(num);

        Example<Topic> example = Example.of(topic);

        int count = (int) repository.count(example);
        return count == 0;
    }

    private void verifyUserPermission(Topic obj) throws NullPointerException {
        UserSS userss = UserService.authenticated();
        if(!userService.verifyPermission(obj.getCourse()) && !userss.hasRole(UserProfiles.ADMIN)){
            throw new AuthorizationException("Você não é professor do curso relacionado a este tópico para realizar essa ação!");
        }
    }


}
