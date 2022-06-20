package dev.trailsgroup.trailsproject.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
