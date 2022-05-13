package dev.trailsgroup.trailsproject.entities;

import javax.persistence.*;

@Entity
@Table(name = "tb_professorsubject")
public class ProfessorSubject extends UserSubject {

    private String userName;

    private String userEmail;

    private Integer contributions;

    public ProfessorSubject(){}

    public ProfessorSubject(Integer id, Subject subject, String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.contributions = 1;
    }

    public Integer getContributions() {
        return contributions;
    }

    public void addCounter() {
        if(this.contributions == null){
            this.contributions = 0;
        }
        this.contributions = this.contributions + 1;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
