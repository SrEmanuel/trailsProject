package dev.trailsgroup.trailsproject.services;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubjectService {


    @Autowired
    private SubjectRepository repository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private StudentSubjectRepository studentSubjectRepository;

    @Autowired
    private ProfessorSubjectRepository professorSubjectRepository;

    @Autowired
    private StaticFileService staticFileService;

    @Autowired
    private UserService userService;

    public Page<Subject> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Subject findByName(String linkName){
        Optional<Subject> obj = repository.findByLinkName(linkName);
        return obj.orElseThrow(() -> new ResourceNotFoundException("Identificador '" + linkName + "' não foi encontrado no sistema"));
    }


    public void saveAll(List<Subject> subjectList){
        repository.saveAll(subjectList);
    }

    public Subject insert(SubjectDTO obj){
        try {
            String linkName = ClassUtils.createLinkName(obj.getName());
            if(verifyLinkNameAvailability(linkName))
                throw new DatabaseException("O nome de assunto '"+ obj.getName() +"' já existe no sistema! Informe um nome diferente.");
            Topic topic = topicRepository.findById(obj.getTopicId()).orElseThrow(() -> new ResourceNotFoundException(obj.getTopicId()));
            Subject subject = new Subject(null, obj.getName(), "default-subject.png", linkName, obj.getGrade(), obj.getHtmlContent(),obj.getPosition(), topic);
            Subject savedSubject = repository.save(subject);
            verifyUserPermission(savedSubject);
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
        Subject subject = repository.findByLinkName(linkName).orElseThrow(()-> new ResourceNotFoundException("Identificador '" + linkName + "' não foi encontrado no sistema"));
        subject.setImage(staticFileService.update(multipartFile, subject.getImageName()));
        repository.save(subject);
        return subject;
    }


    public void delete(String linkName){
        try{
            Subject subject = repository.findByLinkName(linkName).orElseThrow(() -> new ResourceNotFoundException("Identificador '" + linkName + "' não foi encontrado no sistema"));
            verifyUserPermission(subject);
            staticFileService.delete(subject.getImageName());
            repository.delete(subject);
        }catch  (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public Subject update(String linkName, SubjectDTO obj){
        Subject SubjectDatabase = repository.findByLinkName(linkName).orElseThrow(() -> new ResourceNotFoundException("Identificador '" + linkName + "' não foi encontrado no sistema"));
        verifyUserPermission(SubjectDatabase);
        addProfessorName(SubjectDatabase);
        subjectUpdateInformation(SubjectDatabase, obj);
        return repository.save(SubjectDatabase);
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
        if(!userService.verifyPermission(obj.getTopic().getCourse()) && !userss.hasRole(UserProfiles.ADMIN)){
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

        Example<ProfessorSubject> example = Example.of(professorSubject);
        if(professorSubjectRepository.exists(example)){
            ProfessorSubject professorSubject1 = professorSubjectRepository.findOne(example).get();
            professorSubject1.addCounter();
            professorSubjectRepository.save(professorSubject1);
        }else {
            professorSubject.addCounter();
            professorSubjectRepository.save(professorSubject);
        }
    }

    public void markUserProgress(boolean state, String linkName) {
        User user = userService.findById(UserService.authenticated().getId());
        Subject subject = repository.findByLinkName(linkName).orElseThrow(() -> new ResourceNotFoundException("Identificador de Subject não encontrado: "+ linkName));
        StudentSubject studentSubject = new StudentSubject(null, subject, user);
        studentSubject.setCompleted(state);
        studentSubjectRepository.save(studentSubject);

    }
}
