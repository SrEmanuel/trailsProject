package dev.trailsgroup.trailsproject.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PasswordDTO {

    @Size(min=8, message="A senha tem que ter pelo menos 8 dígitos")
    @Pattern(regexp="^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,64}$",message="A senha informada NÃO atende os padrões de segurança especificados.")
    @NotEmpty(message = "A senha é obrigatória!")
    private String password;

    @Size(min=8, message="A senha de confirmação tem que ter pelo menos 8 dígitos")
    @Pattern(regexp="^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,64}$",message="A senha informada NÃO atende os padrões de segurança especificados")
    @NotEmpty(message = "A senha de confirmação é obrigatória!")
    private String confirmPassword;

    public PasswordDTO(){
    }

    public PasswordDTO(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
