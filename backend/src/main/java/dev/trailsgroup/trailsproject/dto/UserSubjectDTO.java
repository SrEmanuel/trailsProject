package dev.trailsgroup.trailsproject.dto;

import dev.trailsgroup.trailsproject.entities.Subject;

public class UserSubjectDTO {

    private Subject subject;

    private String professorName;

    private String professorEmail;

    public UserSubjectDTO() {
    }

    public UserSubjectDTO(Subject subject, String professorName, String professorEmail) {
        this.subject = subject;
        this.professorName = professorName;
        this.professorEmail = professorEmail;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getProfessorEmail() {
        return professorEmail;
    }

    public void setProfessorEmail(String professorEmail) {
        this.professorEmail = professorEmail;
    }

}
