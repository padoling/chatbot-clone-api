package hellobot.api.domain.scenario;

import hellobot.api.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ScenarioRepositoryTest extends RepositoryTest {

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Test
    public void saveScenario_success() {
        // given
        final Scenario scenario = Scenario.builder()
                .name("썸 연애운")
                .variables("who")
                .descMessage("내꺼인 듯 내꺼 아닌 내꺼 같은 그분\n과연 사귀게 될 건지\n사귀면 언제 사귈 건지\n{$imageId1}\n타로로 점 쳐볼까?")
                .build();

        // when
        scenarioRepository.save(scenario);

        // then
        Scenario result = scenarioRepository.findById(scenario.getId()).get();

        assertThat(scenario.getId()).isEqualTo(result.getId());
        assertThat(scenario.getName()).isEqualTo(result.getName());
        assertThat(scenario.getVariables()).isEqualTo(result.getVariables());
        assertThat(scenario.getDescMessage()).isEqualTo(result.getDescMessage());
    }
}
