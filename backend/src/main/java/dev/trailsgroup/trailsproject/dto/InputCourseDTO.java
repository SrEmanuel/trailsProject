package dev.trailsgroup.trailsproject.dto;

import dev.trailsgroup.trailsproject.dto.validationGroups.CreateInfo;
import dev.trailsgroup.trailsproject.dto.validationGroups.UpdateInfo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class InputCourseDTO {

    @Size(min=3, message="O nome não pode ser menor que 3 caracteres", groups = CreateInfo.class)
    @NotEmpty(message = "O nome é obrigatório!", groups= CreateInfo.class)
    private String name;


    @NotNull(message = "O id do tópico é obrigatório!", groups = UpdateInfo.class)
    private List<InputProfessorCourseDTO> professors;

    private String image;



    public InputCourseDTO(){
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

    public List<InputProfessorCourseDTO> getProfessors() {
        return professors;
    }

    public void setProfessors(List<InputProfessorCourseDTO> professors) {
        this.professors = professors;
    }
}
