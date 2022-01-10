package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.FavoriteDTO;
import co.com.sofka.questions.repositories.FavoriteRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Function;
@Service
public class FavoritesUseCase implements Function<String, Flux<FavoriteDTO>> {

    private final FavoriteRepository favoriteRepository;
    private final MapperUtils mapperUtils;

    public FavoritesUseCase(FavoriteRepository favoriteRepository, MapperUtils mapperUtils) {
        this.favoriteRepository = favoriteRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<FavoriteDTO> apply(String userId) {
        return favoriteRepository.findAllByUserId(userId)
                .map(mapperUtils.mapEntityToFavorite());
    }
}
