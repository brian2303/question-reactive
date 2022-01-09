package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.AnswerPositionUser;
import co.com.sofka.questions.model.AnswerPositionUserDTO;
import co.com.sofka.questions.repositories.AnswerPositionUserRepository;
import co.com.sofka.questions.repositories.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.BiFunction;

@Service
@Validated
public class UpdatePositionAnswerUseCase implements UpdatePositionAnswer{

    private final AnswerRepository answerRepository;
    private final AnswerPositionUserRepository answerPositionUserRepository;
    private final MapperUtils mapperUtils;
    private static final String SUM = "sum";

    public UpdatePositionAnswerUseCase(AnswerRepository answerRepository, AnswerPositionUserRepository answerPositionUserRepository, MapperUtils mapperUtils) {
        this.answerRepository = answerRepository;
        this.answerPositionUserRepository = answerPositionUserRepository;
        this.mapperUtils = mapperUtils;
    }


    @Override
    public Mono<AnswerPositionUserDTO> apply(AnswerPositionUserDTO answerPositionUserDTO) {

        return answerPositionUserRepository.findAllByQuestionIdAndUserId(answerPositionUserDTO.getQuestionId(), answerPositionUserDTO.getUserId())
                .reduce(getLastAnswerPositionUser())
                .map(mapperUtils.mapEntityToAnswerPositionUserDTO())
                .flatMap(lastAnswerPositionDTO -> Mono.zip(Mono.just(lastAnswerPositionDTO),answerRepository.findById(lastAnswerPositionDTO.getAnswerId())))
                .flatMap(last -> Mono.zip(Mono.just(last.getT1()),Mono.just(last.getT2()),
                                Mono.just(answerPositionUserDTO),answerRepository.findById(answerPositionUserDTO.getAnswerId())))
                .flatMap(lastAndCurrently -> {
                    evaluateUpdateAnswer(lastAndCurrently.getT1(),lastAndCurrently.getT3(),lastAndCurrently.getT2(),lastAndCurrently.getT4());
                    return answerRepository.save(lastAndCurrently.getT4())
                            .flatMap(answer -> answerRepository.save(lastAndCurrently.getT2()))
                            .flatMap(answer -> answerPositionUserRepository.save(mapperUtils.mapAnswerPositionUserDTOToEntity(null).apply(answerPositionUserDTO)))
                            .map(mapperUtils.mapEntityToAnswerPositionUserDTO());
                })
                .switchIfEmpty(Mono.just(answerPositionUserDTO)
                        .filter(ansPositionDTO -> ansPositionDTO.getAction().equalsIgnoreCase(SUM))
                        .flatMap(answerPositionUserDTO1 -> answerRepository.findById(answerPositionUserDTO1.getAnswerId()))
                        .flatMap(answer -> {
                            answer.setPosition(answer.getPosition()  + 1);
                            return answerRepository.save(answer)
                                    .flatMap(ans -> answerPositionUserRepository.save(mapperUtils.mapAnswerPositionUserDTOToEntity(null).apply(answerPositionUserDTO)))
                                    .map(mapperUtils.mapEntityToAnswerPositionUserDTO());

                        })
                        .switchIfEmpty(Mono.just(answerPositionUserDTO)
                                .flatMap(answerPositionUserDTO1 -> answerRepository.findById(answerPositionUserDTO1.getAnswerId()))
                                .flatMap(answer -> {
                                    answer.setPosition(answer.getPosition() - 1);
                                    return answerRepository.save(answer)
                                            .flatMap(ans -> answerPositionUserRepository.save(mapperUtils.mapAnswerPositionUserDTOToEntity(null).apply(answerPositionUserDTO)))
                                            .map(mapperUtils.mapEntityToAnswerPositionUserDTO());

                                })
                        )
                );


    }

    private BiFunction<AnswerPositionUser,AnswerPositionUser,AnswerPositionUser> getLastAnswerPositionUser(){
        return (answerPositionUser, answerPositionUser2) ->
                answerPositionUser.getAnswerPositionDate().isAfter(answerPositionUser2.getAnswerPositionDate()) ? answerPositionUser : answerPositionUser2;
    }

    private void evaluateUpdateAnswer(AnswerPositionUserDTO lastAnswerPositionUser, AnswerPositionUserDTO currentlyAnswerPositionUser, Answer lastAnswer,Answer currentlyAnswer){
        if (lastAnswerPositionUser.getAnswerId().equalsIgnoreCase(currentlyAnswerPositionUser.getAnswerId())){
            if (currentlyAnswerPositionUser.getAction().equalsIgnoreCase(lastAnswerPositionUser.getAction())){
                return;
            }
            if (currentlyAnswerPositionUser.getAction().equalsIgnoreCase("rest")){
                currentlyAnswer.setPosition(currentlyAnswer.getPosition() - 1);
                lastAnswer.setPosition(lastAnswer.getPosition() - 1);
                return;
            }
            if (currentlyAnswerPositionUser.getAction().equalsIgnoreCase(SUM)){
                currentlyAnswer.setPosition(currentlyAnswer.getPosition() + 1);
                lastAnswer.setPosition(lastAnswer.getPosition() + 1);
                return;
            }
        }

        if(lastAnswerPositionUser.getAction().equalsIgnoreCase("rest") && currentlyAnswerPositionUser.getAction().equalsIgnoreCase("rest")){
            lastAnswer.setPosition(lastAnswer.getPosition() + 1);
            currentlyAnswer.setPosition(currentlyAnswer.getPosition() - 1);
        }
        if (lastAnswerPositionUser.getAction().equalsIgnoreCase(SUM) && currentlyAnswerPositionUser.getAction().equalsIgnoreCase(SUM)){
            lastAnswer.setPosition(lastAnswer.getPosition() - 1);
            currentlyAnswer.setPosition(currentlyAnswer.getPosition() + 1);
        }
        if (lastAnswerPositionUser.getAction().equalsIgnoreCase(SUM) && currentlyAnswerPositionUser.getAction().equalsIgnoreCase("rest")){
            lastAnswer.setPosition(lastAnswer.getPosition() - 1);
            currentlyAnswer.setPosition(currentlyAnswer.getPosition() - 1);
        }
        if (lastAnswerPositionUser.getAction().equalsIgnoreCase("rest") && currentlyAnswerPositionUser.getAction().equalsIgnoreCase(SUM)){
            lastAnswer.setPosition(lastAnswer.getPosition() + 1);
            currentlyAnswer.setPosition(currentlyAnswer.getPosition() + 1);
        }
    }

}
