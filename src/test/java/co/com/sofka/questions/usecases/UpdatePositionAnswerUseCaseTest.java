package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.AnswerPositionUser;
import co.com.sofka.questions.model.AnswerPositionUserDTO;
import co.com.sofka.questions.repositories.AnswerPositionUserRepository;
import co.com.sofka.questions.repositories.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;


class UpdatePositionAnswerUseCaseTest {



    MapperUtils mapperUtils;
    AnswerPositionUserRepository answerPositionUserRepository;
    AnswerRepository answerRepository;
    UpdatePositionAnswerUseCase updatePositionAnswerUseCase;

    @BeforeEach
    public void setUp(){
        mapperUtils = new MapperUtils();
        answerRepository = mock(AnswerRepository.class);
        answerPositionUserRepository = mock(AnswerPositionUserRepository.class);
        updatePositionAnswerUseCase = new UpdatePositionAnswerUseCase(answerRepository,answerPositionUserRepository,mapperUtils);
    }

    @Test
    void testUpdateVoteAnswer(){

        AnswerPositionUserDTO answerPositionUserDTO = new AnswerPositionUserDTO(
                "12345",
                "sum",
                "12345",
                "1111"
        );

        AnswerPositionUser answerPositionUser = new AnswerPositionUser();
        answerPositionUser.setAction("sum");
        answerPositionUser.setAnswerId("111");
        answerPositionUser.setQuestionId("124");
        answerPositionUser.setAnswerPositionDate(LocalDateTime.now());
        answerPositionUser.setUserId("123456");

        AnswerPositionUser answerPositionResponse = new AnswerPositionUser();
        answerPositionResponse.setAction("sum");
        answerPositionResponse.setAnswerId("111");
        answerPositionResponse.setQuestionId("124");
        answerPositionResponse.setAnswerPositionDate(LocalDateTime.now());
        answerPositionResponse.setUserId("123456");

        var answer = new Answer();
        answer.setAnswer("answer 1");
        answer.setPosition(1);
        answer.setId("1112");
        answer.setQuestionId("12356");
        answer.setUserId("id1user");

        var answerResponse = new Answer();
        answerResponse.setAnswer("answer response");
        answerResponse.setPosition(2);
        answerResponse.setId("1112");
        answerResponse.setQuestionId("12356");
        answerResponse.setUserId("id1user");

        var answerResponse2 = new Answer();
        answerResponse2.setId("1112121212");
        answerResponse2.setAnswer("answer response");
        answerResponse2.setPosition(2);
        answerResponse2.setId("1112");
        answerResponse2.setQuestionId("12356");
        answerResponse2.setUserId("id1user");

//        doReturn(Mono.just(answerResponse)).when(answerRepository).save(any(Answer.class));

        when(answerPositionUserRepository.findAllByQuestionIdAndUserId(anyString(),anyString())).thenReturn(Flux.just(answerPositionUser));
        when(answerRepository.findById(anyString())).thenReturn(Mono.just(answerResponse));
        when(answerRepository.save(any())).thenReturn(Mono.just(answerResponse));
        when(answerPositionUserRepository.save(any())).thenReturn(Mono.just(answerPositionResponse));

        StepVerifier.create(updatePositionAnswerUseCase.apply(answerPositionUserDTO))
                .expectNextMatches(answerPositionUserDTO1 -> {
                    assert answerPositionUserDTO1.getAnswerId().equalsIgnoreCase("111");
                    assert answerPositionUserDTO1.getAction().equalsIgnoreCase("sum");
                    assert answerPositionUserDTO1.getUserId().equalsIgnoreCase("123456");
                    return true;
                }).verifyComplete();
        verify(answerRepository,atLeastOnce()).save(any(Answer.class));


    }


}