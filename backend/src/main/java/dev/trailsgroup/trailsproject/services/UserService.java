package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.UserDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.entities.UserCourse;
import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import dev.trailsgroup.trailsproject.entities.enums.UserType;
import dev.trailsgroup.trailsproject.entities.pk.UserCoursePK;
import dev.trailsgroup.trailsproject.repositories.UserCourseRepository;
import dev.trailsgroup.trailsproject.repositories.UserRepository;
import dev.trailsgroup.trailsproject.security.UserSS;
import dev.trailsgroup.trailsproject.services.exceptions.AuthorizationException;
import dev.trailsgroup.trailsproject.services.exceptions.DatabaseException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private Pbkdf2PasswordEncoder pe;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserCourseRepository userCourseRepository;



    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Integer id){

        UserSS user = UserService.authenticated();
        if(user==null || !user.hasRole(UserProfiles.ADMIN) && !id.equals(user.getId())){
            throw new AuthorizationException("Acesso negado!");
        }

        Optional<User> obj =  repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User findBySession(){
        return repository.findById(authenticated().getId()).orElseThrow(() -> new ResourceNotFoundException("Houve" +
                "um problema!"));

    }

    public static UserSS authenticated(){
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch(Exception e){
            return null;
        }
    }

    public boolean verifyPermission(Course course){
        User user = repository.findByEmail(authenticated().getUsername()).get();
        UserCoursePK userCoursePK = new UserCoursePK();
        userCoursePK.setCourse(course);
        userCoursePK.setUser(user);
        return userCourseRepository.findById(userCoursePK).isPresent();
    }

    public User insert(UserDTO obj){
        try {
            if(!verifyEmailAvailability(obj.getEmail()))
                throw new DatabaseException("O email já foi cadastrado no sistema! Informe outro email.");

            User user = new User(null, obj.getName(), pe.encode(obj.getPassword()), obj.getEmail(), UserType.toEnum(obj.getType()), obj.getStatus());
            return repository.save(user);
        }catch (DataIntegrityViolationException e ){
            throw new DatabaseException(e.getMessage());
        }
    }

    public boolean verifyEmailAvailability(String email){
        return repository.findByEmail(email) == null;
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

    public User update(Integer id, UserDTO obj){
        try{
            User userDatabase = repository.getById(id);
            userUpdateInformation(userDatabase, obj);
            return repository.save(userDatabase);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void userUpdateInformation(User userDataBase, UserDTO obj){
        userDataBase.setName(obj.getName());
        userDataBase.setEmail(obj.getEmail());
        userDataBase.setStatus(obj.getStatus());
        userDataBase.setType(obj.getType());
        userDataBase.setPassword(pe.encode(obj.getPassword()));
    }

    //Solution:
    //https://stackoverflow.com/questions/37749559/conversion-of-list-to-page-in-spring
    public Page<Course> getCourses(Integer id, Pageable pageable){
        UserSS user = UserService.authenticated();

        if(user == null || !Objects.equals(user.getId(), id) && !user.hasRole(UserProfiles.ADMIN)){
            throw new AuthorizationException("Acesso negado!");
        }
        List<Course> courses = new ArrayList<>(repository.getById(id).getCourses());
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), courses.size());
        //This solution doesn't allow Sort with Pageable. Need to see another way.
        //https://stackoverflow.com/questions/56946999/can-we-sort-listt-from-spring-boot-pageable-or-sort-page-from-pageimpl
        return new PageImpl<Course>(courses.subList(start, end), pageable, courses.size());
    }
}
