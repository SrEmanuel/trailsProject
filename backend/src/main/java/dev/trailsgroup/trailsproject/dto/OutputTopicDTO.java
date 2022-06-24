package dev.trailsgroup.trailsproject.dto;

import dev.trailsgroup.trailsproject.entities.Topic;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OutputTopicDTO {

    private Integer id;
    private String name;

    //private Integer position;


    private String linkName;

    private Instant createdDate;
    private Instant lastModifiedDate;

    private List<OutputSubjectDTO> subjects = new ArrayList<>();


    public OutputTopicDTO() {
    }

    public OutputTopicDTO(Topic topic, List<OutputSubjectDTO> subjects) {
        this.id = topic.getId();
        this.name = topic.getName();
        //this.position = topic.getPosition();
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

    /*public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }*/

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
        List<OutputSubjectDTO> sortedSubjects = subjects;
        sortedSubjects.sort(Comparator.comparing(OutputSubjectDTO::getPosition));//Returning a sorted list of subjects by its position
        return sortedSubjects;
    }

    public void setSubjects(List<OutputSubjectDTO> subjects) {
        this.subjects = subjects;
    }
}
