package co.com.sofka.questions.usecases;

import co.com.sofka.questions.repositories.FavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class DeleteFavoriteUseCaseTest {

    FavoriteRepository favoriteRepository;
    DeleteFavoriteUseCase deleteFavoriteUseCase;

    @BeforeEach
    public void setUp(){
        favoriteRepository = mock(FavoriteRepository.class);
        deleteFavoriteUseCase = new DeleteFavoriteUseCase(favoriteRepository);
    }

    @Test
    void testToDeleteFavorite(){
        when(favoriteRepository.deleteById(anyString())).thenReturn(Mono.empty());
        StepVerifier.create(deleteFavoriteUseCase.apply(anyString()))
                .verifyComplete();
    }

}