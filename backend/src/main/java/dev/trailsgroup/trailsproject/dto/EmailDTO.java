package dev.trailsgroup.trailsproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class EmailDTO {

    @Email(message = "O endereço de e-mail tem que ser válido")
    @NotEmpty(message = "O email é obrigatório!")
    private String email;

    public EmailDTO(){
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
