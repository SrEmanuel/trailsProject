package dev.trailsgroup.trailsproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;

public class QuestionDTO {

    private Integer id;

    @NotEmpty(message = "O ID do Subject é obrigatório!")
    private String subjectLinkName;

    @NotEmpty(message = "O conteúdo HTML é obrigatório!")
    private String htmlContent;

    @NotEmpty(message = "A alternativa correta é obrigatória")
    private String correct;

    @NotEmpty(message = "A resposta A obrigatória")
    private String answerA;

    @NotEmpty(message = "A resposta B obrigatória")
    private String answerB;

    @NotEmpty(message = "A resposta C obrigatória")
    private String answerC;

    @NotEmpty(message = "A resposta D obrigatória")
    private String answerD;

    @NotEmpty(message = "A resposta E obrigatória")
    private String answerE;

    public QuestionDTO(){};

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubjectLinkName() {
        return subjectLinkName;
    }

    public void setSubjectId(String subjectLinkName) {
        this.subjectLinkName = subjectLinkName;
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
}
