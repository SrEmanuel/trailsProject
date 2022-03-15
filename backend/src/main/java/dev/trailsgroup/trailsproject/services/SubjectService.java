package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.SubjectDTO;
import dev.trailsgroup.trailsproject.entities.Subject;
import dev.trailsgroup.trailsproject.entities.Topic;
import dev.trailsgroup.trailsproject.entities.UserSubject;
import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import dev.trailsgroup.trailsproject.repositories.SubjectRepository;
import dev.trailsgroup.trailsproject.repositories.TopicRepository;
import dev.trailsgroup.trailsproject.repositories.UserSubjectRepository;
import dev.trailsgroup.trailsproject.security.UserSS;
import dev.trailsgroup.trailsproject.services.exceptions.AuthorizationException;
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
import javax.validation.constraints.Null;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubjectService {

    //TODO implement authentication and password encryption


    @Autowired
    private SubjectRepository repository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserSubjectRepository userSubjectRepository;

    @Autowired
    private StaticFileService staticFileService;

    @Autowired
    private UserService userService;

    public Page<Subject> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Subject findById(Integer id){
        Optional<Subject> obj =  repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Subject insert(SubjectDTO obj, MultipartFile imageFile){
        try {
            String fileName = staticFileService.save(imageFile);
            Topic topic = topicRepository.findById(obj.getTopicId()).orElseThrow(() -> new ResourceNotFoundException(obj.getTopicId()));
            Subject subject = new Subject(null, obj.getName(), fileName, obj.getGrade(), obj.getHtmlContent(),obj.getPosition(), topic);
            Subject savedSubject = repository.save(subject);
            verifyUserPermission(savedSubject);
            return savedSubject;
        }catch(IllegalArgumentException | NullPointerException e){
            throw new DatabaseException(e.getMessage());

        }
    }


    public void delete(Integer id){
        try{
            Subject subject = repository.getById(id);
            verifyUserPermission(subject);
            repository.delete(subject);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }catch  (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public Subject update(Integer id, SubjectDTO obj, MultipartFile image){
        try{
            Subject SubjectDatabase = repository.getById(id);
            verifyUserPermission(SubjectDatabase);
            subjectUpdateInformation(SubjectDatabase, obj, image);
            return repository.save(SubjectDatabase);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void subjectUpdateInformation(Subject subjectDataBase, SubjectDTO obj, MultipartFile image){
        subjectDataBase.setName(obj.getName());
        subjectDataBase.setGrade(obj.getGrade());
        if(!(image==null)){
            String oldName = subjectDataBase.getImageName();
            String newName = staticFileService.update(image, oldName);
            subjectDataBase.setImage(newName);
        }
        subjectDataBase.setHtmlContent(obj.getHtmlContent());
        subjectDataBase.setPosition(obj.getPosition());
    }


    private void verifyUserPermission(Subject obj) throws NullPointerException {
        UserSS userss = UserService.authenticated();
        if(!userService.verifyPermission(obj.getTopic().getCourse()) && !userss.hasRole(UserProfiles.ADMIN)){
            throw new AuthorizationException("Você não é professor do curso relacionado a este tópico para realizar essa ação");
        }
        String name = Objects.requireNonNull(userss).getName();
        String email = Objects.requireNonNull(userss).getUsername();
        userSubjectRepository.save(new UserSubject(obj,name, email));
    }

}
