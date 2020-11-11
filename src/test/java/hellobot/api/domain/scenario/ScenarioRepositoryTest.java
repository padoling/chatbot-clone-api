package hellobot.api.domain.scenario;

import hellobot.api.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ScenarioRepositoryTest extends RepositoryTest {

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Test
    public void saveScenario_success() {
        // given
        List<String> variable = new ArrayList<>();
        variable.add("who");
        Scenario scenario = Scenario.builder()
                .name("썸 연애운")
                .variables(variable)
                .description("설레는 썸도 좋지만, 언젠가는 확실한 결론이 필요하죠. 빠르게 타로카드 한 장을 뽑아보세요. 라마마의 풀이를 참고해서 다음 단계를 결정해봐요.")
                .build();

        // when
        scenarioRepository.save(scenario);

        // then
        Scenario result = scenarioRepository.findById(scenario.getId()).get();

        assertThat(scenario.getId()).isEqualTo(result.getId());
        assertThat(scenario.getName()).isEqualTo(result.getName());
        assertThat(scenario.getVariables()).isEqualTo(result.getVariables());
        assertThat(scenario.getDescription()).isEqualTo(result.getDescription());
    }
}
