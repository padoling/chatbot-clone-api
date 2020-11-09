package hellobot.api.domain.input;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InputRepository extends MongoRepository<Input, String> {

    public Optional<Input> findByScenarioIdAndNumber(String scenarioId, Integer number);
}
