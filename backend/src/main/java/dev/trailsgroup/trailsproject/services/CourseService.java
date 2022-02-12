package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.repositories.CourseRepository;
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
public class CourseService {

    //TODO implement authentication and password encryption

    //TODO IMPLEMENT ADD COURSE ENDPOINT

    @Autowired
    private CourseRepository repository;

    public List<Course> findAll(){
        return repository.findAll();
    }

    public Course findById(Integer id){
        Optional<Course> obj =  repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Course insert(Course obj){
        return repository.save(obj);
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

    public Course update(Integer id, Course obj){
        try{
            Course courseDatabase = repository.getById(id);
            courseUpdateInformation(courseDatabase, obj);
            return repository.save(courseDatabase);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void courseUpdateInformation(Course courseDataBase, Course obj){
        courseDataBase.setName(obj.getName());
        courseDataBase.setImage(obj.getImage());
    }

}