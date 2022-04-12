package dev.trailsgroup.trailsproject.dto;

import dev.trailsgroup.trailsproject.dto.validationGroups.CreateInfo;
import dev.trailsgroup.trailsproject.dto.validationGroups.UpdateInfo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CourseDTO {

    @Size(min=3, message="O nome não pode ser menor que 3 caracteres", groups = CreateInfo.class)
    @NotEmpty(message = "O nome é obrigatório!", groups= CreateInfo.class)
    private String name;


    @NotNull(message = "O id do tópico é obrigatório!", groups = UpdateInfo.class)
    private Integer professorID;

    private String image;


    public CourseDTO(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getProfessorID() {
        return professorID;
    }

    public void setProfessorID(Integer professorID) {
        this.professorID = professorID;
    }
}
