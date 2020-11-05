package hellobot.api.domain.scenario;

import hellobot.api.RepositoryTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ScenarioRepositoryTest extends RepositoryTest {

    @Autowired
    private ScenarioRepository scenarioRepository;

    @AfterEach
    public void cleanUp() {
        scenarioRepository.deleteAll();
    }

    @Test
    public void saveScenario_returns_scenario_with_id() {
        final Scenario scenario = Scenario.builder().build();
        final Scenario saveScenario = scenarioRepository.save(scenario);
        assertThat(saveScenario.getId()).isEqualTo(1L);
    }
}
