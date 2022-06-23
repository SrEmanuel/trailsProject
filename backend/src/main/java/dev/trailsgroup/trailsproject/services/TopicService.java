package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.*;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.Subject;
import dev.trailsgroup.trailsproject.entities.Topic;
import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import dev.trailsgroup.trailsproject.repositories.TopicRepository;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TopicRepository repository;

    @Autowired
    private CourseService courseService;

    public Page<Topic> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Topic findByName(String linkName){
        return repository.findByLinkName(linkName).orElseThrow(() -> new ResourceNotFoundException("Identificador '"
                + linkName + "' não foi encontrado no sistema"));
    }

    public Topic findById(Integer topicId) {
        return repository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException(topicId));
    }

    public Topic insert(TopicDTO obj){
        String linkName = ClassUtils.createLinkName(obj.getName());
        if(verifyLinkNameAvailability(linkName))
            throw new DatabaseException("O nome de tópico '"
                    + obj.getName() +"' já existe no sistema! Informe um nome diferente.");

        //if(!verifyPosition(obj.getPosition()))
          //  throw new DatabaseException("Já existe um topico com essa posição!");

        Course course = courseService.findById(obj.getCourseId());
        Topic topic = new Topic(null, obj.getName(), course, linkName);
        verifyUserPermission(topic);

        return repository.save(topic);
    }

    public void saveAll(List<Topic> topicList){
        repository.saveAll(topicList);
    }

    public void delete(String linkName){
        try{
            Topic topic = repository.findByLinkName(linkName).orElseThrow(() -> new ResourceNotFoundException("Identificador '" + linkName + "' não foi encontrado no sistema"));
            verifyUserPermission(topic);
            repository.delete(topic);
        }catch  (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public Topic update(String linkName, TopicDTO obj){
        Topic topicDatabase = repository.findByLinkName(linkName).orElseThrow(()
                -> new ResourceNotFoundException("Identificador '" + linkName + "' não foi encontrado no sistema"));

        verifyUserPermission(topicDatabase);
        topicUpdateInformation(topicDatabase, obj);

        return repository.save(topicDatabase);
    }

    public void topicUpdateInformation(Topic topicDataBase, TopicDTO obj){
        String linkName = ClassUtils.createLinkName(obj.getName());
        if(verifyLinkNameAvailability(linkName) && !Objects.equals(linkName, topicDataBase.getLinkName()))
            throw new DatabaseException("O nome de curso '"+ obj.getName() +"' já existe no sistema! Informe um nome diferente.");

        topicDataBase.setLinkName(linkName);
        topicDataBase.setName(obj.getName());
        //topicDataBase.setPosition(obj.getPosition());
    }

    public Page<?> getTopicsByCourse(String linkName, Pageable pageable){
        Course course = courseService.findByName(linkName);
        Topic topic = new Topic();
        topic.setCourse(course);
        Example<Topic> example = Example.of(topic);
        Page<Topic> topics = repository.findAll(example, pageable);

        if(UserService.authenticated() == null)
            return topics;

        List<OutputTopicDTO> outputTopicDTOList = new ArrayList<>();

        for(Topic x : topics){
            outputTopicDTOList.add(new OutputTopicDTO(x, (List<OutputSubjectDTO>) subjectService.findSubjectsByTopic(x)));
        }

        return new PageImpl<OutputTopicDTO>(outputTopicDTOList, pageable, outputTopicDTOList.size());
    }

    /*public boolean verifyPosition(Integer num){
        Topic topic = new Topic();
        topic.setPosition(num);

        Example<Topic> example = Example.of(topic);

        int count = (int) repository.count(example);
        return count == 0;
    }*/

    private void verifyUserPermission(Topic obj) throws NullPointerException {
        UserSS userss = UserService.authenticated();
        if(userService.verifyPermission(obj.getCourse()) && !userss.hasRole(UserProfiles.ADMIN)){
            throw new AuthorizationException("Você não é professor do curso relacionado a este tópico para realizar essa ação!");
        }
    }

    public boolean verifyLinkNameAvailability(String name){
        return repository.findByLinkName(name).isPresent();
    }

    public Topic updateSubjectPositions(String linkname, List<SubjectPositionDTO> subjectPositionDTOList) {
        Optional<Topic> topic = repository.findByLinkName(linkname);

        if(topic.isEmpty()) {
            throw new ResourceNotFoundException("Recurso não encontrado: " + linkname);

        }else{
            List<Subject> subjects =  topic.get().getSubjects();

            subjects.forEach(x -> {
                List<SubjectPositionDTO> subject = subjectPositionDTOList.stream().filter(y ->
                        y.getSubjectId() == x.getId())
                        .collect(Collectors.toList());
                if(!subject.isEmpty())
                    x.setPosition(subject.get(0).getPosition());
            });

            subjectService.saveAll(subjects);
            return repository.findByLinkName(linkname).get();
        }
    }


}
