package dev.trailsgroup.trailsproject.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_studentsubject")
public class StudentSubject extends UserSubject {

    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;


    public StudentSubject(){}

    public StudentSubject(Integer id, Subject subject, User user) {
        super(id, subject);
        this.user = user;
        this.completed = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
