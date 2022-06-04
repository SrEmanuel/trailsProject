package dev.trailsgroup.trailsproject.dto;

import dev.trailsgroup.trailsproject.dto.validationGroups.CreateInfo;
import dev.trailsgroup.trailsproject.dto.validationGroups.UpdateInfo;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.Subject;
import dev.trailsgroup.trailsproject.entities.Topic;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OutputTopicDTO {

    private Integer id;
    private String name;

    private Integer position;


    private String linkName;

    private Instant createdDate;
    private Instant lastModifiedDate;

    private List<OutputSubjectDTO> subjects = new ArrayList<>();


    public OutputTopicDTO() {
    }

    public OutputTopicDTO(Topic topic, List<OutputSubjectDTO> subjects) {
        this.id = topic.getId();
        this.name = topic.getName();
        this.position = topic.getPosition();
        this.linkName = topic.getLinkName();
        this.createdDate = topic.getCreatedDate();
        this.lastModifiedDate = topic.getLastModifiedDate();
        this.subjects = subjects;
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

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public List<OutputSubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<OutputSubjectDTO> subjects) {
        this.subjects = subjects;
    }
}
