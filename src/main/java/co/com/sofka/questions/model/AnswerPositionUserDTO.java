package co.com.sofka.questions.model;

import javax.validation.constraints.NotBlank;

public class AnswerPositionUserDTO {

    @NotBlank
    private String answerId;

    @NotBlank
    private String action;

    @NotBlank
    private String questionId;

    @NotBlank
    private String userId;

    public AnswerPositionUserDTO(String answerId, String action, String questionId, String userId) {
        this.answerId = answerId;
        this.action = action;
        this.questionId = questionId;
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }


    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
