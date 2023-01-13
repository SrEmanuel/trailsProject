package dev.trailsgroup.trailsproject.dto;

import jakarta.validation.constraints.NotEmpty;

public class CompetenceOnQuestionDTO {

    private Integer id;

    private String name;

    private String description;

    @NotEmpty(message = "A operação a ser realizada é obrigatória")
    private String operation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CompetenceOnQuestionDTO(Integer id) {
        this.id = id;
    }

    public CompetenceOnQuestionDTO(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
