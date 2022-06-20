package dev.trailsgroup.trailsproject.entities;

import dev.trailsgroup.trailsproject.entities.pk.UserCoursePK;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "tb_professorcourse")
@EntityListeners(AuditingEntityListener.class)
public class ProfessorCourse {

    @EmbeddedId
    UserCoursePK id = new UserCoursePK();
    public ProfessorCourse(){}

    @CreatedDate
    private Instant creationDate;

    @LastModifiedDate
    private Instant modificationDate;


    public ProfessorCourse(Course course, User user) {
        id.setCourse(course);
        id.setUser(user);
    }

    public UserCoursePK getId() {
        return id;
    }

    public Course getCourse(){
        return id.getCourse();
    }

    public void setCourse(Course course){
        id.setCourse(course);
    }

    public User getUser(){
        return id.getUser();
    }

    public void setUser(User user){
        id.setUser(user);
    }

    public void setId(UserCoursePK id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }
}
