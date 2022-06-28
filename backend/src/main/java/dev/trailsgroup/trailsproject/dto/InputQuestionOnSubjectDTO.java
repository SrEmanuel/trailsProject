package dev.trailsgroup.trailsproject.dto;

import dev.trailsgroup.trailsproject.entities.Competence;
import dev.trailsgroup.trailsproject.entities.QuestionCompetence;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

public class InputQuestionOnSubjectDTO {
    @NotEmpty(message = "O ID do Question é obrigatório!")
    private Integer id;

    @NotEmpty(message = "A operação a ser realizada é obrigatória")
    private String operation;

    private Set<CompetenceOnQuestionDTO> competences =  new HashSet<>();

    private String htmlContent;

    private String correct;

    private String answerA;

    private String answerB;

    private String answerC;

    private String answerD;

    private String answerE;

    public InputQuestionOnSubjectDTO(){};

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getAnswerE() {
        return answerE;
    }

    public void setAnswerE(String answerE) {
        this.answerE = answerE;
    }

    public Set<CompetenceOnQuestionDTO> getCompetences() {
        return competences;
    }

    public void setCompetences(Set<CompetenceOnQuestionDTO> competences) {
        this.competences = competences;
    }
}
