package co.com.sofka.questions.usecases;

import co.com.sofka.questions.repositories.FavoriteRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class DeleteFavoriteUseCase implements Function<String, Mono<Void>> {

    private final FavoriteRepository favoriteRepository;

    public DeleteFavoriteUseCase(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public Mono<Void> apply(String id) {
        return favoriteRepository.deleteById(id);
    }
}
