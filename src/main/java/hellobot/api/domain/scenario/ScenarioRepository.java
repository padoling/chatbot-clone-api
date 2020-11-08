package hellobot.api.domain.scenario;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScenarioRepository extends MongoRepository<Scenario, String> {
    Scenario findByName(String name);
}
