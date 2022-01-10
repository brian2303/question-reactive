package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Favorite;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.FavoriteRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;


class GetUseCaseTest {


    AnswerRepository answerRepository;
    FavoriteRepository favoriteRepository;
    QuestionRepository questionRepository;
    GetUseCase getUseCase;
    MapperUtils mapperUtils;

    @BeforeEach
    public void setUp(){
        mapperUtils = new MapperUtils();
        answerRepository = mock(AnswerRepository.class);
        questionRepository = mock(QuestionRepository.class);
        favoriteRepository = mock(FavoriteRepository.class);
        getUseCase = new GetUseCase(mapperUtils,questionRepository,answerRepository,favoriteRepository);
    }


    @Test
    void testToGetQuestionWithAnswersWithoutShowFavorites(){

        Question question = new Question();
        question.setId("111");
        question.setCategory("any category");
        question.setType("my type");
        question.setUserId("111");
        question.setQuestion("!! Question !!");

        Answer answerOne = new Answer();
        answerOne.setAnswer("answer 1");
        answerOne.setPosition(1);
        answerOne.setId("1112");
        answerOne.setQuestionId("12356");
        answerOne.setUserId("id1user");


        Favorite favorite = new Favorite();
        favorite.setQuestionId("111");
        favorite.setQuestion("!! Question !!");
        favorite.setUserId("any");

        when(questionRepository.findById(anyString())).thenReturn(Mono.just(question));
        when(answerRepository.findAllByQuestionId(anyString())).thenReturn(Flux.just(answerOne));
        when(favoriteRepository.findAllByUserId("1")).thenReturn(Flux.just(favorite));

        StepVerifier.create(getUseCase.apply("1","1"))
                .expectNextMatches(questionDTO -> {
                    assert questionDTO.getFavorite().equals(Boolean.FALSE);
                    assert questionDTO.getQuestion().equals("!! Question !!");
                    assert questionDTO.getCategory().equals("any category");
                    return true;
                }).verifyComplete();

        verify(questionRepository,times(2)).findById(anyString());
        verify(favoriteRepository,times(1)).findAllByUserId(anyString());
    }

    @Test
    void testToGetQuestionWithAnswersWithShowFavorites(){

        Question question = new Question();
        question.setId("1121");
        question.setCategory("any category");
        question.setType("my type");
        question.setUserId("111");
        question.setQuestion("!! Question !!");

        Answer answerOne = new Answer();
        answerOne.setAnswer("answer 1");
        answerOne.setPosition(1);
        answerOne.setId("1112");
        answerOne.setQuestionId("12356");
        answerOne.setUserId("id1user");


        Favorite favorite = new Favorite();
        favorite.setQuestionId("111");
        favorite.setQuestion("!! Question !!");
        favorite.setUserId("any");

        when(questionRepository.findById(anyString())).thenReturn(Mono.just(question));
        when(answerRepository.findAllByQuestionId(anyString())).thenReturn(Flux.just(answerOne));
        when(favoriteRepository.findAllByUserId("1")).thenReturn(Flux.just(favorite));

        StepVerifier.create(getUseCase.apply("1","1"))
                .expectNextMatches(questionDTO -> {
                    assert questionDTO.getFavorite().equals(Boolean.TRUE);
                    assert questionDTO.getQuestion().equals("!! Question !!");
                    assert questionDTO.getCategory().equals("any category");
                    return true;
                }).verifyComplete();

        verify(questionRepository,times(2)).findById(anyString());
        verify(favoriteRepository,times(1)).findAllByUserId(anyString());
    }


}