package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.CourseDTO;
import dev.trailsgroup.trailsproject.dto.TopicPositionDTO;
import dev.trailsgroup.trailsproject.entities.*;
import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import dev.trailsgroup.trailsproject.repositories.CourseRepository;
import dev.trailsgroup.trailsproject.repositories.UserCourseRepository;
import dev.trailsgroup.trailsproject.repositories.UserRepository;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private CourseRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private StaticFileService staticFileService;

    public Page<Course> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Course findByName(String linkName){
        Optional<Course> obj = repository.findByLinkName(linkName);
        return obj.orElseThrow(() -> new ResourceNotFoundException(linkName));
    }

    public Course insert(CourseDTO obj){
        try {
            String linkName = ClassUtils.createLinkName(obj.getName());
            if(verifyLinkNameAvailability(linkName))
                throw new DatabaseException("O nome de curso '"+ obj.getName() +"' já existe no sistema! Informe outro nome diferente.");
            String fileName = "default-course.png";
            User user = userRepository.findById(obj.getProfessorID()).orElseThrow(() -> new ResourceNotFoundException("O ID de professor informado não existe."));
            Course course = repository.save(new Course(null, obj.getName(), fileName, linkName));
            userCourseRepository.save(new UserCourse(course, user));
            return course;
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
            staticFileService.delete(course.getImageName());
            repository.delete(course);
        } catch(EntityNotFoundException | NoSuchElementException e){
            throw new ResourceNotFoundException(linkName);
        } catch(DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }


        public Course update(String linkName, CourseDTO obj){
        try{
            Course courseDatabase = repository.findByLinkName(linkName).orElseThrow(() -> new ResourceNotFoundException("Identificador '" + linkName + "' não foi encontrado no sistema"));
            verifyUserPermission(courseDatabase);
            courseUpdateInformation(courseDatabase, obj);
            return repository.save(courseDatabase);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(linkName);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public void courseUpdateInformation(Course courseDataBase, CourseDTO obj){
        String linkName = ClassUtils.createLinkName(obj.getName());
        if(verifyLinkNameAvailability(linkName) && !Objects.equals(linkName, courseDataBase.getLinkName()))
            throw new DatabaseException("O nome de curso '"+ obj.getName() +"' já existe no sistema! Informe um nome diferente.");

        courseDataBase.setName(obj.getName());
        courseDataBase.setLinkName(linkName);
    }

    public Course insertImage(MultipartFile multipartFile, String linkName){
        Course course = repository.findByLinkName(linkName).orElseThrow(()-> new ResourceNotFoundException("Identificador '" + linkName + "' não foi encontrado no sistema"));
        course.setImage(staticFileService.update(multipartFile, course.getImageName()));
        repository.save(course);
        return course;
    }

    private void verifyUserPermission(Course obj) throws NullPointerException {
        UserSS userss = UserService.authenticated();
        if(!userService.verifyPermission(obj) && !userss.hasRole(UserProfiles.ADMIN)){
            throw new AuthorizationException("Você não é professor deste curso para realizar essa ação!");
        }
    }

    public List<Topic> updateTopicPositions(String linkname, List<TopicPositionDTO> topicPositionDTO) {
        Optional<Course> course = repository.findByLinkName(linkname);
        if(course.isEmpty()) {
            throw new ResourceNotFoundException("Recurso não encontrado: " + linkname);
        }else{
            List<Topic> topics =  course.get().getTopics();
            topics.forEach(x -> {
                List<TopicPositionDTO> topic = topicPositionDTO.stream().filter(y ->
                                y.getTopicId() == x.getId())
                        .collect(Collectors.toList());
                if(!topic.isEmpty())
                    x.setPosition(topic.get(0).getPosition());
            });
            topicService.saveAll(topics);
            return course.get().getTopics();
        }
    }

}
