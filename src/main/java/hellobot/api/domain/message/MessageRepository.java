package hellobot.api.domain.message;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MessageRepository extends MongoRepository<Message, String> {

    public Optional<Message> findByScenarioIdAndNumber(String scenarioId, Integer number);
}
