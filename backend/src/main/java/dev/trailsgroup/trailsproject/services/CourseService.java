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
        Page<Course> courses = repository.findAll(pageable);
        List<Course> courseList = courses.toList();

        if(UserService.authenticated() == null) {
           return normalFindAll(courseList, pageable);
        }

        return loggedFindAll(courseList, pageable);
    }

    private Page<?> normalFindAll(List<Course> courseList,Pageable pageable){
        List<OutputCourseDTO> outputCourseDTOList = new ArrayList<>();
        for(Course x : courseList){
            List<User> professors = userCourseService.findProfessorsByCourse(x);
            outputCourseDTOList.add(new OutputCourseDTO(x, professors));
        }
        outputCourseDTOList.removeIf(x -> x.getSubjectsCount() == 0);
        return new PageImpl<OutputCourseDTO>(outputCourseDTOList, pageable, outputCourseDTOList.size());
    }

    private Page<?> loggedFindAll(List<Course> courseList, Pageable pageable){
        String email = UserService.authenticated().getUsername();
        User user = userService.findByEmail(email);
        List<LoggedOutputCourseDTO> coursesEnchanted = new ArrayList<>();
        for (Course x : courseList) {
            List<User> professors = userCourseService.findProfessorsByCourse(x);
            StudentCourse studentCourseExample = new StudentCourse(x, user);
            studentCourseExample.setCountCompleted(null);
            Optional<StudentCourse> studentCourse = userCourseService.findStudentCourse(studentCourseExample);


            Integer count = 0;

            if (studentCourse.isPresent()) {
                count = studentCourse.get().getCountCompleted();
            }
            coursesEnchanted.add(new LoggedOutputCourseDTO(x, count, professors));
        }
        if(!user.getProfiles().contains(UserProfiles.ADMIN) || !user.getProfiles().contains(UserProfiles.PROFESSOR))
            coursesEnchanted.removeIf(x -> x.getSubjectsCount() == 0);
        return new PageImpl<LoggedOutputCourseDTO>(coursesEnchanted, pageable, coursesEnchanted.size());
    }



    public Course findById(Integer courseId) {
        return repository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(courseId));
    }

    public OutputCourseDTO outputFindByName(String linkName){
        Course course = repository.findByLinkName(linkName).orElseThrow(() -> new ResourceNotFoundException(linkName));
        List<User> professors = userCourseService.findProfessorsByCourse(course);
        return new OutputCourseDTO(course, professors);
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
            Course course = repository.save(new Course(null, obj.getName(), fileName, linkName));
            userCourseService.saveProfessors(obj.getProfessors(), course);
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
            Course updatedCourse = repository.save(courseDatabase);
            userCourseService.saveProfessors(obj.getProfessors(), updatedCourse);
            repository.flush();
            return updatedCourse;
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

    /*public List<Topic> updateTopicPositions(String linkname, List<TopicPositionDTO> topicPositionDTO) {
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
    }*/

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

    public StudentCourse markUserProgress(Course course, Integer count){
        User user = userService.findBySession();
        StudentCourse studentCourse = new StudentCourse(course, user);
        Optional<StudentCourse> studentCourseDB = userCourseService.findStudentCourse(studentCourse);
        if(studentCourseDB.isPresent()){
            StudentCourse studentCourseGet = studentCourseDB.get();
            studentCourseGet.setCountCompleted(count);
            return userCourseService.saveStudentCourse(studentCourseGet);
        }else{
            StudentCourse studentCourseNew =  new StudentCourse(course, user);
            studentCourseNew.setCountCompleted(count);
            return userCourseService.saveStudentCourse(studentCourseNew);
        }
    }

}
