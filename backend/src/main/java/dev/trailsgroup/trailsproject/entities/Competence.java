package dev.trailsgroup.trailsproject.entities;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "tb_competence")
@EntityListeners(AuditingEntityListener.class)
public class Competence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "competence", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentCompetence> students =  new HashSet<>();

    @OneToMany(mappedBy = "competence", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<QuestionCompetence> questions =  new HashSet<>();

    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;


    public Competence() {

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public Set<StudentCompetence> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentCompetence> students) {
        this.students = students;
    }

    public Set<QuestionCompetence> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionCompetence> questions) {
        this.questions = questions;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competence question = (Competence) o;
        return id.equals(question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
