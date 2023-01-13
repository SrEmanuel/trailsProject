package dev.trailsgroup.trailsproject.dto;

import dev.trailsgroup.trailsproject.dto.validationGroups.CreateInfo;
import dev.trailsgroup.trailsproject.dto.validationGroups.UpdateInfo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TopicPositionDTO {

    @NotNull(message ="O id do topic é obrigatório!", groups = CreateInfo.class)
    @Min(value= 1, message = "O id do tópico tem que ser maior que 1", groups = CreateInfo.class)
    private Integer topicId;

    @NotNull(message = "A posição é obrigatória!", groups = UpdateInfo.class)
    @Min(value= 1, message = "A posição tem que ser maior que 1", groups = UpdateInfo.class)
    private Integer position;


    public TopicPositionDTO() {
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}


