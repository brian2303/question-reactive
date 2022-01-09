package co.com.sofka.questions.repositories;

import co.com.sofka.questions.collections.Favorite;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface FavoriteRepository extends ReactiveCrudRepository<Favorite, String> {
    Flux<Favorite> findAllByUserId(String userId);
}
