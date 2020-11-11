package hellobot.api.domain.tarot;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TarotRepository extends MongoRepository<Tarot, String> {
    public Optional<Tarot> findByScenarioIdAndTarotNum(String scenarioId, int tarotNum);
}
