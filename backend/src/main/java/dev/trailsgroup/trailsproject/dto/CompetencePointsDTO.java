package dev.trailsgroup.trailsproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class CompetencePointsDTO {

    private Integer id;
    private String competence;
    private String description;

    private Integer total;

    private Integer points;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public CompetencePointsDTO(Integer id, String competence, String description, Integer total, Integer points) {
        this.id = id;
        this.competence = competence;
        this.description = description;
        this.total = total;
        this.points = points;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public CompetencePointsDTO(){
    }

}
