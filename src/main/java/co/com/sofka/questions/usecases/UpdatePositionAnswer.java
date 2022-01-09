package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.AnswerPositionUser;
import co.com.sofka.questions.model.AnswerPositionUserDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@FunctionalInterface
public interface UpdatePositionAnswer {
    Mono<AnswerPositionUserDTO> apply(@Valid AnswerPositionUserDTO answerPositionUserDTO);
}
