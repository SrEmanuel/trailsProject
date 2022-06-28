package dev.trailsgroup.trailsproject.dto;

import dev.trailsgroup.trailsproject.entities.ProfessorSubject;
import dev.trailsgroup.trailsproject.entities.Question;
import dev.trailsgroup.trailsproject.entities.Subject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OutputSubjectDTO {

    private Integer id;

    private String name;

    private boolean completed;

    private String grade;

    private String htmlContent;

    private Integer position;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private List<ProfessorSubject> authors = new ArrayList<>();

    private List<Question> questions = new ArrayList<>();

    private String linkName;

    private String imagePath;



    public OutputSubjectDTO(){
    }

    public OutputSubjectDTO(Subject subject, boolean state) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.completed = state;
        this.grade = subject.getGrade();
        this.htmlContent = subject.getHtmlContent();
        this.position = subject.getPosition();
        this.createdDate = subject.getCreatedDate();
        this.lastModifiedDate = subject.getLastModifiedDate();
        this.authors = subject.getAuthors();
        this.linkName = subject.getLinkName();
        this.imagePath = subject.getImagePath();
        this.questions = subject.getQuestions();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public List<ProfessorSubject> getAuthors() {
        return authors;
    }

    public void setAuthors(List<ProfessorSubject> authors) {
        this.authors = authors;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
