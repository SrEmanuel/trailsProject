package dev.trailsgroup.trailsproject.dto;

import dev.trailsgroup.trailsproject.dto.validationGroups.CreateInfo;
import dev.trailsgroup.trailsproject.dto.validationGroups.UpdateInfo;

import javax.validation.GroupSequence;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SubjectDTO {
    

    @Size(min=4, message="O nome não pode ser menor que 4 caracteres", groups = UpdateInfo.class)
    @NotEmpty(message = "O nome é obrigatório!", groups = UpdateInfo.class)
    private String name;

    @NotNull(message = "O id do tópico é obrigatório!", groups = CreateInfo.class)
    @Min(value= 1, message = "O id do tópico tem que ser maior que 1", groups = CreateInfo.class)
    private Integer topicId;

    private String image;

    @NotEmpty(message = "A série é obrigatória!", groups = UpdateInfo.class)
    private String grade;

    @NotEmpty(message = "O conteúdo HTML é obrigatório!", groups = UpdateInfo.class)
    private String htmlContent;

    @NotNull(message = "A posição é obrigatória!", groups = CreateInfo.class)
    @Min(value= 1, message = "A posição tem que ser maior que 1", groups = CreateInfo.class)
    private Integer position;

    public SubjectDTO(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
