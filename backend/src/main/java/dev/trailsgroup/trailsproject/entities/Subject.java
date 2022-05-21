package dev.trailsgroup.trailsproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.trailsgroup.trailsproject.services.StaticFileService;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;


@Entity
@Table(name = "tb_subject")
@EntityListeners(AuditingEntityListener.class)
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String image;
    private String grade;

    private Integer position;

    @Column(columnDefinition="TEXT")
    private String htmlContent;

    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfessorSubject> authors = new ArrayList<>();

    @Column(unique=true)
    private String linkName;

    public Subject(){}

    public Subject(Integer id, String name, String image, String linkName, String grade, String htmlContent, Integer position, Topic topic) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.linkName = linkName;
        this.grade = grade;
        this.htmlContent = htmlContent;
        this.topic = topic;
        this.position = position;
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

    @JsonIgnore
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public List<ProfessorSubject> getAuthors(){
        List<ProfessorSubject> sortedProfessorSubject = authors;
        sortedProfessorSubject.sort(Comparator.comparing(ProfessorSubject::getModificationDate).reversed());
        return sortedProfessorSubject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id.equals(subject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
