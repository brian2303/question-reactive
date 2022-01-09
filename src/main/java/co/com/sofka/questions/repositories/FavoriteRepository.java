package co.com.sofka.questions.repositories;

import co.com.sofka.questions.collections.Favorite;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FavoriteRepository extends ReactiveCrudRepository<Favorite, String> {
}
