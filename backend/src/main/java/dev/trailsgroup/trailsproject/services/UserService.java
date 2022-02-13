package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.UserCourseDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.repositories.CourseRepository;
import dev.trailsgroup.trailsproject.repositories.UserRepository;
import dev.trailsgroup.trailsproject.services.exceptions.DatabaseException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    //TODO implement authentication and password encryption

    //TODO IMPLEMENT ADD COURSE ENDPOINT

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Integer id){
        Optional<User> obj =  repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User obj){
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

    public User update(Integer id, User obj){
        try{
            User userDatabase = repository.getById(id);
            userUpdateInformation(userDatabase, obj);
            return repository.save(userDatabase);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void userUpdateInformation(User userDataBase, User obj){
        userDataBase.setName(obj.getName());
        userDataBase.setEmail(obj.getEmail());
        userDataBase.setStatus(obj.getStatus());
        userDataBase.setType(obj.getType());
        userDataBase.setPassword(obj.getPassword());
    }

    public Set<Course> getCourses(Integer id){
        Set<Course> courses = repository.getById(id).getCourses();
        return courses;
    }


}
