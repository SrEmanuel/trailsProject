package dev.trailsgroup.trailsproject.dto;

import javax.validation.constraints.*;

public class UserDTO {

    @Size(min=2, message="O nome não pode ser menor que 2 caracteres")
    @Pattern(regexp="^[a-zA-Z ]*",message="O nome não pode ter caracteres números ou especiais")
    @NotEmpty(message = "O nome é obrigatório!")
    private String name;

    @Size(min=8, message="A senha tem que ter pelo menos 8 dígitos")
    @Pattern(regexp="^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,64}$",message="A senha informada NÃO atende os padrões de segurança especificados")
    /*password must contain 1 number (0-9)
    password must contain 1 uppercase letters
    password must contain 1 lowercase letters
    password must contain 1 non-alpha numeric number
    password must contain 8-64 characters with no space*/
    @NotEmpty(message = "A senha é obrigatória!")
    private String password;

    @Email(message = "O endereço de e-mail tem que ser válido")
    @NotEmpty(message = "O email é obrigatório!")
    private String email;

    private Integer type;

    private Boolean status;

    public UserDTO(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
