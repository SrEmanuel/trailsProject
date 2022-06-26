package dev.trailsgroup.trailsproject.dto;

public class OutputQuestionAnswerDTO {

    private String answer;
    private String correctAnswer;
    private Boolean isCorrect;

    public OutputQuestionAnswerDTO() {
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
