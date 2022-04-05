package dev.trailsgroup.trailsproject.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
