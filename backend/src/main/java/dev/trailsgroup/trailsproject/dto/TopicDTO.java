package dev.trailsgroup.trailsproject.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;

public class TopicDTO {

    @Size(min=4, message="O nome não pode ser menor que 4 caracteres")
    @NotEmpty(message = "O nome é obrigatório!")
    private String name;

    @NotNull(message ="A posição é obrigatória!")
    private Integer position;

    @NotNull(message ="O id do curso é obrigatório!")
    private Integer courseId;

    public TopicDTO(){
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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
