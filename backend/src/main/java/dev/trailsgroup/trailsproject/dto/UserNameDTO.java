package dev.trailsgroup.trailsproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserNameDTO {

    @Size(min=2, message="O nome não pode ser menor que 2 caracteres")
    @Pattern(regexp="^[a-zA-Z ]*",message="O nome não pode ter caracteres números ou especiais")
    @NotEmpty(message = "O nome é obrigatório!")
    private String name;

    public UserNameDTO(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
