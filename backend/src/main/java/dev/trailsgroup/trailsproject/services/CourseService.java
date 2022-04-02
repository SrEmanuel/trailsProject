package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.CourseDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import dev.trailsgroup.trailsproject.repositories.CourseRepository;
import dev.trailsgroup.trailsproject.security.UserSS;
import dev.trailsgroup.trailsproject.services.exceptions.AuthorizationException;
import dev.trailsgroup.trailsproject.services.exceptions.DatabaseException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import dev.trailsgroup.trailsproject.services.utils.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseRepository repository;

    @Autowired
    private StaticFileService staticFileService;

    public Page<Course> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Course findByName(String linkName){
        Optional<Course> obj = repository.findByLinkName(linkName);
        return obj.orElseThrow(() -> new ResourceNotFoundException(linkName));
    }

    public Course insert(CourseDTO obj, MultipartFile image){
        try {
            String linkName = ClassUtils.createLinkName(obj.getName());
            if(verifyLinkNameAvailability(linkName))
                throw new DatabaseException("O nome de curso '"+ obj.getName() +"' já existe no sistema! Informe outro nome diferente.");
            String fileName = "default-course.png";
            if (!(image == null))
                fileName = staticFileService.save(image);
            return repository.save(new Course(null, obj.getName(), fileName, linkName));
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public boolean verifyLinkNameAvailability(String name){
        return repository.findByLinkName(name).isPresent();
    }


    public void delete(String linkName) {
        try {
            Course course = repository.findByLinkName(linkName).orElseThrow(() -> new ResourceNotFoundException("Identificador '" + linkName + "' não foi encontrado no sistema"));
            verifyUserPermission(course);
            repository.delete(course);
        } catch(EntityNotFoundException | NoSuchElementException e){
            throw new ResourceNotFoundException(linkName);
        } catch(DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }


        public Course update(String linkName, CourseDTO obj, MultipartFile image){
        try{
            Course courseDatabase = repository.findByLinkName(linkName).orElseThrow(() -> new ResourceNotFoundException("Identificador '" + linkName + "' não foi encontrado no sistema"));
            verifyUserPermission(courseDatabase);
            courseUpdateInformation(courseDatabase, obj, image);
            return repository.save(courseDatabase);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(linkName);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public void courseUpdateInformation(Course courseDataBase, CourseDTO obj, MultipartFile image){
        String linkName = ClassUtils.createLinkName(obj.getName());
        if(verifyLinkNameAvailability(linkName) && !Objects.equals(linkName, courseDataBase.getLinkName()))
            throw new DatabaseException("O nome de curso '"+ obj.getName() +"' já existe no sistema! Informe um nome diferente.");

        courseDataBase.setName(obj.getName());
        courseDataBase.setLinkName(linkName);
        if(!(image==null)){
            String oldName = courseDataBase.getImageName();
            String newName = staticFileService.update(image, oldName);
            courseDataBase.setImage(newName);
        }
    }

    private void verifyUserPermission(Course obj) throws NullPointerException {
        UserSS userss = UserService.authenticated();
        if(!userService.verifyPermission(obj) && !userss.hasRole(UserProfiles.ADMIN)){
            throw new AuthorizationException("Você não é professor deste curso para realizar essa ação!");
        }
    }

}
