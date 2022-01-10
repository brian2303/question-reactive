package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.FavoriteDTO;
import co.com.sofka.questions.usecases.AddFavoriteUseCase;
import co.com.sofka.questions.usecases.DeleteFavoriteUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class FavoriteRouter {

    @Bean
    public RouterFunction<ServerResponse> addFavorite(AddFavoriteUseCase addFavoriteUseCase){
        return route(POST("/addFavorite").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(FavoriteDTO.class)
                        .flatMap(favoriteDTO -> addFavoriteUseCase.apply(favoriteDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> deleteFavorite(DeleteFavoriteUseCase deleteUseCase) {
        return route(DELETE("/deleteFavorite/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteUseCase.apply(request.pathVariable("id")), Void.class))
        );
    }

}
