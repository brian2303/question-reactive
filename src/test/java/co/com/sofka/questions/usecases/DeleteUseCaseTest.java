package co.com.sofka.questions.usecases;


import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class DeleteUseCaseTest {

    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    DeleteUseCase deleteUseCase;

    @BeforeEach
    public void setUp(){
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        deleteUseCase = new DeleteUseCase(answerRepository,questionRepository);
    }

    @Test
     void testToDeleteQuestionAndAnswers(){
        when(questionRepository.deleteById(anyString())).thenReturn(Mono.empty());
        when(answerRepository.deleteByQuestionId(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(deleteUseCase.apply(anyString()))
                .verifyComplete();

    }





}