package dev.trailsgroup.trailsproject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TokenDTO {

    @NotEmpty(message = "Você precisa informar o token!")
    private String token;

    public TokenDTO(){}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
