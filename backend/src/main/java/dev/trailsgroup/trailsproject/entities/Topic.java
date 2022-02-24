package dev.trailsgroup.trailsproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "tb_topic")
@EntityListeners(AuditingEntityListener.class)
public class Topic implements Serializable {

    //TODO See the implementation of Course ID into a rest endpoint

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    private Integer position; //variable to show in witch position this topic really is


    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "topic")
    private List<Subject> subjects = new ArrayList<>();

    public Topic(Integer id, String name, Integer position, Course course) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.course = course;
    }

    public Topic() {
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @JsonIgnore
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    @JsonIgnore
    public Integer getSubjectsCount(){
        return subjects.size();
    }

    public List<Subject> getSubjects() {
        List<Subject> sortedSubjects = subjects;
        sortedSubjects.sort(Comparator.comparing(Subject::getPosition));//Returning a sorted list of subjects by its position
        return sortedSubjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return id.equals(topic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
}
