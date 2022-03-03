package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.CourseDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.repositories.CourseRepository;
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
public class CourseService {

    //TODO implement authentication and password encryption

    //TODO IMPLEMENT ADD COURSE ENDPOINT

    @Autowired
    private CourseRepository repository;



    @Autowired
    private TopicRepository topicRepository;

    public Page<Course> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Course findById(Integer id){
        Optional<Course> obj =  repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Course insert(CourseDTO obj, String image){
        return repository.save(new Course(null, obj.getName(), image));
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

    public Course update(Integer id, CourseDTO obj){
        try{
            Course courseDatabase = repository.getById(id);
            courseUpdateInformation(courseDatabase, obj);
            return repository.save(courseDatabase);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void courseUpdateInformation(Course courseDataBase, CourseDTO obj){
        courseDataBase.setName(obj.getName());
        courseDataBase.setImage(obj.getImage());
    }


}
