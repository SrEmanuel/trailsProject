package dev.trailsgroup.trailsproject.dto;

import javax.validation.constraints.NotEmpty;

public class QuestionCompetenceDTO {
    private Integer competenceId;

    public QuestionCompetenceDTO(Integer competenceId) {
        this.competenceId = competenceId;
    }


    public QuestionCompetenceDTO() {
    }


    public Integer getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(Integer competenceId) {
        this.competenceId = competenceId;
    }
}
