package dev.trailsgroup.trailsproject.entities;

import dev.trailsgroup.trailsproject.entities.pk.StudentCoursePK;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "tb_studentcourse")
@EntityListeners(AuditingEntityListener.class)
public class StudentCourse {
    private Integer countCompleted;

    @EmbeddedId
    StudentCoursePK id = new StudentCoursePK();

    @CreatedDate
    private Instant creationDate;

    @LastModifiedDate
    private Instant modificationDate;

    public StudentCourse(){}

    public StudentCourse(Course course, User user) {
        id.setCourse(course);
        id.setUser(user);
        this.countCompleted = 0;
    }

    public Integer getCountCompleted() {
        return countCompleted;
    }

    public StudentCoursePK getId() {
        return id;
    }

    public void setId(StudentCoursePK id) {
        this.id = id;
    }

    public void setCountCompleted(Integer countCompleted) {
        this.countCompleted = countCompleted;
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
