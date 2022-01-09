package co.com.sofka.questions.collections;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class AnswerPositionUser {

    @Id
    private String id;
    private String userId;
    private String questionId;
    private String answerId;
    private LocalDateTime answerPositionDate;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public LocalDateTime getAnswerPositionDate() {
        return answerPositionDate;
    }

    public void setAnswerPositionDate(LocalDateTime answerPositionDate) {
        this.answerPositionDate = answerPositionDate;
    }
}
