package dev.trailsgroup.trailsproject.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CourseDTO {

    @Size(min=3, message="O nome não pode ser menor que 3 caracteres")
    @NotEmpty(message = "O nome é obrigatório!")
    private String name;

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
}
