package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.FavoriteDTO;
import co.com.sofka.questions.repositories.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class AddFavoriteUseCase implements AddFavorite {

    private final FavoriteRepository favoriteRepository;
    private final MapperUtils mapperUtils;

    public AddFavoriteUseCase(FavoriteRepository favoriteRepository, MapperUtils mapperUtils) {
        this.favoriteRepository = favoriteRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<FavoriteDTO> apply(FavoriteDTO favoriteDTO) {
        return favoriteRepository.save(mapperUtils.mapFavoriteToEntity(null).apply(favoriteDTO))
                .map(mapperUtils.mapEntityToFavorite());
    }
}
