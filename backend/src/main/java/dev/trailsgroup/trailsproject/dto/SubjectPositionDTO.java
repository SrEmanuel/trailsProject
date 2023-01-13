package dev.trailsgroup.trailsproject.dto;

import dev.trailsgroup.trailsproject.dto.validationGroups.CreateInfo;
import dev.trailsgroup.trailsproject.dto.validationGroups.UpdateInfo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SubjectPositionDTO {

    @NotNull(message ="O id do subject é obrigatório!", groups = CreateInfo.class)
    @Min(value= 1, message = "O id do curso tem que ser maior que 1", groups = CreateInfo.class)
    private Integer subjectId;

    @NotNull(message = "A posição é obrigatória!", groups = UpdateInfo.class)
    @Min(value= 1, message = "A posição tem que ser maior que 1", groups = UpdateInfo.class)
    private Integer position;


    public SubjectPositionDTO() {
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}


