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
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

@Service
public class CourseService {


    @Autowired
    private CourseRepository repository;

    @Autowired
    private StaticFileService staticFileService;

    @Autowired
    private TopicRepository topicRepository;

    public Page<Course> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Course findById(Integer id){
        Optional<Course> obj =  repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Course insert(CourseDTO obj, MultipartFile image){
        String fileName = "default-course.png";
        if(!(image==null))
            fileName = staticFileService.save(image);
        return repository.save(new Course(null, obj.getName(), fileName));
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

    public Course update(Integer id, CourseDTO obj, MultipartFile image){
        try{
            Course courseDatabase = repository.getById(id);
            courseUpdateInformation(courseDatabase, obj, image);
            return repository.save(courseDatabase);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void courseUpdateInformation(Course courseDataBase, CourseDTO obj, MultipartFile image){
        courseDataBase.setName(obj.getName());
        if(!(image==null)){
            String oldName = courseDataBase.getImageName();
            String newName = staticFileService.update(image, oldName);
            courseDataBase.setImage(newName);
        }
    }
}
