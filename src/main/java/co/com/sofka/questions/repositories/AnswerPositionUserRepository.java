package co.com.sofka.questions.repositories;

import co.com.sofka.questions.collections.AnswerPositionUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AnswerPositionUserRepository extends ReactiveCrudRepository<AnswerPositionUser,String> {
    Flux<AnswerPositionUser> findAllByQuestionIdAndUserId(String questionId,String userId);
}
