package dev.trailsgroup.trailsproject.services;


import dev.trailsgroup.trailsproject.dto.InputQuestionAnswerDTO;
import dev.trailsgroup.trailsproject.dto.InputQuestionOnSubjectDTO;
import dev.trailsgroup.trailsproject.dto.OutputQuestionAnswerDTO;
import dev.trailsgroup.trailsproject.dto.QuestionDTO;
import dev.trailsgroup.trailsproject.entities.Question;
import dev.trailsgroup.trailsproject.entities.Subject;
import dev.trailsgroup.trailsproject.repositories.QuestionRepository;
import dev.trailsgroup.trailsproject.services.exceptions.DatabaseException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingPathVariableException;

import java.util.List;
import java.util.Objects;


@Service
public class QuestionService {

    @Autowired
    QuestionRepository repository;

    @Autowired
    SubjectService subjectService;

    @Lazy
    @Autowired
    StudentCompetenceService studentCompetenceService;

    public Page<Question> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Question findById(Integer id){
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Identificador '" + id + "' não foi encontrado no sistema"));
    }

    public Question insert(QuestionDTO obj){
        try{
            Subject subject = subjectService.findByName(obj.getSubjectLinkName());
            Question question = new Question(null, subject, obj.getHtmlContent(), obj.getCorrect().toUpperCase(), obj.getAnswerA(), obj.getAnswerB(),
                    obj.getAnswerC(), obj.getAnswerD(), obj.getAnswerE());
            return save(question);
        }catch (IllegalArgumentException | NullPointerException e){
            throw new DatabaseException(e.getMessage());
        }

    }

    public Question save(Question question){
        return repository.save(question);
    }

    public void delete(Integer id){
        try{
            Question question = repository.findById(id).orElseThrow(()
                    -> new ResourceNotFoundException("Identificador '" + id + "' para a pergunta não foi encontrado no sistema"));
            //verifyUserPermission(question);
            repository.delete(question);
        }catch  (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public Question update(Integer id, QuestionDTO obj){
        Question questionDatabase = repository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Identificador '" + id + "' não foi encontrado no sistema"));

        //verifyUserPermission(topicDatabase);
        questionUpdateInformation(questionDatabase, obj);
        return repository.save(questionDatabase);
    }

    private void questionUpdateInformation(Question questionDatabase, QuestionDTO obj) {
        questionDatabase.setHtmlContent(obj.getHtmlContent());
        questionDatabase.setCorrect(obj.getCorrect().toUpperCase());
        questionDatabase.setAnswerA(obj.getAnswerA());
        questionDatabase.setAnswerB(obj.getAnswerB());
        questionDatabase.setAnswerC(obj.getAnswerC());
        questionDatabase.setAnswerD(obj.getAnswerD());
        questionDatabase.setAnswerE(obj.getAnswerE());
    }

    public OutputQuestionAnswerDTO verifyAnswer(Integer id, InputQuestionAnswerDTO inputAnswerDTO){
        Question question = findById(id);
        OutputQuestionAnswerDTO outputAnswerDTO = new OutputQuestionAnswerDTO();
        outputAnswerDTO.setAnswer(inputAnswerDTO.getAnswer().toUpperCase());
        outputAnswerDTO.setCorrectAnswer(question.getCorrect());
        if(question.getCorrect().equals(inputAnswerDTO.getAnswer().toUpperCase())){
            outputAnswerDTO.setIsCorrect(true);
            studentCompetenceService.setLoggedUserPoint(question);
            return outputAnswerDTO;
        }else{
            outputAnswerDTO.setIsCorrect(false);
        }
        return outputAnswerDTO;
    }

    public Page<Question> getQuestionsBySubject(String linkName, Pageable pageable) {
        Subject subject = subjectService.findByName(linkName);
        Question question = new Question();
        question.setSubject(subject);
        return repository.findAll(Example.of(question), pageable);
    }

    public void updateArray(List<InputQuestionOnSubjectDTO> questions, Subject subject) {
        if(questions != null) {
            questions.forEach(x -> {
                if(x.getOperation().equalsIgnoreCase("UPDATE") || x.getOperation().equalsIgnoreCase("DELETE")) {
                    if (!Objects.equals(findById(x.getId()).getId(), subject.getId())) {
                        throw new DatabaseException("Você tentou modificar uma pergunta que não faz parte desse conteúdo. Operação cancelada.");
                    }
                }
            });
            for (InputQuestionOnSubjectDTO x : questions) {
                if (x.getOperation().equalsIgnoreCase("DELETE")) {
                    delete(x.getId());
                }else {
                    if (x.getOperation().equalsIgnoreCase("UPDATE")) {
                        if ((x.getId() != null) && (subject.getLinkName() != null) && (x.getHtmlContent() != null) && (x.getCorrect() != null)
                                && (x.getAnswerA() != null) && (x.getAnswerB() != null) && (x.getAnswerC() != null) && x.getAnswerD() != null && x.getAnswerE() != null) {
                            update(x.getId(), new QuestionDTO(x.getId(), subject.getLinkName(), x.getHtmlContent(), x.getCorrect(), x.getAnswerA(),
                                    x.getAnswerB(), x.getAnswerC(), x.getAnswerD(), x.getAnswerE()));
                        } else {
                            throw new DatabaseException("Você tentou realizar uma atualização de salvamento de perguntas entretanto não foram informados" +
                                    "todos os dados obrigatórios. Operação cancelada.");
                        }
                    }else{
                        if (x.getOperation().equalsIgnoreCase("CREATE")) {
                            if((subject.getLinkName() != null) && (x.getHtmlContent() != null) && (x.getCorrect() != null)
                                    && (x.getAnswerA() != null) && (x.getAnswerB() != null) &&
                                    (x.getAnswerC() != null) && x.getAnswerD() != null && x.getAnswerE() != null) {
                                save(new Question(null, subject, x.getHtmlContent(), x.getCorrect(), x.getAnswerA(),
                                        x.getAnswerB(), x.getAnswerC(), x.getAnswerD(), x.getAnswerE()));
                            }else{
                                throw new DatabaseException("Você tentou realizar uma atualização de criação de pergunta entretanto não foram informados" +
                                        "todos os dados obrigatórios. Operação cancelada.");
                            }
                        }
                    }
                }
            }
        }
    }
}
