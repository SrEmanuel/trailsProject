package dev.trailsgroup.trailsproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "tb_user_subject")
@EntityListeners(AuditingEntityListener.class)
public class UserSubject implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    private String professorName;

    private String professorEmail;

    private Integer contributions;

    @CreatedDate
    private Instant creationDate;

    @LastModifiedDate
    private Instant modificationDate;

    public UserSubject(){}

    public UserSubject(Subject subject, String professorName, String professorEmail) {
        this.subject = subject;
        this.professorName = professorName;
        this.professorEmail = professorEmail;
        this.contributions = 1;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getName() {
        return professorName;
    }

    public String getEmail() {
        return professorEmail;
    }

    public void setProfessorEmail(String professorEmail) {
        this.professorEmail = professorEmail;
    }

    public Instant getFirstContributionDate() {
        return creationDate;
    }

    public Instant getLastModificationDate() {
        return modificationDate;
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

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }
}
