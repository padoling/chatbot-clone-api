package hellobot.api.domain.scenario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    Scenario findByName(String name);
}
