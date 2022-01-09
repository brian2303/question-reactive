package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.AnswerPositionUser;
import co.com.sofka.questions.collections.Favorite;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.FavoriteDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.model.AnswerPositionUserDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
public class MapperUtils {

    public Function<AnswerDTO, Answer> mapperToAnswer() {
        return updateAnswer -> {
            var answer = new Answer();
            answer.setPosition(updateAnswer.getPosition());
            answer.setQuestionId(updateAnswer.getQuestionId());
            answer.setUserId(updateAnswer.getUserId());
            answer.setAnswer(updateAnswer.getAnswer());
            return answer;
        };
    }

    public Function<QuestionDTO, Question> mapperToQuestion(String id) {
        return updateQuestion -> {
            var question = new Question();
            question.setId(id);
            question.setUserId(updateQuestion.getUserId());
            question.setCategory(updateQuestion.getCategory());
            question.setQuestion(updateQuestion.getQuestion());
            question.setUserId(updateQuestion.getUserId());
            question.setType(updateQuestion.getType());
            return question;
        };
    }

    public Function<Question, QuestionDTO> mapEntityToQuestion() {
        return entity -> new QuestionDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getQuestion(),
                entity.getType(),
                entity.getCategory()
        );
    }

    public Function<Question, QuestionDTO> mapEntityToQuestionWithoutStarred() {
        return entity -> new QuestionDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getQuestion(),
                entity.getType(),
                entity.getCategory(),
                Boolean.FALSE
        );
    }

    public Function<Answer, AnswerDTO> mapEntityToAnswer() {
        return entity -> new AnswerDTO(
                entity.getQuestionId(),
                entity.getId(),
                entity.getUserId(),
                entity.getAnswer(),
                entity.getPosition()
        );
    }

    public Function<AnswerPositionUserDTO, AnswerPositionUser> mapAnswerPositionUserDTOToEntity(String id) {
        return answerPositionUserDTO -> {
            var answerPosition = new AnswerPositionUser();
            answerPosition.setId(id);
            answerPosition.setQuestionId(answerPositionUserDTO.getQuestionId());
            answerPosition.setUserId(answerPositionUserDTO.getUserId());
            answerPosition.setAnswerId(answerPositionUserDTO.getAnswerId());
            answerPosition.setAction(answerPositionUserDTO.getAction());
            answerPosition.setAnswerPositionDate(LocalDateTime.now());
            return answerPosition;
        };
    }

    public Function<AnswerPositionUser, AnswerPositionUserDTO> mapEntityToAnswerPositionUserDTO() {
        return entity -> new AnswerPositionUserDTO(
                entity.getAnswerId(),
                entity.getAction(),
                entity.getQuestionId(),
                entity.getUserId()
        );
    }

    public Function<FavoriteDTO, Favorite> mapFavoriteToEntity(String id) {
        return favoriteDTO -> {
            var favorite = new Favorite();
            favorite.setId(id);
            favorite.setQuestion(favoriteDTO.getQuestion());
            favorite.setUserId(favoriteDTO.getUserId());
            favorite.setQuestionId(favoriteDTO.getQuestionId());
            return favorite;
        };
    }

    public Function<Favorite, FavoriteDTO> mapEntityToFavorite() {
        return entity -> new FavoriteDTO(
                entity.getUserId(),
                entity.getQuestionId(),
                entity.getQuestion()
        );
    }


}
