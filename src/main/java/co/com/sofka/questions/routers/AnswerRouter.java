package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.AnswerPositionUserDTO;
import co.com.sofka.questions.usecases.AddAnswerUseCase;
import co.com.sofka.questions.usecases.DeleteAnswerUseCase;
import co.com.sofka.questions.usecases.DeleteUseCase;
import co.com.sofka.questions.usecases.UpdatePositionAnswerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AnswerRouter {

    @Bean
    public RouterFunction<ServerResponse> addAnswer(AddAnswerUseCase addAnswerUseCase) {
        return route(POST("/add").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(AnswerDTO.class)
                        .flatMap(addAnswerDTO -> addAnswerUseCase.apply(addAnswerDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
        );
    }

    @Bean
    public RouterFunction<ServerResponse> updatePositionAnswer(UpdatePositionAnswerUseCase updatePositionAnswerUseCase){
        return route(POST("/updatePosition").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(AnswerPositionUserDTO.class)
                        .flatMap(answerPositionUserDTO -> updatePositionAnswerUseCase.apply(answerPositionUserDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> deleteAnswer(DeleteAnswerUseCase deleteUseCase) {
        return route(
                DELETE("/deleteAnswer/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteUseCase.apply(request.pathVariable("id")), Void.class))
        );
    }

}
