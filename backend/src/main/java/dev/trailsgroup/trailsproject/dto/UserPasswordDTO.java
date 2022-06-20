package dev.trailsgroup.trailsproject.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserPasswordDTO {

    @NotEmpty(message = "A senha antiga é obrigatória!")
    private String oldPassword;

    @Size(min=8, message="A senha tem que ter pelo menos 8 dígitos")
    @Pattern(regexp="^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,64}$",message="A senha informada NÃO atende os padrões de segurança especificados.")
    @NotEmpty(message = "A senha é obrigatória!")
    private String password;


    public UserPasswordDTO(){
    }

    public UserPasswordDTO(String oldPassword, String password) {
        this.oldPassword = oldPassword;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
