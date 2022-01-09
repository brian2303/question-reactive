package co.com.sofka.questions.model;

import javax.validation.constraints.NotBlank;

public class FavoriteDTO {

    @NotBlank
    private String userId;
    @NotBlank
    private String questionId;
    @NotBlank
    private String question;

    public FavoriteDTO(String userId, String questionId, String question) {
        this.userId = userId;
        this.questionId = questionId;
        this.question = question;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
