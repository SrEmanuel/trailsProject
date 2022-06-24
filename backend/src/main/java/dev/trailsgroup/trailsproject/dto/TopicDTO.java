package dev.trailsgroup.trailsproject.dto;

import dev.trailsgroup.trailsproject.dto.validationGroups.CreateInfo;
import dev.trailsgroup.trailsproject.dto.validationGroups.UpdateInfo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;

public class TopicDTO {

    @Size(min=4, message="O nome não pode ser menor que 4 caracteres", groups = UpdateInfo.class)
    @NotEmpty(message = "O nome é obrigatório!", groups = UpdateInfo.class)
    private String name;

    /*@NotNull(message = "A posição é obrigatória!", groups = UpdateInfo.class)
    @Min(value= 1, message = "A posição tem que ser maior que 1", groups = UpdateInfo.class)
    private Integer position;*/

    @NotNull(message ="O id do curso é obrigatório!", groups = CreateInfo.class)
    @Min(value= 1, message = "O id do curso tem que ser maior que 1", groups = CreateInfo.class)
    private Integer courseId;

    public TopicDTO(){
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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
