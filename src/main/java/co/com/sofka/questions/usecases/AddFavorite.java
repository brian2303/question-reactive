package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.FavoriteDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@FunctionalInterface
public interface AddFavorite {

    Mono<FavoriteDTO> apply(@Valid FavoriteDTO favoriteDTO);

}
