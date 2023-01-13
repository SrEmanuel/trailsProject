package dev.trailsgroup.trailsproject.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.trailsgroup.trailsproject.services.StaticFileService;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "tb_course")
@EntityListeners(AuditingEntityListener.class)
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true)
    private String name;

    @Column(unique=true)
    private String linkName;

    private String image;

    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;

    @OneToMany(mappedBy = "id.course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProfessorCourse> professorCourses = new HashSet<>();

    @OneToMany(mappedBy = "id.course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentCourse> studentCourses = new HashSet<>();

    public Course(){}

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics = new ArrayList<>();

    public Course(Integer id, String name, String image, String linkName) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.linkName = linkName;
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

    public String getImagePath() {
        return  StaticFileService.getInstance().getIp() + "/uploads/" + image;
    }

    @JsonIgnore
    public String getImageName(){
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @JsonIgnore
    public Set<User> getUsers(){
        Set<User> set = new HashSet<>();
        for(ProfessorCourse x : professorCourses){
            set.add(x.getUser());
        }
        return set;
    }

    @JsonIgnore
    public List<Topic> getTopics() {
        return topics;
    }

    public Integer getSubjectsCount(){
        Integer count = 0;
        for(Topic x : topics){
            count += x.getSubjectsCount();
        }
        return count;
    }


    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }
}
