package dev.trailsgroup.trailsproject.dto;

import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.User;

import java.time.Instant;
import java.util.List;

public class LoggedOutputCourseDTO {

    private Integer id;

    private String name;

    private String linkName;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private Integer subjectsCount;

    private Integer completedCount;

    private List<User> professors;

    private String imagePath;
    public LoggedOutputCourseDTO(Course course, Integer completedCount, List<User> professors) {
        this.id = course.getId();
        this.name = course.getName();
        this.linkName = course.getLinkName();
        this.createdDate = course.getCreatedDate();
        this.lastModifiedDate = course.getLastModifiedDate();
        this.subjectsCount = course.getSubjectsCount();
        this.completedCount = completedCount;
        this.imagePath = course.getImagePath();
        this.professors = professors;
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


    public Integer getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(Integer completedCount) {
        this.completedCount = completedCount;
    }

    public List<User> getProfessors() {
        return professors;
    }

    public void setProfessors(List<User> professors) {
        this.professors = professors;
    }
}
