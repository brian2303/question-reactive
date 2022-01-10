package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class CreateUseCaseTest {

    QuestionRepository questionRepository;
    MapperUtils mapperUtils;
    CreateUseCase createUseCase;

    @BeforeEach
    public void setUp(){
        questionRepository = mock(QuestionRepository.class);
        mapperUtils = new MapperUtils();
        createUseCase = new CreateUseCase(mapperUtils,questionRepository);
    }

    @Test
    void testToCreateNewQuestionCompleteOK(){

        var questionDTO = new QuestionDTO();
        questionDTO.setQuestion("any question!!");
        questionDTO.setCategory("Computer scientist");
        questionDTO.setType("type 1");
        questionDTO.setUserId("xxx-xxx");

        var question =  new Question();
        question.setUserId("xxx-xxx");
        question.setId("123");
        question.setQuestion("any question!!");
        question.setCategory("Computer scientist");

        when(questionRepository.save(any(Question.class))).thenReturn(Mono.just(question));

        StepVerifier.create(createUseCase.apply(questionDTO))
                .expectNextMatches(IdQuestion -> {
                    assert IdQuestion.equalsIgnoreCase(question.getId());
                    return true;
                });

    }

}