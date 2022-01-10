package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.FavoriteRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static reactor.core.publisher.Mono.zip;
import static reactor.core.publisher.Mono.just;

@Service
@Validated
public class GetUseCase implements BiFunction<String,String, Mono<QuestionDTO>> {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final FavoriteRepository favoriteRepository;
    private final MapperUtils mapperUtils;

    public GetUseCase(MapperUtils mapperUtils, QuestionRepository questionRepository, AnswerRepository answerRepository, FavoriteRepository favoriteRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.mapperUtils = mapperUtils;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public Mono<QuestionDTO> apply(String id,String userId) {
        Objects.requireNonNull(id, "Id is required");
        return questionRepository.findById(id)
                .map(mapperUtils.mapEntityToQuestion())
                .flatMap(mapQuestionAggregate())
                .flatMap(questionDTO -> zip(just(questionDTO),just(favoriteRepository.findAllByUserId(userId))))
                .flatMap(questionFluxFavorite -> zip(Mono.just(questionFluxFavorite.getT1()),questionFluxFavorite.getT2().collectList()))
                .filter(questionListFavorite -> questionListFavorite.getT2().stream().anyMatch(
                        favorite -> favorite.getQuestionId().equalsIgnoreCase(questionListFavorite.getT1().getId()))
                ).map(questionListFavorite -> {
                   questionListFavorite.getT1().setFavorite(Boolean.FALSE);
                   return questionListFavorite.getT1();
                }).switchIfEmpty(questionRepository.findById(id)
                        .map(mapperUtils.mapEntityToQuestion())
                        .flatMap(mapQuestionAggregate())
                        .map(questionDTO -> {
                            questionDTO.setFavorite(Boolean.TRUE);
                            return questionDTO;
                        })
                );
    }

    private Function<QuestionDTO, Mono<QuestionDTO>> mapQuestionAggregate() {
        return questionDTO ->
                Mono.just(questionDTO).zipWith(
                        answerRepository.findAllByQuestionId(questionDTO.getId())
                                .map(mapperUtils.mapEntityToAnswer())
                                .collectList()
                                .map(answerList -> {
                                    answerList.sort(Comparator.comparing(AnswerDTO::getPosition).reversed());
                                    return answerList;
                                }),
                        (question,answers) -> {
                            question.setAnswers(answers);
                            return question;
                        }
                );
    }

}
