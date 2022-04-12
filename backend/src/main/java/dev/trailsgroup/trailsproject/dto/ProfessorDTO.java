package dev.trailsgroup.trailsproject.dto;

public class ProfessorDTO {

    private Integer id;

    private String name;

    private String email;

    public ProfessorDTO(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
