package dev.trailsgroup.trailsproject.dto;

import dev.trailsgroup.trailsproject.dto.validationGroups.CreateInfo;
import dev.trailsgroup.trailsproject.dto.validationGroups.UpdateInfo;
import dev.trailsgroup.trailsproject.entities.Course;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

public class OutputCourseDTO {

    private Integer id;

    private String name;

    private String linkName;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private Integer subjectsCount;

    private boolean completed;

    private String imagePath;
    public OutputCourseDTO(Course course, boolean completed) {
        this.id = course.getId();
        this.name = course.getName();
        this.linkName = course.getLinkName();
        this.createdDate = course.getCreatedDate();
        this.lastModifiedDate = course.getLastModifiedDate();
        this.subjectsCount = course.getSubjectsCount();
        this.completed = completed;
        this.imagePath = course.getImagePath();
    }

    public Integer getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public Integer getSubjectsCount() {
        return subjectsCount;
    }

    public void setSubjectsCount(Integer subjectsCount) {
        this.subjectsCount = subjectsCount;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
