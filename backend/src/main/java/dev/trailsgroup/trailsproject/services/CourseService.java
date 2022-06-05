package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.InputCourseDTO;
import dev.trailsgroup.trailsproject.dto.LoggedOutputCourseDTO;
import dev.trailsgroup.trailsproject.dto.OutputCourseDTO;
import dev.trailsgroup.trailsproject.dto.TopicPositionDTO;
import dev.trailsgroup.trailsproject.entities.*;
import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import dev.trailsgroup.trailsproject.entities.pk.UserCoursePK;
import dev.trailsgroup.trailsproject.repositories.*;
import dev.trailsgroup.trailsproject.security.UserSS;
import dev.trailsgroup.trailsproject.services.exceptions.AuthorizationException;
import dev.trailsgroup.trailsproject.services.exceptions.DatabaseException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import dev.trailsgroup.trailsproject.services.utils.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private TopicService topicService;

    @Autowired
    private CourseRepository repository;

    @Lazy
    @Autowired
    private UserCourseService userCourseService;

    @Autowired
    private StudentSubjectRepository studentSubjectRepository;

    @Autowired
    private StaticFileService staticFileService;

    public Page<?> findAll(Pageable pageable) {
        if(UserService.authenticated() == null) {
            List<Course> courses = repository.findAll();
            List<OutputCourseDTO> outputCourseDTOList = new ArrayList<>();
            for(Course x : courses){
                List<User> professors = userCourseService.findProfessorsByCourse(x);
                outputCourseDTOList.add(new OutputCourseDTO(x, professors));
            }
            return new PageImpl<OutputCourseDTO>(outputCourseDTOList, pageable, outputCourseDTOList.size());
        }


        String email = UserService.authenticated().getUsername();
        User user = userService.findByEmail(email);
        Page<Course> all = repository.findAll(pageable);
        List<LoggedOutputCourseDTO> coursesEnchanted = new ArrayList<>();

        for (Course x : all) {
            UserCoursePK userCoursePK = new UserCoursePK();
            userCoursePK.setCourse(x);
            userCoursePK.setUser(user);
            Optional<UserCourse> userCourse = userCourseService.findByIdOptional(userCoursePK);
            List<User> professors = userCourseService.findProfessorsByCourse(x);

            Integer count = 0;

            if (userCourse.isPresent()) {
                count = userCourse.get().getCountCompleted();
            }
            coursesEnchanted.add(new LoggedOutputCourseDTO(x, count, professors));
        }
        return new PageImpl<LoggedOutputCourseDTO>(coursesEnchanted, pageable, coursesEnchanted.size());
    }

    public Course findById(Integer courseId) {
        return repository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(courseId));
    }

    public Course findByName(String linkName){
        Optional<Course> obj = repository.findByLinkName(linkName);
        return obj.orElseThrow(() -> new ResourceNotFoundException(linkName));
    }

    public Course insert(InputCourseDTO obj){
        try {
            String linkName = ClassUtils.createLinkName(obj.getName());
            if(verifyLinkNameAvailability(linkName))
                throw new DatabaseException("O nome de curso '"+ obj.getName() +"' já existe no sistema! Informe outro nome diferente.");
            String fileName = "default-course.png";
            User user = userService.findById(obj.getProfessorID());
            Course course = repository.save(new Course(null, obj.getName(), fileName, linkName));
            userCourseService.save(new UserCourse(course, user));
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


    public Course update(String linkName, InputCourseDTO obj){
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

    public void courseUpdateInformation(Course courseDataBase, InputCourseDTO obj){
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
        if(userService.verifyPermission(obj) && !userss.hasRole(UserProfiles.ADMIN)){
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

    public void verifyCourseCompleted(Course course){
        User user = userService.findBySession();
        Integer counter=0;
        for(Topic x : course.getTopics()){
            for(Subject y : x.getSubjects()) {
                Topic topic = new Topic();
                topic.setCourse(course);
                Example<Topic> example = Example.of(topic);
                Optional<StudentSubject> studentSubject = studentSubjectRepository.findBySubjectAndUser(y, user);
                if(studentSubject.isPresent() && studentSubject.get().isCompleted())
                    counter++;
            }
        }
        if(counter.equals(course.getSubjectsCount()))
            markUserProgress(course, counter);
        else{
            markUserProgress(course,  counter);
        }
    }

    public UserCourse markUserProgress(Course course, Integer count){
        User user = userService.findBySession();
        UserCoursePK userCoursePK = new UserCoursePK();
        userCoursePK.setCourse(course);
        userCoursePK.setUser(user);
        Optional<UserCourse> userCourse = userCourseService.findByIdOptional(userCoursePK);
        if(userCourse.isPresent()){
            UserCourse userCourseGet = userCourse.get();
            userCourseGet.setCountCompleted(count);
            return userCourseService.save(userCourseGet);
        }else{
            UserCourse userCourseNew =  new UserCourse(course, user);
            userCourseNew.setCountCompleted(count);
            return userCourseService.save(userCourseNew);
        }
    }

}
