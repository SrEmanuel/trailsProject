package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.OutputSubjectDTO;
import dev.trailsgroup.trailsproject.dto.SubjectDTO;
import dev.trailsgroup.trailsproject.entities.*;
import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import dev.trailsgroup.trailsproject.repositories.*;
import dev.trailsgroup.trailsproject.security.UserSS;
import dev.trailsgroup.trailsproject.services.exceptions.AuthorizationException;
import dev.trailsgroup.trailsproject.services.exceptions.DatabaseException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import dev.trailsgroup.trailsproject.services.utils.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubjectService {


    @Autowired
    private SubjectRepository repository;

    @Lazy
    @Autowired
    private CourseService courseService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserSubjectService userSubjectService;

    @Autowired
    private StaticFileService staticFileService;

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private QuestionService questionService;

    public Page<Subject> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    protected List<?> findSubjectsByTopic(Topic topic){
        Subject subject = new Subject();
        subject.setTopic(topic);
        List<Subject> subjects =  repository.findAll(Example.of(subject));

        if(UserService.authenticated() == null)
            return subjects;

        String email = UserService.authenticated().getUsername();
        User user = userService.findByEmail(email);

        List<OutputSubjectDTO> outputSubjectDTOList = new ArrayList<>();
        for(Subject x  : subjects){
            StudentSubject studentSubject = userSubjectService.findStudentSubject(x, user);
            outputSubjectDTOList.add(new OutputSubjectDTO(x, studentSubject.isCompleted()));
        }

        return outputSubjectDTOList;
    }

    public <T extends Object> T findByNameOutput(String linkName){
        Subject subject = repository.findByLinkName(linkName).orElseThrow(() -> new ResourceNotFoundException("Identificador '"
                + linkName + "' não foi encontrado no sistema"));
        if(UserService.authenticated() == null)
            return (T)subject;

        String email = UserService.authenticated().getUsername();
        User user = userService.findByEmail(email);

        StudentSubject studentSubject = userSubjectService.findStudentSubject(subject, user);

        return  (T) new OutputSubjectDTO(subject, studentSubject.isCompleted());
    }

    public Subject findByName(String linkName){
        return repository.findByLinkName(linkName).orElseThrow(() -> new ResourceNotFoundException("Identificador '"
                + linkName + "' não foi encontrado no sistema"));
    }

    protected void saveAll(List<Subject> subjectList){
        repository.saveAll(subjectList);
    }

    public Subject insert(SubjectDTO obj){
        try {
            String linkName = ClassUtils.createLinkName(obj.getName());

            if(verifyLinkNameAvailability(linkName))
                throw new DatabaseException("O nome de assunto '"+
                        obj.getName() +"' já existe no sistema! Informe um nome diferente.");

            Topic topic = topicService.findById(obj.getTopicId());
            Subject subject = new Subject(null, obj.getName(), "default-subject.png", linkName, obj.getGrade(),
                    obj.getHtmlContent(),obj.getPosition(), topic);

            Subject savedSubject = repository.save(subject);

            verifyUserPermission(savedSubject); //TODO verify that validation. Is that in the correct place?
            addProfessorName(savedSubject);
            return savedSubject;
        }catch(IllegalArgumentException | NullPointerException e){
            throw new DatabaseException(e.getMessage());
        }
    }
    public boolean verifyLinkNameAvailability(String name){
        return repository.findByLinkName(name).isPresent();
    }

    public Subject insertImage(MultipartFile multipartFile, String linkName){
        Subject subject = findByName(linkName);
        subject.setImage(staticFileService.update(multipartFile, subject.getImageName()));
        repository.save(subject);
        return subject;
    }

    public void delete(String linkName){
        try{
            Subject subject = findByName(linkName);
            verifyUserPermission(subject);
            staticFileService.delete(subject.getImageName());
            repository.delete(subject);
        }catch  (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public Subject update(String linkName, SubjectDTO obj){
        Subject SubjectDatabase = findByName(linkName);
        verifyUserPermission(SubjectDatabase);
        questionService.updateArray(obj.getQuestions(), SubjectDatabase);
        addProfessorName(SubjectDatabase);
        subjectUpdateInformation(SubjectDatabase, obj);
        Subject subject = repository.save(SubjectDatabase);
        repository.flush();
        return subject;
    }

    public void subjectUpdateInformation(Subject subjectDataBase, SubjectDTO obj){
        String linkName = ClassUtils.createLinkName(obj.getName());
        if(verifyLinkNameAvailability(linkName) && !Objects.equals(linkName, subjectDataBase.getLinkName()))
            throw new DatabaseException("O nome de curso '"+ obj.getName() +"' já existe no sistema! Informe um nome diferente.");

        subjectDataBase.setLinkName(linkName);
        subjectDataBase.setName(obj.getName());
        subjectDataBase.setGrade(obj.getGrade());
        subjectDataBase.setHtmlContent(obj.getHtmlContent());
        subjectDataBase.setPosition(obj.getPosition());
    }


    private void verifyUserPermission(Subject obj) throws NullPointerException {
        UserSS userss = UserService.authenticated();
        if(userService.verifyPermission(obj.getTopic().getCourse()) && !userss.hasRole(UserProfiles.ADMIN)){
            throw new AuthorizationException("Você não é professor do curso relacionado a este tópico para realizar essa ação");
        }

    }

    private void addProfessorName(Subject obj){
        UserSS userss = UserService.authenticated();

        String name = Objects.requireNonNull(userss).getName();
        String email = Objects.requireNonNull(userss).getUsername();
        ProfessorSubject professorSubject = new ProfessorSubject();
        professorSubject.setUserEmail(email);
        professorSubject.setUserName(name);
        professorSubject.setSubject(obj);

        if(userSubjectService.existsProfessor(professorSubject)){
            ProfessorSubject professorSubject1 = userSubjectService.findOneProfessor(professorSubject);
            professorSubject1.addCounter();
            userSubjectService.saveProfessor(professorSubject1);
        }else {
            professorSubject.addCounter();
            userSubjectService.saveProfessor(professorSubject);
        }
    }

    public void markUserProgress(boolean state, String linkName) {
        User user = userService.findBySession();
        Subject subject = repository.findByLinkName(linkName)
                .orElseThrow(() -> new ResourceNotFoundException("Identificador de Subject não encontrado: "+ linkName));
        Optional<StudentSubject> studentSubject = userSubjectService.findBySubjectAndUser(subject, user);

        if(studentSubject.isEmpty()) {
            StudentSubject studentSubjectNew = new StudentSubject(null, subject, user);
            studentSubjectNew.setCompleted(state);
            userSubjectService.saveStudent(studentSubjectNew);
            courseService.verifyCourseCompleted(subject.getTopic().getCourse());
        }else{
            StudentSubject studentSubjectDB = studentSubject.get();
            studentSubjectDB.setCompleted(state);
            courseService.verifyCourseCompleted(subject.getTopic().getCourse());
        }
    }
}
